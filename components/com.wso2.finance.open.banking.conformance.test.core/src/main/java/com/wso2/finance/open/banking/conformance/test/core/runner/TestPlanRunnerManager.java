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
import com.wso2.finance.open.banking.conformance.mgt.testconfig.TestPlan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TestPlanRunnerManager {

    private Map<String,BlockingQueue<TestPlanFeatureResult>> resultQueueMap = new HashMap();
    private Map<String,TestPlanRunnerInstance> runnerInstanceMap = new HashMap();

    public String addPlan(TestPlan testPlan){
        String uuid = UUID.randomUUID().toString();
        this.resultQueueMap.put(uuid, new ArrayBlockingQueue(50));
        this.runnerInstanceMap.put(uuid, new TestPlanRunnerInstance(testPlan,this.resultQueueMap.get(uuid)));
        this.start(uuid);
        return uuid;
    }

    public List<TestPlanFeatureResult> getResults(String uuid){
        if (this.runnerInstanceMap.containsKey(uuid)){
            List<TestPlanFeatureResult> results = new ArrayList();
            this.resultQueueMap.get(uuid).drainTo(results);
            return results;
        }else{
            return null;
        }
    }

    public TestPlanRunnerInstance.RUNNER_STATE getStatus(String uuid){
        return this.runnerInstanceMap.get(uuid).getStatus();
    }

    public Map<String, List<JsonObject>> getResultSet(String uuid){
        return this.runnerInstanceMap.get(uuid).getFormattedResult();
    }

    public void start(String uuid){
        //Only one test can run now
        //TODO: block more than one running
        this.runnerInstanceMap.get(uuid).start();
    }

    public Map<String, TestPlan> getAllTests() {
        Map<String, TestPlan> results = new HashMap<>();
        this.runnerInstanceMap.forEach((uuid, runnerInstance) -> results.put(uuid,runnerInstance.getTestPlan()));
        return results;
    }

    public void setCallback(String token){

    }
}
