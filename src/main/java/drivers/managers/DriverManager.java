package drivers.managers;

import drivers.DriverType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Arrays;
import java.util.HashMap;

public class DriverManager {
    private Logger log = LogManager.getLogger(DriverManager.class);
    protected WebDriver driver;
    protected DesiredCapabilities desiredCapabilities;

    public DriverManager() {
    }

    public WebDriver getWebDriver(DriverType driverType) {
        switch (driverType) {
            case CHROME:
                createChromeDriver();
                break;
            case EDGE:
                createEdgeDriver();
                break;
            default:
                createChromeDriver();
        }
        return driver;
    }


    public void createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        String switches = "--ignore-certificate-errors," +
                "--disable-popup-blocking," +
                "--disable-translate," +
                "--incognito,--disable-extensions";
        String[] switchList = switches.split(",");

        options.setCapability("chrome.switches", Arrays.asList(switchList));
        options.addArguments("test-type");

        HashMap<String, Object> chromePreferences = new HashMap();
        chromePreferences.put("profile.default_content_settings.popups", 0);
        options.setExperimentalOption("prefs", chromePreferences);

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
    }

    public void createEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
    }

}
