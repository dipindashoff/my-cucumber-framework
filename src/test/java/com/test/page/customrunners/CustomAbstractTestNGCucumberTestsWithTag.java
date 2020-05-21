package com.test.page.customrunners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.test.page.stepdefinitions.StepDefintions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.cucumber.testng.TestNGCucumberRunner;
//import stepdefinitions.StepDefinitions;

public class CustomAbstractTestNGCucumberTestsWithTag {
	private TestNGCucumberRunner testNGCucumberRunner;

	@BeforeClass(alwaysRun = true)
	public void setUpClass() {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	}

	@Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
	public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) throws Throwable {
		// the 'featureWrapper' parameter solely exists to display the feature file in a
		// test report
		testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
	}

	/**
	 * Returns two dimensional array of PickleEventWrapper scenarios with their
	 * associated CucumberFeatureWrapper feature.
	 *
	 * @return a two dimensional array of scenarios features.
	 */
	@DataProvider
	public Iterator<Object[]> scenarios() {
		ArrayList<Object[]> modifiedList = new ArrayList<>();
		if (testNGCucumberRunner == null) {
//			return new Object[0][0];
			return modifiedList.iterator();
		}
		// I added
//		Object[][] data = testNGCucumberRunner.provideScenarios();
//		
//		PickleEventWrapper pickleEventWrapper = (PickleEventWrapper) data[0][0];
//		CucumberFeatureWrapper cucumberFeatureWrapper = (CucumberFeatureWrapper) data[0][1];
		modifiedList = filterByTag(testNGCucumberRunner.provideScenarios());
//		return testNGCucumberRunner.provideScenarios();
		return modifiedList.iterator();
	}

	// data[0][0] ->> PickleEventWrapper -->Scenario
	// data[0][1] ->> CucumberFeatureWrapper -->Feature

	/**
	 * private method that will return an array list - contains the feature I want
	 * to execute
	 * 
	 * @return
	 */
	private ArrayList<Object[]> filterByTag(Object[][] data) {
		// To Run from VM arguments
//		String tagValue = System.getProperty("TagName");
//
//		if (tagValue == null || tagValue.isEmpty()) {
//			return getFeatureList(data);
//		}
//
//		List<String> tagList = Arrays.asList(tagValue.split(","));
		// Hardcode values
//		List<String> tagList  = Arrays.asList("@test2");
		List<String> tagList  = StepDefintions.getAllTagNames();
		ArrayList<Object[]> modifiedList = new ArrayList<>();

		if (data != null) {
			for (int i = 0; i < data.length; i++) {
				PickleWrapper pickleWrapper = (PickleWrapper) data[i][0]; // PickleEventWrapper picks all tag info
				
				if(!pickleWrapper.getPickle().getTags().isEmpty()) {
					for(String aTag: tagList)
						if(isTagPresent(aTag, pickleWrapper.getPickle().getTags()))
							modifiedList.add(data[i]);
				}
			}
		}
		return modifiedList;
	}
	
	/**
	 * 
	 */
	private boolean isTagPresent(String aTag, List<String> aTagList) {
		
		for(String pickleTag: aTagList) {
			if(aTag.equalsIgnoreCase(pickleTag))
				return true;
		}
		return false;		
	}

	/**
	 * Returns a list of Object[]
	 * Copy contents from data[] to an ArrayList<Object[]>
	 * Used when VM args are not passed i.e no filter of features.
	 */
	private ArrayList<Object[]> getFeatureList(Object[][] data) {
		ArrayList<Object[]> modifiedList = new ArrayList<>();

		if (data != null) {
			for (int i = 0; i < data.length; i++) {
				modifiedList.add(data[i]);
			}
		}

		return modifiedList;
	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass() {
		if (testNGCucumberRunner == null) {
			return;
		}
		testNGCucumberRunner.finish();
	}
}
