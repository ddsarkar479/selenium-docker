package com.debautomation.tests.flightreservation;

import com.debautomation.pages.flightreservation.*;
import com.debautomation.tests.AbstractBaseTest;
import com.debautomation.tests.flightreservation.model.FlightReservationTestData;
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

public class FlightreservationTest extends AbstractBaseTest {

    private String noOfPassenger;
    private String expectedPrice;
    private FlightReservationTestData testData;

    @BeforeTest
    @Parameters("testDataPath")
    public void setParameters(String testDataPath){

        this.testData = JsonUtil.getTestData(testDataPath, FlightReservationTestData.class);
    }

    @Test
    public void userRegistrationTest(){
        RegistrationPage registrationPage = new RegistrationPage(driver);
        //registrationPage.goTo("https://d1uh9e7cu07ukd.cloudfront.net/selenium-docker/reservation-app/index.html");
        registrationPage.goTo(Config.get(Constants.FLIGHT_RESERVATION_URL));
        Assert.assertTrue(registrationPage.isAt());

        registrationPage.enterDetails(testData.getFirstName(),testData.getLastName());
        registrationPage.enterCredentials(testData.getEmail(),testData.getPassword());
        registrationPage.enterAddress(testData.getStreet(),testData.getCity(),testData.getZip());
        registrationPage.register();
    }

    @Test(dependsOnMethods = "userRegistrationTest")
    public void registrationConfirmationTest(){
        RegistrationConfirmationPage registrationConfirmationPage = new RegistrationConfirmationPage(driver);
        Assert.assertTrue(registrationConfirmationPage.isAt());
        Assert.assertEquals(registrationConfirmationPage.getFirstName(),testData.getFirstName());
        registrationConfirmationPage.goToFlightSearch();

    }
    @Test(dependsOnMethods = "registrationConfirmationTest")
    public void flightSearchTest(){
        FlightSearchPage flightSearchPage = new FlightSearchPage(driver);
        Assert.assertTrue(flightSearchPage.isAt());
        flightSearchPage.selectPassenger(testData.getPassengerCount());
        flightSearchPage.searchFlight();

    }
    @Test(dependsOnMethods = "flightSearchTest")
    public void flightSelectionTest(){
        FlightSelectionPage flightSelectionPage =new FlightSelectionPage(driver);
        Assert.assertTrue(flightSelectionPage.isAt());
        flightSelectionPage.selectFlifhts();
        flightSelectionPage.confirmFlight();
    }
    @Test(dependsOnMethods = "flightSelectionTest")
    public void flightConfirmationTest(){

        FlightConfirmationPage flightConfirmationPage = new FlightConfirmationPage(driver);
        Assert.assertTrue(flightConfirmationPage.isAt());
        Assert.assertEquals(flightConfirmationPage.getPrice(),testData.getExpectedPrice());
    }

}
