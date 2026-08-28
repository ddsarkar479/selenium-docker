package com.debautomation.tests.vendorportal;

import com.debautomation.pages.vendorportal.DashboardPage;
import com.debautomation.pages.vendorportal.LoginPage;
import com.debautomation.tests.AbstractBaseTest;
import com.debautomation.tests.vendorportal.model.VendorPortalTestData;
import com.debautomation.util.Config;
import com.debautomation.util.Constants;
import com.debautomation.util.JsonUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class VendorPortalTest extends AbstractBaseTest {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private VendorPortalTestData testData;
    @BeforeTest
    @Parameters("testDataPath")
    public void setPageObjects(String testDataPath){
        this.loginPage = new LoginPage(driver);
        this.dashboardPage = new DashboardPage(driver);
        this.testData = JsonUtil.getTestData(testDataPath, VendorPortalTestData.class);
    }
    @Test
    public void loginTest(){

       // LoginPage loginPage = new LoginPage(driver);
       // loginPage.goTo("https://d1uh9e7cu07ukd.cloudfront.net/selenium-docker/vendor-app/index.html");
        loginPage.goTo(Config.get(Constants.VENODOR_PORTAL_URL));
        Assert.assertTrue(loginPage.isAt());
        loginPage.login(testData.getUsername(),testData.getPassword());
    }
    @Test(dependsOnMethods = "loginTest")
    public void dashBoardTest(){
       // DashboardPage dashboardPage = new DashboardPage(driver);
        Assert.assertTrue(dashboardPage.isAt());

        //finance matrics
        Assert.assertEquals(dashboardPage.getMonthlyEarning(),testData.getMonthlyEarning());
        Assert.assertEquals(dashboardPage.getAnualEarning(),testData.getAnualEarning());
        Assert.assertEquals(dashboardPage.getProfitMargin(),testData.getProfitMargin());
        Assert.assertEquals(dashboardPage.getAvailableInventory(),testData.getAvailableInventory());
        //Order history search
        dashboardPage.getOrderHistory(testData.getSearchKeyword());
        Assert.assertEquals(dashboardPage.getsearchResultContent(),testData.getSearchResultCount());

       // dashboardPage.logout();
    }

    @Test(dependsOnMethods = "dashBoardTest")
    public void logoutTest(){
        dashboardPage.logout();
        Assert.assertTrue(loginPage.isAt());
    }

}
