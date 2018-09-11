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

import com.wso2.finance.open.banking.conformance.mgt.models.Attribute;
import com.wso2.finance.open.banking.conformance.mgt.models.AttributeGroup;
import com.wso2.finance.open.banking.conformance.test.core.Context;
import com.wso2.finance.open.banking.conformance.test.core.utilities.Log;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import com.wso2.finance.open.banking.conformance.test.core.response.ResponseValidator;
import com.wso2.finance.open.banking.conformance.test.core.request.RequestGenerator;
import com.wso2.finance.open.banking.conformance.test.core.oidc.OIDCHandler;
import sun.jvm.hotspot.utilities.Assert;

import java.util.List;
import java.util.ArrayList;

public class OIDCSteps {
    private OIDCHandler oidcHandler;

    private String AUTH_END_POINT;
    private String CALLBACK_URL;

    @Given("user is directed to the auth endpoint to get the consent")
    public void directUsertoGetTheConcent(){
        AUTH_END_POINT = "v1.0.0";
        CALLBACK_URL = Context.getInstance().getCurrentSpecAttribute("oauth","callback_url");
        oidcHandler = new OIDCHandler(AUTH_END_POINT,CALLBACK_URL);
        String clientID = Context.getInstance().getCurrentSpecAttribute("client","client_id");
        String url = oidcHandler.createAuthUrlForUserContent("YWlzcDozMTQ2",clientID);
        setBrowserInteractionURLtoContext(url);
    }

    @When("TPP receives the authorization code")
    public void receiveAuthorizationcode(){
        String authCode = Context.getInstance().getAttributesFromTempMap("auth_code");
        int i = 0;
        while ((authCode == "") && (i < 20)){
            try {
                Thread.sleep(1000);
                authCode = Context.getInstance().getAttributesFromTempMap("auth_code");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        oidcHandler.setAuthCode(authCode);
    }

    @Then("TPP requests an access token from token endpoint")
    public void getAccessToken(){
        Context.getInstance().setAccessToken(oidcHandler.getAccessToken());
    }

    private void setBrowserInteractionURLtoContext(String url) {
        Attribute atr = new Attribute("browserUrl", "",Attribute.ATTRIBUTE_TYPE.String, url, url,"");
        List<Attribute> atrList = new ArrayList();
        atrList.add(atr);

        AttributeGroup atrGrp = new AttributeGroup("browser","","",atrList);
        List<AttributeGroup> atrGrpList = new ArrayList();
        atrGrpList.add(atrGrp);
        Context.getInstance().setUserInteractionAttributes(atrGrpList);
    }
}
