package com.test.page.stepdefinitions;


import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Recordset;
import com.relevantcodes.extentreports.LogStatus;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import reusables.CommonMethods;
import reusables.ExcelUtilities;
import reusables.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static reusables.DriverUtilities.driver;
import static reusables.DriverUtilities.resultFilePath;

public class Hooks {

    /**
     * Create result folder for each run
     */
    @Before
    public static void setUp(Scenario scenario){
        try {
            Collection<String> tagName = scenario.getSourceTagNames();
            String tag = tagName.toString().replaceAll("[\\[\\]]", "");
            resultFilePath = CommonMethods.createResultFolder(tag);
            Log.startLog("********************* START TEST*********************");
            log("Result folder - created!", "pass");
        } catch (Exception e) {
            e.printStackTrace();
            log("Error in result folder creation", "fail");
        }
    }
    /**
     * Perform sign off, generate reports & quit driver
     */
    public static void tearDown(){
        try {
            // Log off from application
            Log.endLog("******************** END TEST *********************");
            CommonMethods.endExtentReport();
            driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
            log("Error in tear down", "fail");
        }
    }

    /**
     * Extract all tag names from daat sheet &return it as list of strings
     */
    public static List<String> getAllTagNames() {

        List<String> list = new ArrayList<>();

        try {
            Recordset rs = ExcelUtilities.readExcel(".\\src\\test\\resources\\ScriptsToRun\\InputTestData.xlsx",
                    "Select * from TagNames");

            for (int i = 1; i <= rs.getCount(); i++) {

                list.add(rs.getField("TagNames"));
                rs.moveNext();

            }
        } catch (FilloException e) {
//			e.printStackTrace();
        }
//		return String.join(",", list).replaceAll("[\\[\\]]","");
        return list;
    }


    /**
     * Add log to extent report
     */
    public static void log(String description, String passOrFail){

        try {
            LogStatus status = null;
            Log.info(description);
            status = passOrFail.equalsIgnoreCase("pass")? LogStatus.PASS : LogStatus.FAIL;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
