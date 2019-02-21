package com.guda.ui.steps;

import com.guda.ui.pageObjects.KlixPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.support.PageFactory;

import java.util.Objects;

import static com.guda.ui.util.Hooks.getDriver;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class KlixSteps {

    private KlixPage klixPage = PageFactory.initElements(getDriver(), KlixPage.class);

    @Given("^User is on page with title '(.*?)'$")
    public void verifyKlixHomePage(String pageTitle) {
        assertEquals(pageTitle, Objects.requireNonNull(getDriver()).getTitle());
    }

    @Then("^Vijesti link is shown$")
    public void assertVijestiLinkVisibleOnPage() {
        assertTrue("", klixPage.vijestiLinkVisibleOnPage());
    }
}
