
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

import com.wso2.finance.open.banking.conformance.test.core.Context;
import com.wso2.finance.open.banking.conformance.test.core.utilities.Log;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.builder.RequestSpecBuilder;
import com.atlassian.oai.validator.restassured.SwaggerValidationFilter;


public class RequestGenerator {

    private RequestSpecBuilder build;
   // private String SWAGGER_JSON_FILE ;
   // private SwaggerValidationFilter validationFilter;// = new SwaggerValidationFilter(SWAGGER_JSON_URL);

    public RequestGenerator(){

          build = new RequestSpecBuilder();
    }

    public RequestSpecification createRequest(String endPoint)
    {
        Log.info("Generating Request for " + endPoint);

        String SWAGGER_JSON_FILE = Context.getInstance().getSwaggerJsonFile();
        SwaggerValidationFilter validationFilter = new SwaggerValidationFilter(SWAGGER_JSON_FILE);

        RestAssured.baseURI = Context.getInstance().getBaseURL();
        return RestAssured.given().accept("application/json").filter(validationFilter);
    }

    public RequestSpecification createRequestFromBuilder(String endPoint){

        Log.info("Generating Request for" + endPoint);
        build.setBaseUri(Context.getInstance().getBaseURL());
        build.setBasePath(Context.getInstance().getBasePath());
        build.setAccept("application/json");

        return build.build ();

    }

}
