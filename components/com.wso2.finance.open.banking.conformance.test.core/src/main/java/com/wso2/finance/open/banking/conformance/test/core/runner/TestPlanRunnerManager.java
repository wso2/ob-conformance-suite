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

import com.wso2.finance.open.banking.conformance.mgt.models.Report;
import com.wso2.finance.open.banking.conformance.mgt.testconfig.TestPlan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Entry Point to the Test Core, Manages TestRunnerInstances.
 */
public class TestPlanRunnerManager implements RunnerManagerCallback {

    private Map<String, BlockingQueue<TestPlanFeatureResult>> resultQueueMap = new HashMap();
    private Map<String, TestPlanRunnerInstance> runnerInstanceMap = new HashMap();
    private static Map<String, Map<Integer, Report>> reportStore = new ConcurrentHashMap<>();

    /**
     * @param testPlan
     * @return
     */
    public String addPlan(TestPlan testPlan) {

        String uuid = UUID.randomUUID().toString();
        testPlan.setTestId(uuid);
        this.resultQueueMap.put(uuid, new ArrayBlockingQueue(50));
        this.runnerInstanceMap.put(uuid,
                new TestPlanRunnerInstance(testPlan, this.resultQueueMap.get(uuid), this));
        return uuid;
    }

    /**
     * @param uuid
     * @return
     */
    public List<TestPlanFeatureResult> getResults(String uuid) {

        if (this.runnerInstanceMap.containsKey(uuid)) {
            List<TestPlanFeatureResult> results = new ArrayList();
            this.resultQueueMap.get(uuid).drainTo(results);
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
    public Report start(String uuid) {

        TestPlanRunnerInstance runnerInstance = runnerInstanceMap.get(uuid);
        this.runnerInstanceMap.put(uuid,
                new TestPlanRunnerInstance(runnerInstance.getTestPlan(), this.resultQueueMap.get(uuid), this));
        Report report = this.runnerInstanceMap.get(uuid).buildReport();
        report = this.onAddResult(report);
        report.state = Report.RunnerState.RUNNING;
        this.runnerInstanceMap.get(uuid).setReportId(report.reportId);
        this.runnerInstanceMap.get(uuid).start();
        return report;
    }

    /**
     * @return
     */
    public Map<String, TestPlan> getAllTests() {

        Map<String, TestPlan> results = new HashMap<>();
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
    public void setContextAttribute(String key, String value) {   //todo handle multiple threads

        for (TestPlanRunnerInstance instance : this.runnerInstanceMap.values()) {
            if (instance.getStatus() == Report.RunnerState.WAITING) {
                instance.setContextAttributes(key, value);
                instance.setStatus(Report.RunnerState.RUNNING);
            }
        }
    }

    /**
     * RunnerManagerCallback.
     *
     * @param report
     * @return
     */
    @Override
    public Report onAddResult(Report report) {

        if (reportStore.get(report.testId) == null) {
            reportStore.put(report.testId, new ConcurrentHashMap<>());
        }
        report.reportId = reportStore.get(report.testId).size() + 1;
        reportStore.get(report.testId).put(report.reportId, report);
        return report;
    }

    /**
     * @param report
     */
    @Override
    public void onUpdateResult(Report report) {

        reportStore.get(report.testId).put(report.reportId, report);
    }

    /**
     * @param uuid
     * @return
     */
    public List<Report> getAllReports(String uuid) {

        return new ArrayList<>(reportStore.getOrDefault(uuid, new HashMap<>()).values());
    }

    /**
     * @param uuid
     * @param reportId
     * @return
     */
    public Report getReport(String uuid, Integer reportId) {

        return reportStore.get(uuid).get(reportId);
    }
}
