package com.debautomation.pages.vendorportal;

import com.debautomation.pages.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DashboardPage extends AbstractPage{

    private static final Logger log = LoggerFactory.getLogger(DashboardPage.class);

    @FindBy(id = "monthly-earning")
    private WebElement monthlyEarning;

    @FindBy(id = "annual-earning")
    private WebElement anualEarning;

    @FindBy(id = "profit-margin")
    private WebElement profitMargin;

    @FindBy(id = "available-inventory")
    private WebElement availableInventory;

    @FindBy(xpath = "//div[@id='dataTable_filter']//input")
    private WebElement searchInput;

    @FindBy(id = "dataTable_info")
    private WebElement dataTableInfo;

    @FindBy(xpath = "//img[@class='img-profile rounded-circle']")
    private WebElement profilePicture;

    @FindBy(xpath = "//a[i[contains(@class,'fa-sign-out-alt')]]")
    private WebElement logoutButton;

    @FindBy(xpath = "//a[text()='Logout']")
    private WebElement modalLogoutButton;
    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAt() {

        this.wait.until(ExpectedConditions.visibilityOf(monthlyEarning));
        return this.monthlyEarning.isDisplayed();
    }

    public String getMonthlyEarning(){
        return this.monthlyEarning.getText();
    }

    public String getAnualEarning(){
        return this.anualEarning.getText();
    }

    public String getProfitMargin(){
        return this.profitMargin.getText();
    }

    public String getAvailableInventory(){
        return this.availableInventory.getText();
    }

    public void getOrderHistory(String keyword){
        this.searchInput.sendKeys(keyword);
    }

    public int getsearchResultContent(){

        String resultText = this.dataTableInfo.getText();
        String []arr = resultText.split(" ");
        int count = Integer.parseInt(arr[5]);
        log.info("Result count {}",count);
        return count;
    }

    public void logout(){

        this.profilePicture.click();
        this.wait.until(ExpectedConditions.visibilityOf(logoutButton));
        this.logoutButton.click();
        this.wait.until(ExpectedConditions.visibilityOf(modalLogoutButton));
        this.modalLogoutButton.click();
    }
}
