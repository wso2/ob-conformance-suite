
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

import com.wso2.finance.open.banking.conformance.mgt.models.*;
import java.util.Map;
import java.util.HashMap;

public class Context {

    private static final Context contextInstance = new Context();
    private TestPlan testPlan;

    private String currentSpec = "";
    private String currentSpceVersion = "";
    private String currentFeature = "";

    private Map<String,String> swaggerJsonFileMap = new HashMap<String,String>(); //specname+version -> swaggerJsonFile

    private Context(){}

    public static Context getInstance(){
        return contextInstance;
    }


    public void init(TestPlan testPlan){
        this.testPlan = testPlan;
        swaggerJsonFileMap.put("specExamplev1.0","schema/v1_0_0/open_data.json");

    }

    public String getSwaggerJsonFile() {
        return swaggerJsonFileMap.get(currentSpec+currentSpceVersion);
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

    public void setFeatureContext(String featureTitle)
    {
        currentFeature = featureTitle;
    }

    public void clearFeatureContext()
    {
        currentFeature = "";
    }

    /**
     *
     * @param specName
     * @param specVersion
     * @param featureTitle
     * @param attribGroupName
     * @param attribName
     * @return
     */
    public Attribute getFeatureAttribute(String specName, String specVersion, String featureTitle,
                                         String attribGroupName, String attribName){

        Attribute attrib;
        attrib = testPlan.getSpecification(specName, specVersion).getFeature(featureTitle).
                getAttributeGroup(attribGroupName).getAttribute(attribName);

        return attrib;
    }

    /**
     *
     * @param specName
     * @param specVersion
     * @param attribGroupName
     * @param attribName
     * @return
     */
    public Attribute getSpecAttribute(String specName, String specVersion,
                                      String attribGroupName, String attribName){

        Attribute attrib;
        attrib = testPlan.getSpecification(specName, specVersion).getAttributeGroup(attribGroupName).
                getAttribute(attribName);

        return attrib;
    }

    public Attribute getCurrentSpecAttribute(String attribGroupName, String attribName)
    {
        return getSpecAttribute(currentSpec,currentSpceVersion,attribGroupName,attribName);
    }

    public Attribute getCurrentFeatureAttribute(String attribGroupName, String attribName)
    {
        return getFeatureAttribute(currentSpec,currentSpceVersion,currentFeature,attribGroupName,attribName);
    }

}
