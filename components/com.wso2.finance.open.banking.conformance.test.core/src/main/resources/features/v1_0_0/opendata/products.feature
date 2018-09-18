Feature: Get PRODUCT Details

  @OpenDataAPISpec @3.1 @data
  Scenario: Get the details of all products which are supported by bank
    Given a request is initiated to PRODUCT endpoint
    When a user retrieves the product details
    Then response json data should be compliant to the standard

  @OpenDataAPISpec @3.2 @security
  Scenario: Get the details of all products which are supported by bank2
    Given a request is initiated to PRODUCT endpoint
    When a user retrieves the product details
    Then response json data should be compliant to the standard

  @OpenDataAPISpec @3.3 @data
  Scenario: Get the details of all products which are supported by bank3
    Given a request is initiated to PRODUCT endpoint
    When a user retrieves the product details
    Then response json data should be compliant to the standard