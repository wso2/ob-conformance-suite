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

package org.wso2.finance.open.banking.conformance.test.core.runner;

import org.wso2.finance.open.banking.conformance.mgt.models.Report;
import org.wso2.finance.open.banking.conformance.mgt.testconfig.TestPlan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Entry Point to the Test Core, Manages TestRunnerInstances.
 */
public class TestPlanRunnerManager {

    private Map<Integer, TestPlanRunnerInstance> runnerInstanceMap = new HashMap();

    /**
     * @param testPlan
     * @return
     */
    public void addPlan(TestPlan testPlan, int testID) {

        testPlan.setTestId(testID);
        this.runnerInstanceMap.put(testID, new TestPlanRunnerInstance(testPlan));
    }

    /**
     * @param uuid
     * @return
     */
    public List<TestPlanFeatureResult> getResults(int uuid) {

        if (this.runnerInstanceMap.containsKey(uuid)) {
            List<TestPlanFeatureResult> results = new ArrayList();
            this.runnerInstanceMap.get(uuid).getResultsQueue().drainTo(results);
            return results;
        } else {
            return null;
        }
    }

    /**
     * @param uuid
     * @return
     */
    public Report.RunnerState getStatus(String uuid) {

        return this.runnerInstanceMap.get(uuid).getStatus();
    }

    /**
     * @param uuid
     * @return
     */
    public Report start(int uuid, int reportID) {

        //TODO: Remove this method, should run test on addition.
        TestPlanRunnerInstance runnerInstance = runnerInstanceMap.get(uuid);
        this.runnerInstanceMap.put(uuid, new TestPlanRunnerInstance(runnerInstance.getTestPlan()));
        this.runnerInstanceMap.get(uuid).start();
        return this.runnerInstanceMap.get(uuid).buildReport(reportID);
    }

    /**
     * @return
     */
    public Map<Integer, TestPlan> getAllTests() {

        Map<Integer, TestPlan> results = new HashMap<>();
        this.runnerInstanceMap.forEach((uuid, runnerInstance) -> results.put(uuid, runnerInstance.getTestPlan()));
        return results;
    }

    /**
     * @param uuid
     * @return
     */
    public TestPlan getTestPlan(String uuid) {

        return this.runnerInstanceMap.get(uuid).getTestPlan();
    }

    /**
     * @param key
     * @param value
     */
    public void setContextAttribute(String key, String value) {

        for (TestPlanRunnerInstance instance : this.runnerInstanceMap.values()) {
            if (instance.getStatus() == Report.RunnerState.WAITING) {
                instance.setContextAttributes(key, value);
                instance.setStatus(Report.RunnerState.RUNNING);
            }
        }
    }

    /**
     * @param uuid
     * @return
     */
    public List<Report> getAllReports(String uuid) {

        //TODO: replaced with DTO on API end
        // Only returns the single iteration object held.
        ArrayList<Report> tmp = new ArrayList<>();
        //tmp.add(this.runnerInstanceMap.get(uuid).buildReport());
        return tmp;
    }

    /**
     * @param uuid
     * @param reportId
     * @return
     */
    public Report getReport(int uuid, Integer reportId) {

        return this.runnerInstanceMap.get(uuid).buildReport(reportId);
    }
}
