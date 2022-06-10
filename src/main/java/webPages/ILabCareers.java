package webPages;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import commons.CommonMethods;
import dataFiles.Data;
import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import reporting.CreateExcelFile;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

public class ILabCareers extends objectRepository.ILabCareers {
    private Properties properties;
    CreateExcelFile createExcelFile = new CreateExcelFile();
    String fileName = "";
    String sheetName = "";
    static ExtentTest test;
    static ExtentReports report;

    public ILabCareers(Properties properties) {
        try {
            this.properties = properties;
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    public void jobApplication(WebDriver webDriver, String[] headers, String[] data, ExtentTest test) throws Exception {

        PageFactory.initElements(webDriver, this);
        CommonMethods commonMethods = new CommonMethods();
        String country = Data.getCellValue(headers, data, "Country");
        String name = Data.getCellValue(headers, data, "Name");
        String emailaddr = Data.getCellValue(headers, data, "Email");
        String testcaseNo = Data.getCellValue(headers, data, "TestcaseNo");

        commonMethods.clickElement(careersButton, test);
        webDriver.findElement(By.xpath("//a[text()='" + country + "']")).click();
        if (!currentOpenings.isEmpty() && currentOpenings != null) {
            test.log(LogStatus.PASS, "Careers Page opened successfully");
            String scrShrt = new CommonMethods().takeScreenshotImage(webDriver, "2");
            test.log(LogStatus.PASS, test.addScreenCapture(scrShrt));
            for (WebElement itemsList : currentOpenings) {
                currentOpenings.get(0).click();
                break;
            }
        } else {
            throw new Exception(currentOpenings + "is Not Displayed");
        }
        commonMethods.clickElement(applyonline, test);
        commonMethods.sendKeys(applicantName, name, test);
        commonMethods.sendKeys(email, emailaddr, test);
        commonMethods.sendKeys(phone, generateCellNo(), test);
        test.log(LogStatus.PASS, "Application Screen");
        String scrShrt = new CommonMethods().takeScreenshotImage(webDriver, "3");
        test.log(LogStatus.PASS, test.addScreenCapture(scrShrt));
        commonMethods.clickElement(sendApplicationButton, test);
        commonMethods.checkError(webDriver, errorMessage, testcaseNo, properties, test);
    }

    private String generateCellNo() {
        String phNo = "";
        String randomNumbers = RandomStringUtils.randomNumeric(7);
        return phNo = "083" + " " + randomNumbers.substring(0, 3) + " " + randomNumbers.substring(3, 7);
    }

}
