package com.guda.ui.steps;

import com.guda.ui.pageObjects.WikipediaPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.support.PageFactory;

import static com.guda.ui.util.Hooks.getDriver;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class WikipediaSteps {

    private WikipediaPage wikipediaPage = PageFactory.initElements(getDriver(), WikipediaPage.class);

    @Given("^Enter search term '(.*?)'$")
    public void searchFor(String searchTerm) {
        wikipediaPage.sendKeysToSearchInputField(searchTerm);
    }

    @When("^Do search$")
    public void clickSearchButton() {
        wikipediaPage.clickSearchButton();
    }

    @Then("^Single result is shown for '(.*?)'$")
    public void assertSingleResult(String searchResult) {
        String resultText = wikipediaPage.getResultsText();

        assertFalse(resultText.contains(searchResult + " may refer to:"));
        assertTrue("'" + resultText + "' text does not start with '" + searchResult + "'", resultText.startsWith(searchResult));
    }
}
