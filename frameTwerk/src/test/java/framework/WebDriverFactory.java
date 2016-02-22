package framework;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * Created by sterlingg on 11/25/2015.
 */
public class WebDriverFactory {
    private static BrowserType browserType;
    private static ResourceBundle _prop = ResourceBundle.getBundle("dev");

    public WebDriverFactory() {
    }

    public static WebDriver initWebDriver() throws MalformedURLException {
        WebDriver webDriver = getWebDriver();
        configDriverOptions(webDriver);
        return webDriver;
    }

    private static WebDriver getWebDriver() throws MalformedURLException {
        setBrowserSelection();

        System.out.println("Current Operating System: " + System.getProperties().getProperty("os.name"));
        System.out.println("Current Architecture: " + System.getProperties().getProperty("os.arch"));
        System.out.println("Current Browser Selection: " + browserType);

        //Load standalone executable if required
        switch (browserType) {
            case CHROME:
                if (System.getProperties().getProperty("os.arch").toLowerCase().equals("x86_64") || System.getProperties().getProperty("os.arch").toLowerCase().equals("amd64")) {
                    if (System.getProperties().getProperty("os.name").toLowerCase().contains("windows")) {
                        System.setProperty("webdriver.chrome.driver", _prop.getString("binaryRootFolder") + "/windows/googlechrome/64bit/2.20/chromedriver.exe");
                    } else if (System.getProperties().getProperty("os.name").toLowerCase().contains("mac")) {
                        System.setProperty("webdriver.chrome.driver", _prop.getString("binaryRootFolder") + "/osx/googlechrome/64bit/2.20/chromedriver");
                    } else if (System.getProperties().getProperty("os.name").toLowerCase().contains("linux")) {
                        System.setProperty("webdriver.chrome.driver", _prop.getString("binaryRootFolder") + "/linux/googlechrome/64bit/2.20/chromedriver");
                    }
                } else {
                    if (System.getProperties().getProperty("os.name").toLowerCase().contains("windows")) {
                        System.setProperty("webdriver.chrome.driver", _prop.getString("binaryRootFolder") + "/windows/googlechrome/32bit/2.20/chromedriver.exe");
                    } else if (System.getProperties().getProperty("os.name").toLowerCase().contains("mac")) {
                        System.setProperty("webdriver.chrome.driver", _prop.getString("binaryRootFolder") + "/osx/googlechrome/32bit/2.20/chromedriver");
                    } else if (System.getProperties().getProperty("os.name").toLowerCase().contains("linux")) {
                        System.setProperty("webdriver.chrome.driver", _prop.getString("binaryRootFolder") + "/linux/googlechrome/32bit/2.20/chromedriver");
                    }
                }
                break;
        }

        //Instantiate driver object
        URL appiumAddress = new URL("http://127.0.0.1:4723/wd/hub");
        switch (browserType) {
            case FIREFOX:
                return new FirefoxDriver(generateDesiredCapabilities(browserType));
            case CHROME:
                return new ChromeDriver(generateDesiredCapabilities(browserType));
            case REMOTE:
                return new RemoteWebDriver(generateDesiredCapabilities(browserType));
            case ANDROID:
                return new AndroidDriver<MobileElement>(appiumAddress, generateDesiredCapabilities(browserType));
            case IOS:
                return new IOSDriver<MobileElement>(appiumAddress, generateDesiredCapabilities(browserType));
            default:
                return new ChromeDriver(generateDesiredCapabilities(BrowserType.CHROME));
        }
    }

    private static DesiredCapabilities generateDesiredCapabilities(BrowserType browserType) {
        DesiredCapabilities capabilities;

        switch (browserType) {
            case CHROME:
                capabilities = DesiredCapabilities.chrome();
                capabilities.setCapability("chrome.switches", Arrays.asList("--no-default-browser-check"));
                HashMap<String, String> chromePreferences = new HashMap<String, String>();
                chromePreferences.put("profile.password_manager_enabled", "false");
                chromePreferences.put("download.prompt_for_download", "false");
                //This line below sets the directory where files will be automatically downloaded to
                //Directory currently does not exist in this repo and therefore is commented out in order for compilation success
//                chromePreferences.put("download.default_directory", _prop.getString("downloadsFolder"));
                chromePreferences.put("download.directory_upgrade", "true");
                ChromeOptions options = new ChromeOptions();
                HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
                options.setExperimentalOption("prefs", chromePreferences);
                options.addArguments("--test-type");
                capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                break;
            case FIREFOX:
                capabilities = DesiredCapabilities.firefox();
                break;
            case ANDROID:
                capabilities = new DesiredCapabilities();
                break;
            case IOS:
                capabilities = new DesiredCapabilities();
                capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.1");
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 5s");
                break;
            default:
                capabilities = DesiredCapabilities.firefox();
        }
        return capabilities;
    }

    private static void configDriverOptions(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
    }

    private static void setBrowserSelection() {
        for (BrowserType browser : BrowserType.values()) {
            if (browser.toString().toLowerCase().equals(_prop.getString("browser").toLowerCase())) {
                browserType = browser;
            }
        }
        if (browserType == null) {
            System.err.println("Unknown browser specified, defaulting to 'Chrome'...");
            browserType = BrowserType.CHROME;
        }
    }
}
