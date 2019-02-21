package com.guda.ui.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions
        (plugin = {"io.qameta.allure.cucumber3jvm.AllureCucumber3Jvm", "pretty", "json:target/cucumber-report/report.json"},
                monochrome = true,
                features = "src/test/resources/features/klix.feature",
                glue = {"com.guda.ui.util", "com.guda.ui.steps"})

public class KlixTest {
}
