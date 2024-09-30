package net.siinergy.springbatch.demo;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = { Application.class, JdbcConfiguration.class}, properties = { "spring.main.allow-bean-definition-overriding=true" })
@ActiveProfiles({ "TEST" })
public class CucumberSpringConfiguration {

}