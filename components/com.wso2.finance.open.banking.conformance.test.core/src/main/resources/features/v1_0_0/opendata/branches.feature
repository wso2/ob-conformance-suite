Feature: Get Branches

  @OpenDataAPISpec @2.1 @data
 Scenario: Get the details of all branches of a bank
   Given a request is initiated to BRANCH endpoint
   When a user retrieves the branch details
   Then response json data should be compliant to the standard

  @OpenDataAPISpec @2.2 @security
  Scenario: Get the details of all branches of a bank2
    Given a request is initiated to BRANCH endpoint
    When a user retrieves the branch details
    Then response json data should be compliant to the standard

  @OpenDataAPISpec @2.3 @data
  Scenario: Get the details of all branches of a bank3
    Given a request is initiated to BRANCH endpoint
    When a user retrieves the branch details
    Then response json data should be compliant to the standard