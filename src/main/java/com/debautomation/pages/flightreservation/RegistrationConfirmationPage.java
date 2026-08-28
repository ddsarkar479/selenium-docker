package com.debautomation.pages.flightreservation;

import com.debautomation.pages.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegistrationConfirmationPage extends AbstractPage {

    @FindBy(id = "go-to-flights-search")
    private WebElement gotoflightssearchButton;

    @FindBy(xpath = "//p[@class='mt-3']/b")
    private WebElement firstNameElement;

    public RegistrationConfirmationPage(WebDriver driver){
        super(driver);
    }

    @Override
    public boolean isAt() {
        this.wait.until(ExpectedConditions.visibilityOf(gotoflightssearchButton));
        return this.gotoflightssearchButton.isDisplayed();
    }

    public String getFirstName(){

        return firstNameElement.getText();
    }

    public void goToFlightSearch(){

        this.gotoflightssearchButton.click();
    }
}
