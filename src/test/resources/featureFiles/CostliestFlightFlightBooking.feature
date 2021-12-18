Feature: Book the Costliest flight from Portland to New York on Firefox
  @regression
  Scenario Outline:: Book the Costliest flight from Portland to New York
    Given User opens the "<browser>" browser
    And Navigate to url "<website>"
    And User select "<departureCity>" from departure city dropdown
    And User select "<destinationCity>" from destination city dropdown
    When User click on search flight button
    When User select costliest flight from flight list
    And User fill the details to purchase ticket
    Then User verify confirmation page and save confirmation Id
    Then User quit the browser
    Examples:
      |browser|website|departureCity|destinationCity|
      |firefox |blazedemo|Portland|New York|