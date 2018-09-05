Feature: Get Branches
 Scenario: Get the details of all branches of a bank
   Given a request is initiated to BRANCH endpoint
   When a user retrieves the branch details
   Then response json data should be compliant to the standard