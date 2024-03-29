package com.sii.springbatchdemo;

import com.sii.springbatchdemo.services.MovieDatabaseProviderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbatchdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbatchdemoApplication.class, args);
    }

    @Autowired
    public MovieDatabaseProviderServices movieDatabaseProviderServices;

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) throws Exception {
        return args -> movieDatabaseProviderServices.run();
    }

}
