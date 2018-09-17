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

package com.wso2.finance.open.banking.conformance.api;

import com.wso2.finance.open.banking.conformance.api.dto.TestResultDTO;
import com.wso2.finance.open.banking.conformance.test.core.runner.TestPlanFeatureResult;
import com.wso2.finance.open.banking.conformance.test.core.runner.TestPlanRunnerManager;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * This is the Microservice resource class.
 * See <a href="https://github.com/wso2/msf4j#getting-started">https://github.com/wso2/msf4j#getting-started</a>
 * for the usage of annotations.
 *
 * @since 1.0.0-SNAPSHOT
 */
@Path("/results")
public class ResultsAPI {

    TestPlanRunnerManager runnerManager = ApplicationDataHolder.getInstance().getRunnerManager();

    @GET
    @Path("/poll/{testId}")
    @Produces("application/json")
    public List<TestPlanFeatureResult> getCurrentResult(@PathParam("testId") String testId) {

        return this.runnerManager.getResults(testId);
    }

    @GET
    @Path("/{testId}/{reportId}")
    @Produces("application/json")
    public TestResultDTO getCompleteResult(@PathParam("testId") String testId,
                                           @PathParam("reportId") Integer reportId) {

        return new TestResultDTO(
                this.runnerManager.getTestPlan(testId),
                this.runnerManager.getReport(testId, reportId)
        );
    }

}
