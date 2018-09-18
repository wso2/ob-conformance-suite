
/*
 * Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.wso2.finance.open.banking.conformance.test.core.steps.v1_0_0;
import static org.junit.Assert.*;
import com.wso2.finance.open.banking.conformance.test.core.Context;
import com.wso2.finance.open.banking.conformance.test.core.utilities.Log;
import com.wso2.finance.open.banking.conformance.test.core.utilities.Constants;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import com.wso2.finance.open.banking.conformance.test.core.response.ResponseValidator;
import com.wso2.finance.open.banking.conformance.test.core.request.RequestGenerator;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class OpenDataSteps {

    private RequestGenerator requestGenerator = new RequestGenerator();
    private ResponseValidator responseValidator = new ResponseValidator();
    private  RequestSpecification httpRequest;
    private  Response response;

    private String ENDPOINT_GET_ATMS_BY_BANK_ID;
    private String ENDPOINT_GET_BRANCHES_BY_BANK_ID;
    private String ENDPOINT_GET_PRODUCTS_BY_BANK_ID;

    @Given("a request is initiated to ATM endpoint")
    public void createRequestToAtmEndpoint()
    {
      ENDPOINT_GET_ATMS_BY_BANK_ID =  "/v1.0.0/banks/" + Context.getInstance().getCurrentFeatureAttribute("uri", "bank_id") + "/atms";
      httpRequest = requestGenerator.createRequest(Constants.ATM_END_POINT);
    }

    @When("a user retrieves the atm details")
    public void getAtmDetails(){
        response = httpRequest.request(Method.GET,ENDPOINT_GET_ATMS_BY_BANK_ID);
        Log.info("response: " + response.getBody().asString());
    }

    @Then("response json data should be compliant to the standard")
    public void validateResponse()
    {
        responseValidator.validateResponse();
    }

    @Given("a request is initiated to BRANCH endpoint")
    public void createRequestToBranchEndpoint()
    {
        ENDPOINT_GET_BRANCHES_BY_BANK_ID =  "/v1.0.0/banks/" + Context.getInstance().getCurrentFeatureAttribute("uri", "bank_id") + "/branches";
        httpRequest = requestGenerator.createRequest(Constants.BRANCH_END_POINT);
    }

    @When("a user retrieves the branch details")
    public void getBranchDetails(){
        response = httpRequest.request(Method.GET,ENDPOINT_GET_BRANCHES_BY_BANK_ID);
        Log.info("response: " + response.getBody().asString());
    }

    @Given("a request is initiated to PRODUCT endpoint")
    public void createRequestToProductEndpoint()
    {
        ENDPOINT_GET_PRODUCTS_BY_BANK_ID =  "/v1.0.0/banks/" + Context.getInstance().getCurrentFeatureAttribute("uri", "bank_id") + "/products";
        httpRequest = requestGenerator.createRequest(Constants.PRODUCT_END_POINT);
    }

    @When("a user retrieves the product details")
    public void getProductDetails(){
        response = httpRequest.request(Method.GET,ENDPOINT_GET_PRODUCTS_BY_BANK_ID);
        Log.info("response: " + response.getBody().asString());
    }

    @Then("response json data body should contain geo-location of the bank")
    public void validateGeoLocation(){
        assertEquals("Response does not contain geo-location of the bank",false,true);
    }


}
