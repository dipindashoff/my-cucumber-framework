package com.test.page.testrunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.testng.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/Features",
//		features = "src\\test\\resources\\Features\\",
		glue = {"com.test.page.stepdefinitions"},
//		tags = {"@test4"},
		strict = true,
		monochrome = true
		)


public class TestRunner {
}
