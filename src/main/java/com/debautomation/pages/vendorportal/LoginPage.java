package com.debautomation.pages.vendorportal;

import com.debautomation.pages.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends AbstractPage {


    @FindBy(id = "username")
    WebElement userNameInput;

    @FindBy(id = "password")
    WebElement passWordInput;

    @FindBy(id = "login")
    WebElement loginButton;
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAt() {
        this.wait.until(ExpectedConditions.visibilityOf(loginButton));
        return this.loginButton.isDisplayed();
    }

    public void goTo(String url){
        this.driver.get(url);
    }

    public void login(String username,String password){
        this.userNameInput.sendKeys(username);
        this.passWordInput.sendKeys(password);
        this.loginButton.click();
    }
}
