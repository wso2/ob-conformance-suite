
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

package com.wso2.finance.open.banking.conformance.test.core.response;

import com.wso2.finance.open.banking.conformance.test.core.utilities.Log;

import io.restassured.response.Response;

import com.atlassian.oai.validator.restassured.SwaggerValidationFilter;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static io.restassured.RestAssured.given;

public class ResponseValidator {

    private static final String SWAGGER_JSON_URL = "./schema/v1_0_0/open_data.json";

    private final SwaggerValidationFilter validationFilter = new SwaggerValidationFilter(SWAGGER_JSON_URL);

    String path = "banks/bank-4020-01/branches";

    @Test
    public void testGetBranches() {
        given().
                filter(validationFilter).
                baseUri("https://api-openbanking.wso2.com/OpenBankAPI/v1.0.0").
//                basePath("/OpenBankAPI/v1.0.0").
        header("content-type", "application/json").
                pathParam("path", path).
                urlEncodingEnabled(false).
                when().
                get("{path}").
                then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void testGetAtms() {
        given().
                filter(validationFilter).
                baseUri("https://api-openbanking.wso2.com/OpenBankAPI/v1.0.0/").
                header("content-type", "application/json").
                when().
                get("banks/bank-4020-01/atms").
                then().
                assertThat().
                statusCode(200);
    }

//    @Test
//    public void testGetWithInvalidId() {
//        given().
//                filter(validationFilter).
//        when().
//                get("/pet/fido").
//        then().
//                assertThat().
//                statusCode(200);
//    }

    public ResponseValidator(){
      //  SWAGGER_JSON_PATH = "resources/schema/open_data.json";
      //  validationFilter = new SwaggerValidationFilter(SWAGGER_JSON_PATH);
    }

    public void validateResponse()
    {

        Log.info("validating response");

        Result result = JUnitCore.runClasses(com.wso2.finance.open.banking.conformance.test.core.response.ResponseValidator.class);

        System.out.println("Total Number of tests: ");
        System.out.println(result.getRunCount());

        System.out.println("Total Number of failed tests: ");
        System.out.println(result.getFailureCount());

        for (Failure failure : result.getFailures()) {
            System.out.println("Test : ");
            System.out.println(failure.toString());
        }

        System.out.println("Final");
        System.out.println(result.wasSuccessful());

    }
}
