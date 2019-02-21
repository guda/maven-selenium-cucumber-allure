package com.guda.ui.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@SuppressWarnings("unused")
public class KlixPage {

    @FindBy(linkText = "Vijesti")
    private WebElement vijesti;

    public boolean vijestiLinkVisibleOnPage() {
        return vijesti.isDisplayed();
    }

    public void clickVijesti(String searchTerm) {
        vijesti.click();
    }
}
