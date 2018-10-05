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
import com.wso2.finance.open.banking.conformance.mgt.models.AttributeGroup;
import com.wso2.finance.open.banking.conformance.mgt.models.Report;
import com.wso2.finance.open.banking.conformance.mgt.testconfig.Feature;
import com.wso2.finance.open.banking.conformance.mgt.testconfig.Specification;
import com.wso2.finance.open.banking.conformance.mgt.testconfig.TestPlan;
import com.wso2.finance.open.banking.conformance.test.core.context.Context;
import com.wso2.finance.open.banking.conformance.test.core.testrunners.FeatureRunner;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * Executes and holds results of a TestPlan.
 */
public class TestPlanRunnerInstance extends Thread {

    private static Logger log = Logger.getLogger(TestPlanRunnerInstance.class);

    private TestPlan testPlan;
    private volatile Integer reportId;
    private BlockingQueue<TestPlanFeatureResult> resultsQueue;
    private volatile Map<String, List<JsonObject>> formattedResult = new HashMap();
    private volatile Report.RunnerState status;
    private RunnerManagerCallback runnerManagerCallback;
    private Context context;

    /**
     * @param testPlan Configured TestPlan received from front end
     * @param resultQueue queue used to publish the results realtime
     * @param managerCallbacks Interface used to publish updates to the test runner manager
     */
    public TestPlanRunnerInstance(TestPlan testPlan, BlockingQueue<TestPlanFeatureResult> resultQueue,
                                  RunnerManagerCallback managerCallbacks) {

        super();
        this.testPlan = testPlan;
        this.resultsQueue = resultQueue;
        this.status = Report.RunnerState.NOT_STARTED;
        this.runnerManagerCallback = managerCallbacks;
        //Initialize Specs in Data Structure
        for (Specification spec : this.testPlan.getSpecifications()) {
            this.formattedResult.put(spec.getName(), new ArrayList<>());
        }
    }

    /**
     * @param result test results json
     * @param specification corresponding specification
     */
    private void queueResult(JsonObject result, Specification specification) {

        TestPlanFeatureResult testPlanFeatureResult = new TestPlanFeatureResult();
        testPlanFeatureResult.setFeatureResult(result);
        testPlanFeatureResult.setSpecName(specification.getName());
        testPlanFeatureResult.setRunnerState(this.status);
        this.resultsQueue.add(testPlanFeatureResult);

        log.debug("Queue test results of the Spec : " + specification.getName());

    }

    /**
     * add last result to the queue.
     */
    private void queueStopMessege() {

        TestPlanFeatureResult testPlanFeatureResult = new TestPlanFeatureResult();
        testPlanFeatureResult.setRunnerState(this.status);
        this.resultsQueue.add(testPlanFeatureResult);
        log.debug("Add last test result of TestPlan: " + testPlan.getName() + " to the queue");
    }

    /**
     * @param attributeGroup
     */
    public void queueBrowserInteractionAttributes(AttributeGroup attributeGroup) {

        TestPlanFeatureResult featureResult = new TestPlanFeatureResult();
        featureResult.setAttributeGroup(attributeGroup);
        featureResult.setRunnerState(this.status);
        this.resultsQueue.add(featureResult);
    }

    /**
     * @param specification
     */
    private void processSpec(Specification specification) {

        log.debug("Run Spec : " + specification.getName());

        List<JsonObject> featureResults = new ArrayList();
        formattedResult.put(specification.getName(), featureResults);

        Context.getInstance().setSpecContext(specification.getName(), specification.getVersion());
        Context.getInstance().setRunnerInstance(this);

        for (Feature feature : specification.getFeatures()) {
            FeatureRunner featureRunner = new FeatureRunner(feature);
            JsonObject featureResult = featureRunner.runFeature();
            featureResults.add(featureResult);
            this.queueResult(featureResult, specification);
            this.runnerManagerCallback.onUpdateResult(this.buildReport());
        }

        Context.getInstance().clearSpecContext();
    }

    /**
     * @return Report with test results
     */
    public Report buildReport() {

        return new Report(testPlan.getTestId(), reportId, formattedResult, status, new Date());
    }

    /**
     * Start running tests.
     */
    public void run() {

        context = Context.getInstance();
        context.init(testPlan);
        this.status = Report.RunnerState.RUNNING;
        for (Specification specification : this.testPlan.getSpecifications()) {
            this.processSpec(specification);
        }
        this.status = Report.RunnerState.DONE;
        this.testPlan.setLastRun(new Date());
        this.runnerManagerCallback.onUpdateResult(this.buildReport());
        queueStopMessege();
        this.interrupt();
    }

    /**
     * @return
     */
    public Report.RunnerState getStatus() {

        return status;
    }

    /**
     * @return
     */
    public TestPlan getTestPlan() {

        return testPlan;
    }

    /**
     * @param status
     */
    public void setStatus(Report.RunnerState status) {

        this.status = status;
    }

    /**
     * @param key
     * @param value
     */
    public void setContextAttributes(String key, String value) {

        context.setAttributesToTempMap(key, value);
    }

    /**
     * @return
     */
    public Integer getReportId() {

        return reportId;
    }

    /**
     * @param reportId
     */
    public void setReportId(Integer reportId) {

        this.reportId = reportId;
    }
}
