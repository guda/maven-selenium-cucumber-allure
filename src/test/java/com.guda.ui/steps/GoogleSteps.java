package com.guda.ui.steps;

import com.guda.ui.pageObjects.GoogleHomePage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.support.PageFactory;

import static com.guda.ui.util.Hooks.getDriver;
import static org.junit.Assert.assertEquals;

public class GoogleSteps {

    private GoogleHomePage googleHomePage = PageFactory.initElements(getDriver(), GoogleHomePage.class);

    @Given("^Enter google search term '(.*?)'$")
    public void searchFor(String searchTerm) {
        googleHomePage.sendKeysToSearchInputField(searchTerm);
    }

    @When("^Do Google search$")
    public void clickSearchButton() {
        googleHomePage.clickGoogleSearchButton();
    }

    @Then("^Cucumber link is shown$")
    public void assertSingleResult() {
        assertEquals("href of Cucumber link result is not 'https://docs.cucumber.io/'", "https://docs.cucumber.io/", googleHomePage.getCucumberResultLink());
    }
}
