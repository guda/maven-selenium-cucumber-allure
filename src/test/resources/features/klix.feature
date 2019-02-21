Feature: Navigate Klix

  Background:
    Given Open page "https://www.klix.ba/"

  Scenario: Klix navigate
    Given User is on page with title 'Klix.ba | Saznaj više'
    Then Vijesti link is shown