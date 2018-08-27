package com.wso2.finance.open.banking.conformance.test.core.steps.v1_0_0;

import com.wso2.finance.open.banking.conformance.test.core.Context;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import com.wso2.finance.open.banking.conformance.test.core.RequestGenerator;
import com.wso2.finance.open.banking.conformance.test.core.ResponseValidator;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class OpenDataSteps {

    private String bankID;
    private String ENDPOINT_GET_ATMS_BY_BANK_ID;
    private RequestGenerator requestGenerator = new RequestGenerator();
    private ResponseValidator responseValidator = new ResponseValidator();

    @Given("a user provided a bankID")
    public void setBankID()
    {
       bankID = Context.getInstance().getBankID();
       //https://api-openbanking.wso2.com/OpenBankAPI/v1.0.0/banks/bank-4020-01/atms
       ENDPOINT_GET_ATMS_BY_BANK_ID =  "/v1.0.0/banks/" + bankID + "/atms";
        System.out.println("End Point :" + ENDPOINT_GET_ATMS_BY_BANK_ID);
    }

    @When("a user retrieves the atm details")
    public void getAtmDetails(){
        RequestSpecification httprequest = requestGenerator.createRequest();
        //Response response = request.when().get(ENDPOINT_GET_ATMS_BY_BANK_ID); //httpRequest.request(Method.GET, "/Hyderabad");
        Response response = httprequest.request(Method.GET,ENDPOINT_GET_ATMS_BY_BANK_ID);
       // System.out.println(request.get());
        System.out.println("response: " + response.prettyPrint());
    }

    @Then("response data should be compliant to the standard")
    public void validateResponse()
    {
        responseValidator.validateResponse();
    }


}
