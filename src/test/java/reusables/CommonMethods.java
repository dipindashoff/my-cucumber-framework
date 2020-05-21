package reusables;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class CommonMethods extends DriverUtilities {
    /**
     * Launch Browser
     */
    public void launchBrowser(String browser){
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        if(browser.equalsIgnoreCase("chrome")){
            ChromeOptions options = new ChromeOptions();
            options.setPageLoadStrategy(PageLoadStrategy.NONE);
            options.setExperimentalOption("useAutomationExtension", false);
            options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            options.setExperimentalOption("prefs", prefs);
            options.addArguments("-disable-save-password-bubble", "false");
            options.addArguments("-disable-extensions");
            options.addArguments("no-sandbox");
            options.addArguments("start-maximized");

            driver = new ChromeDriver(options);

            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            driver.manage().window().maximize();
        }
    }

    /**
     * Switch to current window
     */
    public void switchToCurrentWindow(){
        try{
            driver.getWindowHandles().forEach(x->driver.switchTo().window(x));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Switch to frame
     */
    public void switchFrame(String frameName){
        try{
            driver.switchTo().frame(frameName);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * get properties from .properties file
     */
   public static String getProperty(String propFileName, String propertyName){
       Properties prop = null;
       String filePath = "";

       filePath = ".\\src\\test\\resources\\Configuration\\" + propFileName + ".properties";

       try(InputStream input = new FileInputStream(filePath)){
           prop = new Properties();
           prop.load(input);

       } catch(Exception e){
            e.printStackTrace();
       }
       return prop.getProperty(propertyName);
    }

    /**
     * Get current time
     */
    public String getCurrentTimeStamp(){
        return (LocalDateTime.now().toString().replaceAll("\\W","_"));
    }

    /**
     * Take screenshot
     */
    public String takeScreenshot(){
        String filePath = resultFilePath + "\\Images\\" + getCurrentTimeStamp() + ".jpeg";

        try {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath));
        } catch (Exception e){
            e.printStackTrace();
        }
        return filePath;
    }

 /*
 * Create result folder
  */
    public static String createResultFolder(String tagName){

        resultFilePath =
                System.getProperty("user.dir") + getProperty("config", "ActualResultsPath") + File.separator +tagName
                + File.separator + LocalDateTime.now().toString().replaceAll("\\W", "_");

        // if file doesn't exist, create one
        if(!Files.exists(Paths.get(resultFilePath))){
            try{
                Files.createDirectories(Paths.get(resultFilePath));
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return resultFilePath;
    }

    /**
     * Initialize Extent report
     */
    public  static void startExtentReport(String testCaseID){

        try {
            extent = new ExtentReports(System.getProperty("user.dir") + getProperty("config", "ExtentReportsPath"),
                    false, DisplayOrder.NEWEST_FIRST);
            // By default, the OS, User Name, Java Version and Host Name will be available
            extent.addSystemInfo("Selenium Version", "2.46");
            extent.addSystemInfo("Environment", "Prod");

            // Configuration
            extent.loadConfig(new File(System.getProperty("user.dir") + getProperty("config", "ExtentConfigXMLPath")));
            test = extent.startTest(testCaseID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generate Extent Report
     */
    public static void endExtentReport(){
        try {
            extent.endTest(test);
            extent.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Add log to extent report
     */
    public void log(String description, String passOrFail){

        try{
            LogStatus status = null;
            Log.info(description);
            status = passOrFail.equalsIgnoreCase("pass")? LogStatus.PASS:LogStatus.FAIL;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
