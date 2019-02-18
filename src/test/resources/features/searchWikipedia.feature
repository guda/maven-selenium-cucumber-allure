Feature: search Wikipedia

  Background:
    Given Open page "http://en.wikipedia.org"

  Scenario: direct search article
    Given Enter search term 'Cucumber'
    When Do search
    Then Single result is shown for 'Cucumber'