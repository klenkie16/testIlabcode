package commons;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import jxl.Workbook;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import reporting.CreateExcelFile;
import reporting.ReportBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class CommonMethods {

    private WebDriver driver;
    private static final int TIMEOUT = 100;
    final String ENVIRONMENT_VARIABLES = "./resources/Env.Properties.properties";

    public CommonMethods() {

    }

    public String getEnvironmentVariables() {
        return this.ENVIRONMENT_VARIABLES;
    }

    public Properties getPropInstance(String propertiesFilePath) throws Exception {
        InputStream input = null;
        Properties properties = new Properties();

        try {
            input = new FileInputStream(new File(propertiesFilePath));
            properties.load(input);
        } catch (Exception var12) {
            throw new IOException();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException var11) {
                }
            }

        }

        return properties;
    }


    public int runFrom(int val) {
        try {
            return Integer.valueOf(System.getProperty("from"));
        } catch (Exception e) {
            return val;
        }
    }

    public int runTo(int val) {
        try {
            return Integer.valueOf(System.getProperty("to"));
        } catch (Exception e) {
            return val;
        }
    }

    public void checkError(WebDriver webDriver, List<WebElement> errorMessage, String testcaseNo, Properties properties, ExtentTest test) throws Exception {

        String errorMsgs = "";
        if (!errorMessage.isEmpty() && errorMessage != null) {
            for (WebElement error : errorMessage) {
                errorMsgs = error.getText();
                test.log(LogStatus.FAIL, errorMsgs);
            }
            String scrShrt = takeScreenshotImage(webDriver, "4");
            test.log(LogStatus.FAIL, test.addScreenCapture(scrShrt));
            JavascriptExecutor jse = (JavascriptExecutor) webDriver;
            jse.executeScript("window.scrollBy(0,300)");
            scrShrt = takeScreenshotImage(webDriver, "5");
            test.log(LogStatus.FAIL, test.addScreenCapture(scrShrt));
            throw new Exception(errorMsgs);
        }
    }

    public void handleException(String filename, String sheetName, int testCounter, Exception testEx, String experror) {

        CreateExcelFile createExcelFile = new CreateExcelFile();
        String error = null;
        try {
            if (!testEx.getMessage().isEmpty()) {
                error = testEx.getMessage();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        if (error != null) {
            if (error.equalsIgnoreCase(experror)) {
                createExcelFile.writeToReport(filename, sheetName, ReportBean.getRESULT(), testCounter, "Pass");
            } else {
                createExcelFile.writeToReport(filename, sheetName, ReportBean.getRESULT(), testCounter, "Fail");
            }
            createExcelFile.writeToReport(filename, sheetName, ReportBean.getREASONFORFAILURE(), testCounter, error);
        }
    }

    public String takeScreenshotImage(WebDriver driver, String description) {

        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(".\\Resources\\Reports\\ILabCareers\\HTMLReport\\" + description + ".png"));
            return description + ".png";
        } catch (Exception e) {
            return null;
        }
    }

    public void clickElement(WebElement element, ExtentTest test) {
        try {
            if (element.isDisplayed() && element.isEnabled()) {
                element.click();
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, element + "Is Not Displayed or Is Not Enabled for Clicking");
            e.printStackTrace();
        }
    }



    public void sendKeys(WebElement element, String value, ExtentTest test) {
        try {
            if (element.isDisplayed()) {
                element.sendKeys(value);
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, element + "Is Not Displayed or Is Not Enabled for Editing");
            e.printStackTrace();
        }
    }
}
