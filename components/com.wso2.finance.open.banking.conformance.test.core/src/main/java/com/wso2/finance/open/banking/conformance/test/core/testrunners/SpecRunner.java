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

package com.wso2.finance.open.banking.conformance.test.core.testrunners;

import com.google.gson.JsonObject;
import com.wso2.finance.open.banking.conformance.mgt.testconfig.Feature;
import com.wso2.finance.open.banking.conformance.mgt.testconfig.Specification;
import com.wso2.finance.open.banking.conformance.test.core.Context;
import com.wso2.finance.open.banking.conformance.test.core.utilities.Log;

import java.util.ArrayList;
import java.util.List;

public class SpecRunner {
    private Specification specification;

    public SpecRunner(Specification specification) {
        this.specification = specification;
    }

    public List<JsonObject> runSpecification(){
        Log.info("Start Running TestSpec " + specification.getName() + " version:" + specification.getVersion());
        List<JsonObject> results = new ArrayList();
        //set spec context
        Context.getInstance().setSpecContext(specification.getName(), specification.getVersion());
        for(Feature feature : specification.getFeatures()){
            FeatureRunner featureRunner = new FeatureRunner(feature);
            results.add(featureRunner.runFeature());
        }
        //clear spec context
        Context.getInstance().clearSpecContext();
        Log.info("End Running TestSpec " + specification.getName() + " version:" + specification.getVersion());
        return results;

    }
}
