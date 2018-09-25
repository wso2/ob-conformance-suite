
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

package com.wso2.finance.open.banking.conformance.test.core.request;

import com.wso2.finance.open.banking.conformance.test.core.context.Context;
import com.wso2.finance.open.banking.conformance.test.core.constants.Constants;
import com.wso2.finance.open.banking.conformance.test.core.utilities.Log;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.builder.RequestSpecBuilder;
import com.atlassian.oai.validator.restassured.SwaggerValidationFilter;


public class RequestGenerator {

    /*public static RequestSpecification createRequest(String Endpoint, String Version){
        retun new RequestSpecification();
    }*/


    public RequestSpecification createRequest(String endPoint) {
        Log.info("Generating Request for " + endPoint);

        String SWAGGER_JSON_FILE = Context.getInstance().getCurrentSwaggerJsonFile();
        SwaggerValidationFilter validationFilter = new SwaggerValidationFilter(SWAGGER_JSON_FILE);

        RestAssured.baseURI = Context.getInstance().getCurrentSpecAttribute("default","base_url");
        return RestAssured.given().accept("application/json").filter(validationFilter);
    }


    public RequestSpecification createRequestForTokenEndPoint(String endPoint) {
        Log.info("Generating Request for token endpoint " + endPoint);

        //String SWAGGER_JSON_FILE = Context.getInstance().getSwaggerJsonFile();
        //SwaggerValidationFilter validationFilter = new SwaggerValidationFilter(SWAGGER_JSON_FILE);
        //client_id=MGw0ych4DOR9Fz_m6xwEWLdIMjQa&grant_type=authorization_code&code=4bf4c166-32ab-373a-ae11-38ff438806af&scope=accounts payments&redirect_uri=https://openbanking.wso2.com/authenticationendpoint/authorize_callback.do"

        // curl -v -X POST --basic
        // -u MGw0ych4DOR9Fz_m6xwEWLdIMjQa:1ZFZuUU9xBFr7MxaP5V0XutuTRga
        // -H "Content-Type: application/x-www-form-urlencoded;charset=UTF-8" -k
        // -d "client_id=MGw0ych4DOR9Fz_m6xwEWLdIMjQa&grant_type=authorization_code&code=4bf4c166-32ab-373a-ae11-38ff438806af&scope=accounts payments&redirect_uri=https://openbanking.wso2.com/authenticationendpoint/authorize_callback.do"
        // https://api-openbanking.wso2.com/TokenAPI/v1.0.0/


        String clientID = Context.getInstance().getCurrentSpecAttribute("client","consumer key");
        String clientSecret = Context.getInstance().getCurrentSpecAttribute("client","consumer secret");
        String authCode = Context.getInstance().getAttributesFromTempMap("auth_code");

        String requestBody="client_id="+clientID+"&grant_type=authorization_code&code="+authCode+"&scope=accounts payments&redirect_uri=http://localhost:9090/testplan/callback";

        Log.info("Token End Point Request Body: " + requestBody);

        String SWAGGER_JSON_FILE = Context.getInstance().getSwaggerJsonFile(Constants.TOKEN_API_SPEC+"v1.0.0");
        SwaggerValidationFilter validationFilter = new SwaggerValidationFilter(SWAGGER_JSON_FILE);

        RestAssured.baseURI = endPoint;
        return RestAssured.given()
                .auth().preemptive().basic(clientID,clientSecret)
                .accept("application/json")
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .body(requestBody)
                .filter(validationFilter);
    }

}
