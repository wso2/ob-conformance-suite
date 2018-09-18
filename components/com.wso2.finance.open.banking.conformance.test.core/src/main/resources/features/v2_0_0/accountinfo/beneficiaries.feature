Feature: Beneficiaries endpoint

  @AccountAndTransactionAPI @9.1.8 @security
  Scenario: Get Details on Account Beneficiaries
    Given TPP wants to access beneficiary details of an account
    When user provides his consent by clicking on redirect url
    Then TPP receives the authorization code from the authorization endpoint
    Then TPP requests and receives an access token from token endpoint
    Then TPP initiates a request to Beneficiaries endpoint
    And TPP receives the beneficiary details of the given account