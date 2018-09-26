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

package com.wso2.finance.open.banking.conformance.test.core.oidc;

import com.google.gson.Gson;
import com.wso2.finance.open.banking.conformance.test.core.request.RequestGenerator;
import com.wso2.finance.open.banking.conformance.test.core.request.TokenEndPointRequestGenerator;
import com.wso2.finance.open.banking.conformance.test.core.utilities.Log;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
/**
 * Helper for Handling OIDC flow.
 */
public class OIDCHandler {

    class TokenEndResponse {

        private String accessToken;
        private String refreshToken;
        private String scope;
        private String tokenType;
        private int expiredIn;

        public String getAccessToken() {

            return accessToken;
        }
    }

    private String clientID = "";         //client username
    private String clientSecret = "";     //client password
    private String callbackURL = "";    // eg: "https://openbanking.wso2.com/authenticationendpoint/authorize_callback.do";
    private String authEnd = "";        //eg: "https://api-openbanking.wso2.com/AuthorizeAPI/v1.0.0/";
    private String tokenEnd = "";       //eg: https://api-openbanking.wso2.com/TokenAPI/v1.0.0/
    private String authCode = "";       //Authorization code received from authorization endpoint

    /**
     *
     * @param clientID
     * @param clientSecret
     * @param authEnd
     * @param callbackURL
     * @param tokenEnd
     */
    public OIDCHandler(String clientID, String clientSecret, String authEnd, String callbackURL, String tokenEnd) {

        this.clientID = clientID;
        this.clientSecret = clientSecret;
        this.authEnd = authEnd;
        this.callbackURL = callbackURL;
        this.tokenEnd = tokenEnd;
    }

    /**
     *
     * @param state
     * @return
     */
    public String createAuthUrlForUserContent(String state) {

        String url = authEnd + "?response_type=code&scope=accounts payments&state=" + state + "&client_id=" + clientID + "&redirect_uri=" + callbackURL;
        Log.info(url);
        return url;
    }

    /**
     *
     * @return
     */
    public String getAccessTokenByAuthorizationCode() {

        RequestGenerator reqGenerator = new TokenEndPointRequestGenerator();
        RequestSpecification req = reqGenerator.generate();

        Log.info("Token EndPoint Request: " + req.toString());

        Response response = given().spec(req).when().post();

        Log.info("Token EndPoint Response: " + response.getBody().asString());

        Gson gson = new Gson();
        return gson.fromJson(response.getBody().asString(), TokenEndResponse.class).getAccessToken();

    }


    /**
     *
     * @return
     */
    public String getAccessTokenByClientCredintials()
    {
        return "";

    }


    /**
     *
     * @param authCode
     */
    public void setAuthCode(String authCode){
        this.authCode = authCode;
    }
}
