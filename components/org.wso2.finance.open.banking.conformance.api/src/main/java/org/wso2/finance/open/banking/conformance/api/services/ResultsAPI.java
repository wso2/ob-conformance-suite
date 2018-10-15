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
import org.wso2.finance.open.banking.conformance.mgt.dto.TestResultDTO;
import org.wso2.finance.open.banking.conformance.mgt.models.Report;
import org.wso2.finance.open.banking.conformance.test.core.runner.TestPlanRunnerManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Microservice for managing Results.
 */
@Path("/results")
public class ResultsAPI {

    TestPlanRunnerManager runnerManager = ApplicationDataHolder.getInstance().getRunnerManager();

    /**
     * Get Complete Result of a Test.
     *
     * @param testId   Id of the test.
     * @param reportId Report Iteration Id.
     * @return TestResultDTO
     */
    @GET
    @Path("/{testId}/{reportId}")
    @Produces("application/json")
    public TestResultDTO getCompleteResult(@PathParam("testId") int testId,
                                           @PathParam("reportId") int reportId) {

        TestPlanDAO testPlanDAO = new TestPlanDAOImpl();
        ReportDAO reportDAO = new ReportDAOImpl();
        Report report = reportDAO.getReport(reportId);

        if(report == null){
            report =  this.runnerManager.getReport(testId, reportId);
        }
        return new TestResultDTO(testPlanDAO.getTestPlan(testId), report);
    }

}
