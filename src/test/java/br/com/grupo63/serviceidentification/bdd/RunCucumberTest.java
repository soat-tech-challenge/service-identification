package br.com.grupo63.serviceidentification.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "StepsDefinition",
        plugin = {"pretty", "html:target/cucumber-reports"}
)
public class RunCucumberTest {
}