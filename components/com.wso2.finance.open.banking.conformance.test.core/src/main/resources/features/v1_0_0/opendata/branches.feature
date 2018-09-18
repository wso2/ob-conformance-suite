Feature: Get Branches

  @OpenDataAPISpec @2.1 @data
 Scenario: Get the details of all branches of a bank
   Given a request is initiated to BRANCH endpoint
   When a user retrieves the branch details
   Then response json data should be compliant to the standard

  @OpenDataAPISpec @2.2 @data
  Scenario: Check the geo-location information of the branch
    Given a request is initiated to BRANCH endpoint
    When a user retrieves the branch details
    Then response json data should contain geo-location of the branch

