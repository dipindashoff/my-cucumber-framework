package com.test.page.stepdefinitions;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Recordset;
import io.cucumber.java.en.Given;
import reusables.ExcelUtilities;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

public class StepDefintions {

    @Given("^I watched Onward today$")
    public void i_watched_onward_today() {
        System.out.println("Tag Test1");
        Assert.assertTrue(false);
    }

    @Given("^I also watched Contagion today$")
    public void i_watched_contag_today() {
        System.out.println("Tag Test2");
    }

    @Given("^This is sample$")
    public void this_is_sample() {
        System.out.println("Tag Test3");
    }

    @Given("^This is another sample$")
    public void this_is_another_sample() {
        System.out.println("Tag Test4");
    }

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
}
