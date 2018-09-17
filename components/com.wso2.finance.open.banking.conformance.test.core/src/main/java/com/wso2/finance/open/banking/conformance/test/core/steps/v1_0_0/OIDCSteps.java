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
import com.wso2.finance.open.banking.conformance.test.core.runner.TestPlanRunnerInstance;
import com.wso2.finance.open.banking.conformance.test.core.utilities.Log;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import com.wso2.finance.open.banking.conformance.test.core.oidc.OIDCHandler;

import java.util.List;
import java.util.ArrayList;

public class OIDCSteps {
    private OIDCHandler oidcHandler;

    private String AUTH_END_POINT;
    private String CALLBACK_URL;
    private String TOKEN_END_POINT;
    private String clientID;
    private String clientSecret;

    @When("user provides his consent by clicking on redirect url")
    public void directUserToGetTheConcent(){
        clientID = Context.getInstance().getCurrentSpecAttribute("client","consumer key");
        clientSecret = Context.getInstance().getCurrentSpecAttribute("client","consumer secret");
        AUTH_END_POINT = "https://api-openbanking.wso2.com/AuthorizeAPI/v1.0.0/";
        CALLBACK_URL = Context.getInstance().getCurrentSpecAttribute("oauth","callback_url");
        TOKEN_END_POINT = "https://api-openbanking.wso2.com/TokenAPI/v1.0.0/";
        oidcHandler = new OIDCHandler(clientID,clientSecret,AUTH_END_POINT,CALLBACK_URL,TOKEN_END_POINT);
        String url = oidcHandler.createAuthUrlForUserContent("YWlzcDozMTQ2");
        setBrowserInteractionURLtoContext(url);
    }

    @When("TPP receives the authorization code")
    public void receiveAuthorizationcode(){
        String authCode = Context.getInstance().getAttributesFromTempMap("auth_code");
        int i = 0;
        while ((authCode == null) && (i < 60)){
            try {
                Thread.sleep(1000);
                authCode = Context.getInstance().getAttributesFromTempMap("auth_code");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
        Context.getInstance().getRunnerInstance().setStatus(TestPlanRunnerInstance.RUNNER_STATE.RUNNING);
        oidcHandler.setAuthCode(authCode);
        Log.info("Received Auth Code: "+authCode);
    }

    @Then("TPP requests and receives an access token from token endpoint")
    public void getAccessToken(){

        String accessToken = oidcHandler.getAccessTokenByAuthorizationCode();

        Context.getInstance().setAccessToken(accessToken);
        Log.info("Received Access Token: "+ accessToken);
    }

    private void setBrowserInteractionURLtoContext(String url) {
        Attribute atr = new Attribute("consentUrl", "Get Consent",Attribute.ATTRIBUTE_TYPE.LinkButton, url, url,"Get Consent");
        List<Attribute> atrList = new ArrayList();
        atrList.add(atr);

        AttributeGroup atrGrp = new AttributeGroup("browser","Get Consent","Get Consent through browser interaction",atrList);
        List<AttributeGroup> atrGrpList = new ArrayList();
        atrGrpList.add(atrGrp);
        Context.getInstance().getRunnerInstance().queueBrowserInteractionAttributes(atrGrp);
        Context.getInstance().getRunnerInstance().setStatus(TestPlanRunnerInstance.RUNNER_STATE.WAITING);
    }
}
