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

package com.wso2.finance.open.banking.conformance.api.services;

import com.wso2.finance.open.banking.conformance.api.ApplicationDataHolder;
import com.wso2.finance.open.banking.conformance.api.dto.TestPlanDTO;
import com.wso2.finance.open.banking.conformance.api.dto.TestPlanRequestDTO;
import com.wso2.finance.open.banking.conformance.api.dto.TestPlanResponseDTO;
import com.wso2.finance.open.banking.conformance.mgt.models.Report;
import com.wso2.finance.open.banking.conformance.test.core.runner.TestPlanRunnerManager;

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

        String testId = this.runnerManager.addPlan(plan.getTestPlan());
        Report report = null;
        if (plan.isRunNow()) {
            report = this.runnerManager.start(testId);
        }

        return new TestPlanResponseDTO(testId, report);
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
    public Map<String, TestPlanDTO> getAllTestPlans() {

        Map<String, TestPlanDTO> results = new HashMap<>();
        this.runnerManager.getAllTests().forEach(
                (uuid, testPlan) -> results.put(
                        uuid,
                        new TestPlanDTO(uuid, testPlan, this.runnerManager.getAllReports(uuid))
                )
        );
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
    public Report startTestPlan(@PathParam("uuid") String uuid) {

        return this.runnerManager.start(uuid);
    }

}
