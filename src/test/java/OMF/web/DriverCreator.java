package OMF.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.util.concurrent.TimeUnit;

public class DriverCreator {
    public static int implicitWait = 15;

    private WebDriver initChromeDriver() {
        System.out.println("Launching google chrome with new profile..");
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        options.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, PageLoadStrategy.NORMAL);
        options.setCapability(CapabilityType.SUPPORTS_ALERTS, true);
        options.addArguments("test-type");
        options.addArguments("enable-strict-powerful-feature-restrictions");
        options.addArguments("disable-geolocation");
//        options.setBinary("C:\\Program Files (x86)\\Google\\Chrome Beta\\Application\\chrome.exe");
        WebDriver driver = new ChromeDriver(options);
        return driver;
    }

    private WebDriver initFirefoxDriver() {
        System.out.println("Launching Firefox browser..");

        WebDriverManager.firefoxdriver().setup();
        FirefoxProfile geoDisabled = new FirefoxProfile();
        geoDisabled.setPreference("geo.enabled", true);
        geoDisabled.setPreference("geo.provider.use_corelocation", true);
        geoDisabled.setPreference("geo.prompt.testing", true);
        geoDisabled.setPreference("geo.prompt.testing.allow", true);
        FirefoxOptions firefoxOptions = new FirefoxOptions().setProfile(geoDisabled);
        firefoxOptions.setCapability(CapabilityType.SUPPORTS_ALERTS, true);
        firefoxOptions.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, PageLoadStrategy.NORMAL);
        WebDriver driver = new FirefoxDriver(firefoxOptions);
        return driver;
    }

    private WebDriver initSafariDriver()     {
        System.out.println("Launching Safari browser..");
        SafariOptions safariOptions = new SafariOptions();
        safariOptions.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, PageLoadStrategy.NORMAL);
//        safariOptions.setCapability(SaCapabilityType.SUPPORTS_ALERTS, true);
        WebDriver driver = new SafariDriver(safariOptions);
        return driver;
    }

    public WebDriver setDriver(String browserType, String appURL) {
        WebDriver driver = null;
        if ("chrome".equalsIgnoreCase(browserType)) {
            driver = initChromeDriver();
        } else if ("firefox".equalsIgnoreCase(browserType)) {
            driver = initFirefoxDriver();
        } else if ("safari".equalsIgnoreCase(browserType)) {
            driver = initSafariDriver();
        } else {
            System.out.println("browser : " + browserType + " is invalid");
        }
        driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.navigate().to(appURL);
        return driver;
    }
}
