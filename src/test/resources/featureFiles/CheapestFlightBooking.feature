Feature: Book the Cheapest flight from Boston to Rome on Chrome
  @sanity @regression
  Scenario Outline:: Book the Cheapest flight from Boston to Rome and Portland to London
    Given User opens the "<browser>" browser
    And Navigate to url "<website>"
    And User select "<departureCity>" from departure city dropdown
    And User select "<destinationCity>" from destination city dropdown
    When User click on search flight button
    When User select cheapest flight from flight list
    And User fill the details to purchase ticket
    Then User verify confirmation page and save confirmation Id
    Then User quit the browser
    Examples:
      |browser |website  |departureCity|destinationCity|
      |chrome  |blazedemo|Boston       |Rome           |
      |Extent.propertiesfirefox |blazedemo|Portland     |London         |