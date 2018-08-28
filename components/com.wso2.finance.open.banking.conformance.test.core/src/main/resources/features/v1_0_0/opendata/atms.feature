Feature: Get ATM Details
  Scenario: Get the details of all atms owned by a bank
    Given a request is initiated to ATM endpoint
    When a user retrieves the atm details
    Then response json data should be compliant to the standard