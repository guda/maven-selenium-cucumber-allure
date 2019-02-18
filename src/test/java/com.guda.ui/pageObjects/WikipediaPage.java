package com.guda.ui.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@SuppressWarnings("unused")
public class WikipediaPage {

    @FindBy(id = "searchInput")
    private WebElement searchInputField;

    @FindBy(id = "searchButton")
    private WebElement searchButton;

    @FindBy(id = "firstHeading")
    private WebElement firstHeading;

    public void sendKeysToSearchInputField(String searchTerm) {
        searchInputField.sendKeys(searchTerm);
    }

    public void clickSearchButton() {
        searchButton.click();
    }

    public String getResultsText() {
        return firstHeading.getText();
    }
}
