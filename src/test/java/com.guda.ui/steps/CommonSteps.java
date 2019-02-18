package com.guda.ui.steps;

import cucumber.api.java.en.Given;

import static com.guda.ui.util.Hooks.getDriver;

public class CommonSteps {
    @Given("^Open page \"([^\"]*)\"$")
    public void openPage(String url) {
        getDriver().get(url);
    }
}
