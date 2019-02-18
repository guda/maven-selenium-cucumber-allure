Feature: search Wikipedia

  Background:
    Given Open page "https://www.google.ba"

  Scenario: Google Cucumber search
    Given Enter google search term 'Cucumber'
    When Do Google search
    Then Cucumber link is shown
