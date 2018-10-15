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

package org.wso2.finance.open.banking.conformance.api.services;

import org.wso2.finance.open.banking.conformance.api.ApplicationDataHolder;
import org.wso2.finance.open.banking.conformance.mgt.dao.ReportDAO;
import org.wso2.finance.open.banking.conformance.mgt.dao.TestPlanDAO;
import org.wso2.finance.open.banking.conformance.mgt.dao.impl.ReportDAOImpl;
import org.wso2.finance.open.banking.conformance.mgt.dao.impl.TestPlanDAOImpl;
import org.wso2.finance.open.banking.conformance.mgt.dto.TestPlanDTO;
import org.wso2.finance.open.banking.conformance.api.dto.TestPlanRequestDTO;
import org.wso2.finance.open.banking.conformance.api.dto.TestPlanResponseDTO;
import org.wso2.finance.open.banking.conformance.mgt.models.Report;
import org.wso2.finance.open.banking.conformance.test.core.runner.TestPlanRunnerManager;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Microservice for managing TestPlans.
 */
@Path("/testplan")
public class TestPlanAPI {

    private TestPlanRunnerManager runnerManager = ApplicationDataHolder.getInstance().getRunnerManager();

    /**
     * Add a new TestPlan.
     *
     * @param plan TestPlanRequestDTO
     * @return TestPlanResponseDTO
     */
    @POST
    @Path("/")
    @Consumes("application/json")
    @Produces("application/json")
    public TestPlanResponseDTO addTestPlan(TestPlanRequestDTO plan) {
        // SAVE TO DB
        //  String testId = this.runnerManager.addPlan(plan.getTestPlan());
        TestPlanDAO testPlanDAO = new TestPlanDAOImpl();
        ReportDAO reportDAO = new ReportDAOImpl();
        int testID = testPlanDAO.storeTestPlan("adminx", plan.getTestPlan()); //TODO: Remove hardcoded userID
        this.runnerManager.addPlan(plan.getTestPlan(), testID);
        Report report = null;
        if (plan.isRunNow()) {
            // PASS REPORT
            int reportID = reportDAO.getNewReportID("adminx", testID); //TODO: Remove hardcoded userID
            report = this.runnerManager.start(testID, reportID);
        }

        return new TestPlanResponseDTO(String.valueOf(testID), report);
    }

    /**
     * Options Endpoint for addTestPlan.
     *
     * @return
     */
    @OPTIONS
    @Path("/")
    public Response getOptionsRunTestPlan() {

        return Response.status(Response.Status.OK).header("Access-Control-Allow-Methods", "POST,OPTIONS").build();
    }

    /**
     * List all TestPlans.
     *
     * @return List of TestPlanDTOs.
     */
    @GET
    @Path("/")
    @Produces("application/json")
    public Map<Integer, TestPlanDTO> getAllTestPlans() {
        TestPlanDAO testPlanDAO = new TestPlanDAOImpl();
        Map<Integer, TestPlanDTO> results;
        results = testPlanDAO.getTestPlans("adminx"); //TODO: remove hardcoded userID
        return results;
    }


    /**
     * Run a single TestPlan.
     *
     * @param uuid id of the TestPlan.
     * @return Report.
     */
    @GET
    @Path("/{uuid}/run")
    @Produces("application/json")
    public Report startTestPlan(@PathParam("uuid") int uuid) {

        TestPlanDAO testPlanDAO = new TestPlanDAOImpl();
        ReportDAO reportDAO = new ReportDAOImpl();
        int reportID = reportDAO.getNewReportID("adminx", uuid); // TODO: remove hardcoded userID.
        this.runnerManager.addPlan(testPlanDAO.getTestPlan(uuid), uuid);
        return this.runnerManager.start(uuid, reportID);
    }
}
