/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.finance.open.banking.conformance.test.core.request;

import com.atlassian.oai.validator.restassured.SwaggerValidationFilter;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;
import org.wso2.finance.open.banking.conformance.test.core.constants.Constants;
import org.wso2.finance.open.banking.conformance.test.core.context.Context;

/**
 * Helper class for generating api request to ATM endpoint.
 */
public class TokenEndPointRequestGenerator extends RequestGenerator {

    private static Logger log = Logger.getLogger(TokenEndPointRequestGenerator.class);

    private String grantType;
    private String scope;
    private String redirectURL;
    private String tokenEnd;

    /**
     * @param grantType authorization grant type
     * @param scope consent scope
     * @param redirectURL Redirect URL to receive authorization code
     * @param tokenEnd token endpoint URL
     */
    public TokenEndPointRequestGenerator(String grantType, String scope, String redirectURL, String tokenEnd) {

        this.grantType = grantType;
        this.scope = scope;
        this.redirectURL = redirectURL;
        this.tokenEnd = tokenEnd;
    }

    /**
     * Generate RequestSpecification for the Token Endpoint.
     * @return RequestSpecification
     */
    @Override
    public RequestSpecification generate() {

        log.debug("Generating Request for Token End Point");

        String clientID = Context.getInstance()
                .getCurrentSpecAttribute("client", "consumer key");
        String clientSecret = Context.getInstance()
                .getCurrentSpecAttribute("client", "consumer secret");
        String authCode = Context.getInstance().getAttributesFromTempMap("auth_code");

        String requestBody = "client_id=" + clientID + "&grant_type=" + grantType + "&code="
                + authCode + "&scope=" + scope + "&redirect_uri=" + redirectURL;

        log.debug("Token End Point Request Body: " + requestBody);

        String swaggerJsonFile = Context.getInstance().getSwaggerJsonFile(Constants.TOKEN_API_SPEC);
        SwaggerValidationFilter validationFilter = new SwaggerValidationFilter(swaggerJsonFile);

        RequestSpecification spec = this.setBaseUri(tokenEnd).
                setAccept("application/json").
                setContentType("application/x-www-form-urlencoded;charset=UTF-8").
                setBody(requestBody).
                addFilter(validationFilter).build();

        return spec.auth().preemptive().basic(clientID, clientSecret);

    }
}
