Feature: Beneficiaries endpoint

  @AccountAndTransactionAPI @9.1.8 @security @data
  Scenario: Get Details on Account Beneficiaries
    Given user is directed to the auth endpoint to get the consent
    When TPP receives the authorization code
    Then TPP requests an access token from token endpoint
    When user initiates a request to Beneficiaries endpoint
    Then user receives the beneficiaries details for the given account