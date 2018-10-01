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
package com.wso2.finance.open.banking.conformance.test.core.steps.v1_0_0;

import com.wso2.finance.open.banking.conformance.mgt.models.Attribute;
import com.wso2.finance.open.banking.conformance.mgt.models.AttributeGroup;
import com.wso2.finance.open.banking.conformance.mgt.models.Report;
import com.wso2.finance.open.banking.conformance.test.core.context.Context;
import com.wso2.finance.open.banking.conformance.test.core.oidc.OIDCHandler;
import com.wso2.finance.open.banking.conformance.test.core.utilities.Utils;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Steps used in OIDC flow.
 */
public class OIDCSteps {

    private OIDCHandler oidcHandler;

    private String authEndPoint;
    private String callbackUrl;
    private String tokenEndPoint;
    private String clientID;
    private String clientSecret;

    private Logger log = Logger.getLogger(OIDCSteps.class);

    @When("user provides his consent by clicking on redirect url")
    public void directUserToGetTheConcent() {

        clientID = Context.getInstance().getCurrentSpecAttribute("client", "consumer key");
        clientSecret = Context.getInstance().getCurrentSpecAttribute("client", "consumer secret");
        authEndPoint = "https://api-openbanking.wso2.com/AuthorizeAPI/v1.0.0/";
        callbackUrl = Context.getInstance().getCurrentSpecAttribute("oauth", "callback_url");
        tokenEndPoint = "https://api-openbanking.wso2.com/TokenAPI/v1.0.0/";
        oidcHandler = new OIDCHandler(clientID, clientSecret, authEndPoint, callbackUrl, tokenEndPoint);
        oidcHandler.setScope("accounts payments");
        oidcHandler.setGrantType("authorization_code");
        String url = oidcHandler.createAuthUrlForUserContent("YWlzcDozMTQ2");
        setBrowserInteractionURLtoContext(url);
    }

    @Then("TPP receives the authorization code from the authorization endpoint")
    public void receiveAuthorizationcode() {

        String authCode = Context.getInstance().getAttributesFromTempMap("auth_code");
        int i = 0;
        while ((authCode == null) && (i < 60)) {
            try {
                Thread.sleep(1000);
                authCode = Context.getInstance().getAttributesFromTempMap("auth_code");
            } catch (InterruptedException e) {
                log.warn("Receive authorization code timeout", e);
            }
            i++;
        }

        Context.getInstance().getRunnerInstance().setStatus(Report.RunnerState.RUNNING);
        oidcHandler.setAuthCode(authCode);
        log.info("Received Auth Code: " + authCode);
        assertTrue(Utils.formatError("Authorization Code not received from authorization endpoint"),
                authCode != null);

    }

    @Then("TPP requests and receives an access token from token endpoint")
    public void getAccessToken() {

        String accessToken = oidcHandler.getAccessTokenByAuthorizationCode();

        Context.getInstance().setAccessToken(accessToken);
        log.info("Received Access Token: " + accessToken);
    }

    private void setBrowserInteractionURLtoContext(String url) {

        Attribute atr = new Attribute("consentUrl",
                "Get Consent", Attribute.AttributeType.LinkButton, url, url, "Get Consent");
        List<Attribute> atrList = new ArrayList();
        atrList.add(atr);

        AttributeGroup atrGrp = new AttributeGroup("browser", "Get Consent",
                "Get Consent through browser interaction", atrList);

        Context.getInstance().getRunnerInstance().queueBrowserInteractionAttributes(atrGrp);
        Context.getInstance().getRunnerInstance().setStatus(Report.RunnerState.WAITING);
    }
}
