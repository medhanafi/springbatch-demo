package net.siinergy.springbatch.demo;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("cucumber")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "net.siinergy.springbatch.demo")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "json:build/reports/tests/cucumber.json,pretty,html:build/reports/tests/cucumber-reports.html")
public class CucumberIntegration {
    // This class stays empty
}