Feature: Get ATM Details
  Scenario: Get the details of all atms owned by a bank
    Given a user provided a bankID
    When a user retrieves the atm details
    Then response data should be compliant to the standard