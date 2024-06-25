package net.siinergy.springbatch.demo.jobs.batch.initdata;

import net.siinergy.springbatch.demo.jobs.model.MovieDto;
import net.siinergy.springbatch.demo.jobs.batch.Movie;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public FlatFileItemReader<Movie> reader() {
        String[] columnNames = {"Id", "Title", "Year", "Genre", "Duration", "Origin", "Director", "IMDB rating","Rating count", "IMDB link"}; // Noms des colonnes

        return new FlatFileItemReaderBuilder<Movie>()
                .resource(new ClassPathResource("movies.csv"))
                .name("MovieInit")
                .linesToSkip(1)
                .delimited()
                .delimiter(",")
                .names(columnNames)
                .fieldSetMapper(fieldSet -> new Movie()
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
    public ItemProcessor<Movie, MovieDto> processor() {
        return new MovieInitProcessor();
    }

    @Bean("movieInitWriter")
    public MovieInitWriter writer() {
        return new MovieInitWriter();
    }



 @Bean
    public Step stepInitMovie() {
        return new StepBuilder("stepInitMovie", jobRepository)
                .<Movie, MovieDto>chunk(BATCH_SIZE, batchTransactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job jobMovie() {
        return new JobBuilder("jobMovie", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(stepInitMovie())
                .build();
    }

}
