
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

package com.wso2.finance.open.banking.conformance.test.core;

import com.wso2.finance.open.banking.conformance.mgt.models.AttributeGroup;
import com.wso2.finance.open.banking.conformance.mgt.testconfig.*;
import com.wso2.finance.open.banking.conformance.test.core.runner.TestPlanRunnerInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import com.wso2.finance.open.banking.conformance.test.core.utilities.Constants;

public class Context {

    private static final Context contextInstance = new Context();

    private TestPlan testPlan;

    //current API Spec Context
    private String currentSpec = "";
    private String currentSpceVersion = "";

    //current Feature context
    private String currentFeatureTitle = "";
    private String currentFeatureUri = "";

    //security context
    private String accessToken = "";

    private String test_id = UUID.randomUUID().toString();

    private Map<String, String> tempAttributeMap = new HashMap<>();

    private Map<String,String> swaggerJsonFileMap = new HashMap<String,String>(); //specname+version -> swaggerJsonFile

    private TestPlanRunnerInstance runnerInstance;

    private Context(){}

    public static Context getInstance(){
        return contextInstance;
    }


    public void init(TestPlan testPlan){
        this.testPlan = testPlan;
        swaggerJsonFileMap.put(Constants.OPEN_DATA_API_SPEC+"v1.0","schema/v1_0_0/open_data.json");
        //swaggerJsonFileMap.put(Constants.EXAMPLE_2_API_SPEC+"v2.0","schema/v1_0_0/open_data.json");
        swaggerJsonFileMap.put(Constants.ACCOUNTS_INFORMATION_API_SPEC+"v2.0.0","schema/v2_0_0/accounts_information.json");
        swaggerJsonFileMap.put(Constants.AUTH_API_SPEC+"1.0.0","schema/v1_0_0/authorize.json");
        swaggerJsonFileMap.put(Constants.TOKEN_API_SPEC+"v1.0.0","schema/v1_0_0/token.json");
    }

    public String getCurrentSwaggerJsonFile() {
        return swaggerJsonFileMap.get(currentSpec+currentSpceVersion);
    }

    public String getSwaggerJsonFile(String name) {
        return swaggerJsonFileMap.get(name);
    }

    public void setSpecContext(String spec, String specVersion)
    {
        currentSpec = spec;
        currentSpceVersion = specVersion;
    }


    public void clearSpecContext()
    {
        currentSpec = "";
        currentSpceVersion = "";
    }

    public void setFeatureContext(String featureTitle, String featureUri) {
        currentFeatureTitle = featureTitle;
        currentFeatureUri = featureUri;
    }

    public void clearFeatureContext()
    {
        currentFeatureTitle = "";
        currentFeatureUri = "";
    }

    /**
     *
     * @param specName
     * @param specVersion
     * @param featureUri
     * @param attribGroupName
     * @param attribName
     * @return
     */
    public String getFeatureAttribute(String specName, String specVersion, String featureUri,
                                         String attribGroupName, String attribName){

        return testPlan.getSpecification(specName).getFeature(featureUri).getAttribute(attribGroupName,attribName);

    }

    /**
     *
     * @param specName
     * @param specVersion
     * @param attribGroupName
     * @param attribName
     * @return
     */
    public String getSpecAttribute(String specName, String specVersion,
                                      String attribGroupName, String attribName){

        String attrib;
        attrib = testPlan.getSpecification(specName).getAttribute(attribGroupName,attribName);

        return attrib;
    }

    public String getCurrentSpecAttribute(String attribGroupName, String attribName)
    {
        return getSpecAttribute(currentSpec,currentSpceVersion,attribGroupName,attribName);
    }

    public String getCurrentFeatureAttribute(String attribGroupName, String attribName)
    {
        return getFeatureAttribute(currentSpec,currentSpceVersion,currentFeatureUri,attribGroupName,attribName);
    }

    public String getTest_id() {

        return test_id;
    }

    public void setAccessToken(String token){
        this.accessToken = token;
    }

    public String getAccessToken(){
        return accessToken;
    }

    public void setAttributesToTempMap(String key, String value){
        tempAttributeMap.put(key,value);
    }

    public String getAttributesFromTempMap(String key)
    {
        return tempAttributeMap.get(key);
    }

    public void setRunnerInstance(TestPlanRunnerInstance testPlanRunnerInstance) {
        this.runnerInstance = testPlanRunnerInstance;
    }

    public TestPlanRunnerInstance getRunnerInstance() {

        return runnerInstance;
    }
}
