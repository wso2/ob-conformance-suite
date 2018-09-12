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

import com.wso2.finance.open.banking.conformance.test.core.Context;
import com.wso2.finance.open.banking.conformance.test.core.utilities.Log;

public class OIDCHandler {

    //enum Scope {ACCCOUNT};

    private String clientID="";
    private String clientSecret="";
    private String callbackURL = ""; // eg: "https://openbanking.wso2.com/authenticationendpoint/authorize_callback.do";
    private String authEnd = "";  //eg: "https://api-openbanking.wso2.com/AuthorizeAPI/v1.0.0/";
    private String authCode = "";

    //https://api-openbanking.wso2.com/AuthorizeAPI/v1.0.0/?response_type=code&scope=accounts payments&state=YWlzcDozMTQ2&client_id=MGw0ych4DOR9Fz_m6xwEWLdIMjQa&redirect_uri=https://openbanking.wso2.com/authenticationendpoint/authorize_callback.do

    public OIDCHandler(String clientID, String clientSecret, String authEnd, String callbackURL) {
        this.clientID = clientID;
        this.clientSecret = clientSecret;
        this.authEnd = authEnd;
        this.callbackURL = callbackURL;
    }

    public  String createAuthUrlForUserContent(String state) {
        String url = authEnd+"?response_type=code&scope=accounts payments&state="+state+"&client_id="+clientID+"&redirect_uri="+callbackURL;
        Log.info(url);
        return url;
    }

    /*
    curl -v -X POST --basic -u MGw0ych4DOR9Fz_m6xwEWLdIMjQa:1ZFZuUU9xBFr7MxaP5V0XutuTRga -H "Content-Type: application/x-www-form-urlencoded;charset=UTF-8"
    -k -d "client_id=MGw0ych4DOR9Fz_m6xwEWLdIMjQa&grant_type=authorization_code&code=YOUR_AUTHORIZATION_CODE&scope=accounts payments&redirect_uri
    =https://openbanking.wso2.com/authenticationendpoint/authorize_callback.do" https://api-openbanking.wso2.com/TokenAPI/v1.0.0/
     */
    public String getAccessToken(){
        return "17f17e6d-503e-393d-878f-4616584292dc";
    }

    public void setAuthCode(String authCode){
        this.authCode = authCode;
    }
}
