
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

package com.wso2.finance.open.banking.conformance.test.core.context;

import com.wso2.finance.open.banking.conformance.mgt.testconfig.TestPlan;
import com.wso2.finance.open.banking.conformance.test.core.constants.Constants;

import com.wso2.finance.open.banking.conformance.test.core.runner.TestPlanRunnerInstance;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ThreadLocal Context for managing test runner data.
 */
public class Context {

    private static ThreadLocal<Context> threadLocalContext = ThreadLocal.withInitial(Context::new);

    private TestPlan testPlan;

    //current API Spec Context
    private String currentSpec = "";

    //current Feature context
    private String currentFeatureUri = "";

    //security context
    private String accessToken = "";

    private String testId = UUID.randomUUID().toString();

    private Map<String, String> tempAttributeMap = new HashMap<>();

    private Map<String, String> swaggerJsonFileMap = new HashMap<>();

    private TestPlanRunnerInstance runnerInstance;

    private Context() {

    }

    public static Context getInstance() {

        return threadLocalContext.get();
    }

    public void init(TestPlan testPlan) {

        this.testPlan = testPlan;
        swaggerJsonFileMap.put(Constants.OPEN_DATA_API_SPEC, "schema/v1_0_0/open_data.json");
        swaggerJsonFileMap.put(Constants.ACCOUNTS_INFORMATION_API_SPEC, "schema/v2_0_0/accounts_information.json");
        swaggerJsonFileMap.put(Constants.AUTH_API_SPEC, "schema/v1_0_0/authorize.json");
        swaggerJsonFileMap.put(Constants.TOKEN_API_SPEC, "schema/v1_0_0/token.json");
    }

    public String getCurrentSwaggerJsonFile() {

        return swaggerJsonFileMap.get(currentSpec);
    }

    public String getSwaggerJsonFile(String name) {

        return swaggerJsonFileMap.get(name);
    }

    public void setSpecContext(String spec, String specVersion) {

        currentSpec = spec;
    }

    public void clearSpecContext() {

        currentSpec = "";
    }

    public void setFeatureContext(String featureUri) {

        currentFeatureUri = featureUri;
    }

    public void clearFeatureContext() {

        currentFeatureUri = "";
        accessToken = "";
    }

    /**
     * @param specName        ]     * @param featureUri
     * @param attribGroupName
     * @param attribName
     * @return
     */
    public String getFeatureAttribute(String specName, String featureUri, String attribGroupName, String attribName) {

        return testPlan.getSpecification(specName).getFeature(featureUri).getAttribute(attribGroupName, attribName);

    }

    /**
     * @param specName
     * @param attribGroupName
     * @param attribName
     * @return
     */
    public String getSpecAttribute(String specName, String attribGroupName, String attribName) {

        String attrib;
        attrib = testPlan.getSpecification(specName).getAttribute(attribGroupName, attribName);

        return attrib;
    }

    /**
     * @param attribGroupName
     * @param attribName
     * @return
     */
    public String getCurrentSpecAttribute(String attribGroupName, String attribName) {

        return getSpecAttribute(currentSpec, attribGroupName, attribName);
    }

    /**
     * @param attribGroupName
     * @param attribName
     * @return
     */
    public String getCurrentFeatureAttribute(String attribGroupName, String attribName) {

        return getFeatureAttribute(currentSpec, currentFeatureUri, attribGroupName, attribName);
    }

    /**
     * @return
     */
    public String getTestId() {

        return testId;
    }

    /**
     * @param token
     */
    public void setAccessToken(String token) {

        this.accessToken = token;
    }

    /**
     * @return
     */
    public String getAccessToken() {

        return accessToken;
    }

    /**
     * @param key
     * @param value
     */
    public void setAttributesToTempMap(String key, String value) {

        tempAttributeMap.put(key, value);
    }

    /**
     * @param key
     * @return
     */
    public String getAttributesFromTempMap(String key) {

        return tempAttributeMap.get(key);
    }

    /**
     * @param testPlanRunnerInstance
     */
    public void setRunnerInstance(TestPlanRunnerInstance testPlanRunnerInstance) {

        this.runnerInstance = testPlanRunnerInstance;
    }

    /**
     * @return
     */
    public TestPlanRunnerInstance getRunnerInstance() {

        return runnerInstance;
    }
}
