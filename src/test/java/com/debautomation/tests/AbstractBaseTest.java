package com.debautomation.tests;
import com.debautomation.listener.TestListener;
import com.debautomation.util.Config;
import com.debautomation.util.Constants;
import com.google.common.util.concurrent.Uninterruptibles;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

@Listeners({TestListener.class})
public abstract class AbstractBaseTest {
    private static final Logger log = LoggerFactory.getLogger(AbstractBaseTest.class);

    protected WebDriver driver;
    @BeforeSuite
    public void setUp(){
        Config.initialize();
    }

    @BeforeTest
    @Parameters("browser")
    public void setDriver(String browser, ITestContext ctx) throws MalformedURLException {
        //if(Boolean.getBoolean("selenium.grid.enabled"))
        if (Boolean.parseBoolean(Config.get(Constants.GRID_ENABLED))){
            this.driver = getRemoteDriver(browser);
            ctx.setAttribute(Constants.DRIVER,this.driver);
            this.driver.manage().window().maximize();
        }
        else {
            this.driver=getLocalDriver(browser);
            this.driver.manage().window().maximize();
        }
    }
    // This is for selenium grid is true in pom.xml
    private WebDriver getRemoteDriver(String browser) throws MalformedURLException {

        //Capabilities capabilities;
        Capabilities capabilities = new ChromeOptions();
        if (Constants.FIREFOX.equalsIgnoreCase(Config.get(Constants.BROWSER))){
            capabilities = new FirefoxOptions();
        }
        //if (System.getProperty("browser").equalsIgnoreCase("chrome"))
//        if(browser.equalsIgnoreCase("chrome")){
//            capabilities = new ChromeOptions();
//        } else {
//            capabilities = new FirefoxOptions();
//        }
        String urlformat = Config.get(Constants.GRID_URL_FORMAT);
        String hubHost = Config.get(Constants.GRID_HUB_HOST);
        String url = String.format(urlformat,hubHost);
        log.info("Grid url {}",url);
        return new RemoteWebDriver(new URL(url),capabilities);
        //return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
    }

    // This is for local driver like chrome. If in pom.xml selenium.grid i s false
    private WebDriver getLocalDriver(String browser){
        //WebDriverManager.chromedriver().setup();
       // return new ChromeDriver();
        //if (System.getProperty("browser").equalsIgnoreCase("chrome"))
        if(browser.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver();
        } else {
            WebDriverManager.firefoxdriver().setup();
            return new FirefoxDriver();
        }
    }
    @AfterTest
    public void quitDriver(){
        this.driver.quit();
    }

    @AfterTest
    public void sleep(){

        Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));
    }
}


