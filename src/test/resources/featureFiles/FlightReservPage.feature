Feature: Verify the flight reservation page
  @regression
  Scenario: Verify Page title on Flight reservation page
    Given User opens the "chrome" browser
    And Navigate to url "blazedemo"
    And User select "Paris" from departure city dropdown
    And User select "Cairo" from destination city dropdown
    When User click on search flight button
    Then User verify flight reservation page
    Then User quit the browser