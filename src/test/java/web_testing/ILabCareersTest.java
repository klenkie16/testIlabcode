package web_testing;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import reporting.CreateExcelFile;
import dataFiles.Data;
import commons.CommonMethods;
import drivers.DriverType;
import drivers.managers.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import reporting.ReportBean;
import webPages.ILabCareers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ILabCareersTest extends CommonMethods {

    private Properties properties;
    private String headers[];
    private int testCounter = -1;
    CreateExcelFile createExcelFile = new CreateExcelFile();
    Data testData = new Data();
    String fileName = "";
    String sheetName = "";
    WebDriver webDriver;
    static ExtentTest test;
    static ExtentReports report;

    public ILabCareersTest() {
        try {
            this.properties = getPropInstance(getEnvironmentVariables());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DataProvider(name = "ILabDataProvider")
    public Object[][] createSauceData() throws Exception {
        return testData.datasheetArr(properties.getProperty("TEST_DATA").toString(), properties.getProperty("SHEET"));
    }

    @BeforeTest
    public void setup() {
        try {
            testCounter = -1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test(dataProvider = "ILabDataProvider", enabled = true)
    public void testScenario(String... lineData) {
        String experror = "";
        try {
            ILabCareers iLabCareers = new ILabCareers(properties);
            String browserType = properties.getProperty("BROWSER_TYPE");
            testCounter++;
            if (testCounter == 0) {
                headers = lineData;
                String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Calendar.getInstance().getTime());
                fileName = ".\\Resources\\Reports\\ILabCareers\\Excel\\" + properties.getProperty("SHEET") + timeStamp + ".xls";
                sheetName = properties.getProperty("SHEET");
                createExcelFile.getExcelSheetFile(fileName, sheetName);
                createExcelFile.addColumsToReport(properties.getProperty("REPORT_COLS").split(","));
                report = new ExtentReports(".\\Resources\\Reports\\ILabCareers\\HTMLReport\\" + properties.getProperty("SHEET") + timeStamp + ".html");
                test = report.startTest("JobApplication");
            } else {
                String testNo = Data.getCellValue(headers, lineData, "TestcaseNo");
                experror = Data.getCellValue(headers, lineData, "ExpectedErrorMSG").trim();
                createExcelFile.writeToReport(fileName, sheetName, ReportBean.getTESTCASENO(), testCounter, testNo);
                webDriver = new DriverManager().getWebDriver(DriverType.valueOf(browserType.toUpperCase()));
                webDriver.get(properties.getProperty("URL"));
                webDriver.manage().window().maximize();
                test.log(LogStatus.PASS, "URL opened successfully");
                String urlscrShrt = takeScreenshotImage(webDriver, "1");
                test.log(LogStatus.PASS, test.addScreenCapture(urlscrShrt));

                iLabCareers.jobApplication(webDriver, headers, lineData, test);
            }
        } catch (Exception e) {
            handleException(fileName, sheetName, testCounter, e, experror);
        } finally {
                report.endTest(test);
                report.flush();
        }
    }

    @AfterTest
    public void closeBrowser() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
    @Test
    private void isPalindrome(){
        String fibo = "aaba";
        StringBuilder sb = new StringBuilder(fibo);
        System.out.println(fibo.equalsIgnoreCase(sb.reverse().toString()));

        System.setProperty("webdriver.chrom.driver","driverpath");
        WebDriver driver = new ChromeDriver();
        Actions a = new Actions(driver);
        a.moveToElement(driver.findElement(By.xpath(""))).click().perform();
        a.doubleClick().perform();
        a.moveToElement(driver.findElement(By.xpath(""))).build().perform();

        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//")));
    }
}

