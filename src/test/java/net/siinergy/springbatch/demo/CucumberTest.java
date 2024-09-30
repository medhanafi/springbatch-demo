package net.siinergy.springbatch.demo;


import io.cucumber.junit.CucumberOptions;

@CucumberOptions(features = "classpath:net/siinergy/springbatch/demo/cucumber", glue = "net.siinergy.springbatch.demo.cucumber", plugin = { "pretty", "html:target/cucumber" })
public class CucumberTest {
}
