package net.siinergy.springbatch.demo;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:net/siinergy/springbatch/demo/cucumber", glue = "net.siinergy.springbatch.demo.cucumber", plugin = { "pretty", "html:target/cucumber" })
public class CucumberTest {
}