package com.test.page.testrunner;

import com.test.page.customrunners.CustomAbstractTestNGCucumberTestsWithTag;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = {"src/test/resources/Features"},
		glue = {"com.test.page.stepdefinitions"},
		strict = true,
		monochrome = true
		//tags = {"@regression"}
		)
public class TestNGRunner extends CustomAbstractTestNGCucumberTestsWithTag {

}
