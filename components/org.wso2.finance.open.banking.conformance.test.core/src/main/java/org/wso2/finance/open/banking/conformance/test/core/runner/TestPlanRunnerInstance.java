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

import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.wso2.finance.open.banking.conformance.mgt.dao.ReportDAO;
import org.wso2.finance.open.banking.conformance.mgt.dao.impl.ReportDAOImpl;
import org.wso2.finance.open.banking.conformance.mgt.exceptions.ConformanceMgtException;
import org.wso2.finance.open.banking.conformance.mgt.models.AttributeGroup;
import org.wso2.finance.open.banking.conformance.mgt.models.Report;
import org.wso2.finance.open.banking.conformance.mgt.models.Result;
import org.wso2.finance.open.banking.conformance.mgt.testconfig.Feature;
import org.wso2.finance.open.banking.conformance.mgt.testconfig.Specification;
import org.wso2.finance.open.banking.conformance.mgt.testconfig.TestPlan;
import org.wso2.finance.open.banking.conformance.test.core.context.Context;
import org.wso2.finance.open.banking.conformance.test.core.testrunners.FeatureRunner;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Executes and holds results of a TestPlan.
 */
public class TestPlanRunnerInstance extends Thread {

    private static Logger log = Logger.getLogger(TestPlanRunnerInstance.class);

    private TestPlan testPlan;
    private volatile Integer reportId;
    private BlockingQueue<TestPlanFeatureResult> resultsQueue;
    private volatile Map<String, List<Result>> formattedResult = new HashMap();
    private volatile Report.RunnerState status;
    private Context context;

    /**
     * @param testPlan Configured TestPlan received from front end
     */
    public TestPlanRunnerInstance(TestPlan testPlan) {

        super();
        this.testPlan = testPlan;
        this.status = Report.RunnerState.RUNNING;
        this.resultsQueue = new ArrayBlockingQueue(50);
        //Initialize Specs in Data Structure
        for (Specification spec : this.testPlan.getSpecifications()) {
            this.formattedResult.put(spec.getName(), new ArrayList<>());
        }
    }

    /**
     * @param result test results json
     * @param specification corresponding specification
     */
    private void queueResult(Result result, Specification specification) {

        TestPlanFeatureResult testPlanFeatureResult =
                new TestPlanFeatureResult(result, specification.getName(), this.status);
        this.resultsQueue.add(testPlanFeatureResult);

        log.debug("Queue test results of the Spec : " + specification.getName());

    }

    /**
     * add last result to the queue.
     */
    private void queueStopMessege() {

        TestPlanFeatureResult testPlanFeatureResult = new TestPlanFeatureResult(this.status);
        this.resultsQueue.add(testPlanFeatureResult);
        log.debug("Add last test result of TestPlan: " + testPlan.getName() + " to the queue");
    }

    /**
     * @param attributeGroup
     */
    public void queueBrowserInteractionAttributes(Specification specification, AttributeGroup attributeGroup) {

        TestPlanFeatureResult featureResult
                = new TestPlanFeatureResult(attributeGroup, specification.getName(), this.status);
        this.resultsQueue.add(featureResult);
    }

    /**
     * @param specification
     */
    private void processSpec(Specification specification) {

        log.debug("Run Spec : " + specification.getName());

        List<Result> featureResults = new ArrayList();
        formattedResult.put(specification.getName(), featureResults);

        Context.getInstance().setSpecContext(specification.getName(), specification.getVersion());
        Context.getInstance().setRunnerInstance(this);

        for (Feature feature : specification.getFeatures()) {
            FeatureRunner featureRunner = new FeatureRunner(feature);
            Result featureResult = featureRunner.runFeature();
            featureResults.add(featureResult);
            this.queueResult(featureResult, specification);
        }

        Context.getInstance().clearSpecContext();
    }

    /**
     * @return Report with test results
     */
    public Report buildReport(int reportId) {

        this.reportId = reportId;
        return new Report(testPlan.getTestId(), reportId, formattedResult, status, new Date()); // TODO: Get reportID from DB
    }

    /**
     * Start running tests.
     */
    public void run() {

        ReportDAO reportDAO = new ReportDAOImpl();
        context = Context.getInstance();
        context.init(testPlan);
        this.status = Report.RunnerState.RUNNING;
        for (Specification specification : this.testPlan.getSpecifications()) {
            this.processSpec(specification);
        }
        this.status = Report.RunnerState.DONE;
        this.testPlan.setLastRun(new Date()); //TODO: Update date in DB
        queueStopMessege();

        // Add report to DB
        try {
            reportDAO.updateReport(this.reportId, this.buildReport(this.reportId));
        } catch (ConformanceMgtException e){
            log.error(e.getMessage(),e);
        }
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

    public BlockingQueue<TestPlanFeatureResult> getResultsQueue() {

        return resultsQueue;
    }
}
