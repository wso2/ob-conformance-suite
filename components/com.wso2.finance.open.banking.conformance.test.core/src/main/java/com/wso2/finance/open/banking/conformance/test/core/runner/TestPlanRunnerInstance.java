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

package com.wso2.finance.open.banking.conformance.test.core.runner;

import com.google.gson.JsonObject;
import com.wso2.finance.open.banking.conformance.mgt.testconfig.Feature;
import com.wso2.finance.open.banking.conformance.mgt.testconfig.Specification;
import com.wso2.finance.open.banking.conformance.mgt.testconfig.TestPlan;
import com.wso2.finance.open.banking.conformance.test.core.Context;
import com.wso2.finance.open.banking.conformance.test.core.testrunners.FeatureRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class TestPlanRunnerInstance extends Thread{
    private TestPlan testPlan;
    private BlockingQueue<TestPlanFeatureResult> resultQueue;
    private volatile Map<String,List<JsonObject>> formattedResult = new HashMap();
    private volatile RUNNER_STATE status;
    public enum RUNNER_STATE {
        RUNNING, DONE, NOT_STARTED
    }


    public TestPlanRunnerInstance(TestPlan testPlan, BlockingQueue<TestPlanFeatureResult> resultQueue) {
        super();
        Context.getInstance().init(testPlan);
        this.testPlan = testPlan;
        this.resultQueue = resultQueue;
        this.status = RUNNER_STATE.NOT_STARTED;
    }

    private void queueResult(JsonObject result, Specification specification){
        TestPlanFeatureResult testPlanFeatureResult = new TestPlanFeatureResult();
        testPlanFeatureResult.featureResult = result;
        testPlanFeatureResult.specName = specification.getName();
        this.resultQueue.add(testPlanFeatureResult);
    }

    private void processSpec(Specification specification){
        List<JsonObject> featureResults = new ArrayList();
        Context.getInstance().setSpecContext(specification.getName(), specification.getVersion());
        for(Feature feature : specification.getFeatures()){
            FeatureRunner featureRunner = new FeatureRunner(feature);
            JsonObject featureResult = featureRunner.runFeature();
            featureResults.add(featureResult);
            this.queueResult(featureResult,specification);
        }
        formattedResult.put(specification.getName(),featureResults);
        Context.getInstance().clearSpecContext();
    }

    public void run(){
        this.status = RUNNER_STATE.RUNNING;
        for(Specification specification : this.testPlan.getSpecifications()){
            this.processSpec(specification);
        }
        this.status = RUNNER_STATE.DONE;
    }

    public RUNNER_STATE getStatus() {

        return status;
    }

    public Map<String, List<JsonObject>> getFormattedResult() {

        return formattedResult;
    }

    public TestPlan getTestPlan() {

        return testPlan;
    }
}
