package net.siinergy.springbatch.demo.jobs.updatedata;

import net.siinergy.springbatch.demo.dto.MovieDto;
import net.siinergy.springbatch.demo.jobs.initdata.MovieInitWriter;
import net.siinergy.springbatch.demo.jobs.updatedata.model.MovieOut;
import net.siinergy.springbatch.demo.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

//@Configuration
//@EnableBatchProcessing
public class MovieUpdateConfig {

    final JobRepository jobRepository;
    final PlatformTransactionManager batchTransactionManager;

    public static final Logger logger = LoggerFactory.getLogger(MovieUpdateConfig.class);
    private static final int BATCH_SIZE = 3;
    private final JdbcTemplate jdbcTemplate;

    public MovieUpdateConfig(JobRepository jobRepository, PlatformTransactionManager batchTransactionManager, @Qualifier("jdbcTemplatePostgres") JdbcTemplate jdbcTemplate) {
        this.jobRepository = jobRepository;
        this.batchTransactionManager = batchTransactionManager;
        this.jdbcTemplate =jdbcTemplate;

    }

    @Bean("movieUpdateReader")
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
    /**
     * Job which contains multiple steps
     */
    @Bean
    public Job jobUpdateInputFile() {
        return new JobBuilder("jobUpdateMovies", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(stepUpdateMovies())
                .build();
    }


    @Bean
    public Step stepUpdateMovies() {
        return new StepBuilder("stepUpdateMovies", jobRepository)
                .<Movie, MovieDto>chunk(BATCH_SIZE, batchTransactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }


    @Bean
    public MovieUpdateProcessor processor() {
        return new MovieUpdateProcessor(this.jdbcTemplate);
    }

    @Bean
    public ItemWriter<? super MovieDto> writer() {
        return new MovieUpdateWriter(this.jdbcTemplate);
    }

}
