package net.siinergy.springbatch.demo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.support.JdbcTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;

@TestConfiguration
public class JdbcTestConfiguration {
    private final Environment env;
    public JdbcTestConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    public JdbcTransactionManager transactionManager(DataSource dataSource) {
        return new JdbcTransactionManager(dataSource);
    }

    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return this.getDataSource();
    }

    @Bean(name = "jdbcTemplatePostgres")
    public JdbcTemplate jdbcTemplatePostgres(@Qualifier("dataSource") DataSource dataSource) {
        DatabasePopulatorUtils.execute(createDatabasePopulator(), dataSource);
        return new JdbcTemplate(dataSource);
    }
    private DataSource getDataSource() {
        var dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty(String.format("%s.driver-class-name", "spring.datasource"))));
        dataSource.setUrl(Objects.requireNonNull(env.getProperty(String.format("%s.url", "spring.datasource"))));
        return dataSource;
    }

    private DatabasePopulator createDatabasePopulator() {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.setContinueOnError(false);
        databasePopulator.addScript(new ClassPathResource("scripts/test_movie_model.sql"));
        databasePopulator.addScript(new ClassPathResource("scripts/db_batch_metadata.sql"));
        return databasePopulator;
    }
}