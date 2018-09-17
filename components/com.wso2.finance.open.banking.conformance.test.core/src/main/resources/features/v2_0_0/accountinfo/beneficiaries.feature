Feature: Beneficiaries endpoint

  @AccountAndTransactionAPI @9.1.8 @security
  Scenario: Get Details on Account Beneficiaries
    Given TPP wants to access beneficiary details of an account
    And user provides his consent by clicking on redirect url
    When TPP receives the authorization code
    And TPP requests and receives an access token from token endpoint
    When TPP initiates a request to Beneficiaries endpoint
    Then TPP receives the beneficiary details of the given account