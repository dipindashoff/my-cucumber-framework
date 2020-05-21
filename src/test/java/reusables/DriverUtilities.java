package reusables;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverUtilities {

    public static WebDriver driver;
    public static ExtentReports extent;
    public static ExtentTest test;
    public static String resultFilePath;

    /**
     * Explicit Waait
     */
    public void wait(By by){
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(by));
    }

    /**
     * Highight element
     */
    // Refer Naveen Automation github
//    https://github.com/naveenanimation20/SeleniumJavaCourse/blob/master/src/SeleniumSessions/JavaScriptExecutorConcept.java
    public void highlightElement(By by) {

       ((JavascriptExecutor) driver).executeScript("arguments[0].style.backgroundColor='2px solid blue'",
               driver.findElement(by));
       }

    public void flash(By by) {
        WebElement element = driver.findElement(by);
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        String bgcolor  = element.getCssValue("backgroundColor");
        for (int i = 0; i <  10; i++) {
            changeColor("rgb(0,200,0)", element);//1
            changeColor(bgcolor, element);//2
        }
    }
    public void changeColor(String color, WebElement element) {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].style.backgroundColor = '"+color+"'",  element);

        try {
            Thread.sleep(20);
        }  catch (InterruptedException e) {
        }
    }

    /**
     * Is element present
     */
    public boolean isElementPresent(By by){
        try {
            return driver.findElement(by) != null? true : false;
        } catch (Exception e) {
            System.out.println("Element, "+by+" is not found");
        }
        return false;
    }

    /**
     * Move to element
     */
    public void hoverOver(By by){
        try {
            wait(by);
            new Actions(driver).moveToElement(driver.findElement(by)).perform();;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Click a web element
     */
    public void click(By by){
        try {
            wait(by);
            hoverOver(by);
            highlightElement(by);
            driver.findElement(by).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Right click a web element
     */
    public void rightClick(By by){
        try {
            wait(by);
            hoverOver(by);
            highlightElement(by);
            new Actions(driver).contextClick(driver.findElement(by)).perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Double click a web element
     */
    public void doubleClick(By by){
        try {
            wait(by);
            hoverOver(by);
            highlightElement(by);
            new Actions(driver).doubleClick(driver.findElement(by)).perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Click using JS
     */

    public void clickForcefully(By by) {
        try {
            wait(by);
            hoverOver(by);
            highlightElement(by);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                    driver.findElement(by));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Enter text
     */
    public void enterText(By by, String str) {
        try {
            wait(by);
            hoverOver(by);
            highlightElement(by);
            WebElement element = driver.findElement(by);
            element.clear();
            element.sendKeys(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Enter text using JS
     */
    public void enterTextForcefully(By by, String str) {
        try {
            wait(by);
            hoverOver(by);
            highlightElement(by);
            WebElement element = driver.findElement(by);
            element.clear();
            ((JavascriptExecutor) driver).executeScript("arguments[0].value='"+str+"';",
                    element);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Scroll to element
     */
    public void scrollToElement(By by){
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollToView();",
                    driver.findElement(by));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get value of the attribute
     */
    public String getAttribute(By by, String attributeName){
        try {
            wait(by);
            WebElement element = driver.findElement(by);
            return ((!element.equals(null))? element.getAttribute(attributeName): null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Select drop down
     */
    public void selectDropDown(By by, String dropdownValue){
        try {
            wait(by);
            WebElement element = driver.findElement(by);
            if(!element.equals(null)){
                highlightElement(by);
                new Select(element).selectByVisibleText(dropdownValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get text
     */
    public String getText(By by){
        try {
            wait(by);
            return driver.findElement(by).getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Mimic keyboard actions
     */
    public void enterKey(String key){

        Actions action = new Actions(driver);
        switch(key) {
            case "ENTER":
                action.sendKeys(Keys.ENTER).build().perform();
                break;
            case "LEFT":
                action.sendKeys(Keys.LEFT).build().perform();
                break;

                //TODO - add other cases
        }
    }
}
