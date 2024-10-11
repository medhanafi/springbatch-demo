package net.siinergy.springbatch.demo.jobs.initdata;

import lombok.Getter;
import net.siinergy.springbatch.demo.jobs.listener.BatchStepListener;
import net.siinergy.springbatch.demo.model.Movie;
import net.siinergy.springbatch.demo.model.MovieData;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class MovieInitConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager batchTransactionManager;
    private final JdbcTemplate jdbcTemplate;
    private static final int BATCH_SIZE = 100;

    public MovieInitConfig(@Qualifier("jdbcTemplatePostgres") JdbcTemplate jdbcTemplate, JobRepository jobRepository, PlatformTransactionManager batchTransactionManager) {
        this.jobRepository = jobRepository;
        this.batchTransactionManager = batchTransactionManager;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean("movieInitReader")
    @StepScope
    public FlatFileItemReader<MovieData> reader(@Value("#{jobParameters[fileName]}") String fileName) {
        String[] columnNames = {"Id", "Title", "Year", "Genre", "Duration", "Origin", "Director", "IMDB rating","Rating count", "IMDB link"}; // Noms des colonnes

        return new FlatFileItemReaderBuilder<MovieData>()
                .resource(new ClassPathResource(fileName))
                .name("MovieInit")
                .linesToSkip(1)
                .delimited()
                .delimiter(";")
                .names(columnNames)
                .fieldSetMapper(fieldSet -> new MovieData()
                        .setId(fieldSet.readString(columnNames[0]))
                        .setTitle(fieldSet.readString(columnNames[1]))
                        .setYear(fieldSet.readString(columnNames[2]))
                        .setGenre(fieldSet.readString(columnNames[3]))
                        .setDuration(fieldSet.readString(columnNames[4]))
                        .setCountries(fieldSet.readString(columnNames[5]))
                        .setDirector(fieldSet.readString(columnNames[6]))
                        .setRating(fieldSet.readString(columnNames[7]))
                        .setImdbLink(fieldSet.readString(columnNames[9]))).build();

    }

    @Bean("movieInitProcessor")
    public ItemProcessor<MovieData, Movie> processor() {
        return new MovieInitProcessor();
    }

    @Bean("movieInitWriter")
    public MovieInitWriter writer() {
        return new MovieInitWriter();
    }



 @Bean
    public Step stepInitMovie(BatchStepListener batchStepListener ) {
        return new StepBuilder("stepInitMovie", jobRepository)
                .<MovieData, Movie>chunk(BATCH_SIZE, batchTransactionManager)
                .reader(reader(null))
                .processor(processor())
                .writer(writer())
                .listener(batchStepListener)
                .build();
    }

    @Bean
    public Job jobMovie(BatchStepListener batchStepListener) {
        return new JobBuilder("jobMovie", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(stepInitMovie(batchStepListener))
                .build();
    }

}
