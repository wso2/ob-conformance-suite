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

import com.wso2.finance.open.banking.conformance.mgt.models.Feature;
import com.wso2.finance.open.banking.conformance.test.core.utilities.Log;
import cucumber.api.cli.Main;

public class FeatureRunner {
    private Feature feature;

    public FeatureRunner(Feature feature){
        this.feature = feature;
    }

    public void runFeature(){
        Log.info("Start Running Feature: " + feature.getTitle());
        //set context with feature related stuff

        String[] argv = new String[]
                            { "-g",
                              "com.wso2.finance.open.banking.conformance.test.core.steps.v1_0_0",
                              feature.getUri().getParent()
                             };


        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Main.run(argv, contextClassLoader);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        //clear context
        Log.info("End Running Feature: " + feature.getTitle());
    }
}
