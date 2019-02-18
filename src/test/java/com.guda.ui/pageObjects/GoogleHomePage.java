package com.guda.ui.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@SuppressWarnings("unused")
public class GoogleHomePage {

    @FindBy(xpath = "//*[@id=\"tsf\"]/div[2]/div/div[1]/div/div[1]/input")
    private WebElement searchInputField;

    @FindBy(xpath = "//*[@id=\"tsf\"]/div[2]/div/div[3]/center/input[1]")
    private WebElement searchButton;

    @FindBy(linkText = "Cucumber")
    private WebElement cucumberResultLink;

    public void sendKeysToSearchInputField(String searchTerm) {
        searchInputField.sendKeys(searchTerm);
    }

    public void clickGoogleSearchButton() {
        searchButton.click();
    }

    public String getCucumberResultLink() {
        return cucumberResultLink.getAttribute("href");
    }
}
