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
import org.wso2.finance.open.banking.conformance.test.core.runner.TestPlanFeatureResult;
import org.wso2.finance.open.banking.conformance.test.core.runner.TestPlanRunnerManager;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Microservice for RunnerManager related actions and callbacks.
 */
@Path("/runner")
public class RunnerManagerAPI {

    TestPlanRunnerManager runnerManager = ApplicationDataHolder.getInstance().getRunnerManager();

    /**
     * Poll Result Queue to get progressive results.
     *
     * @param testId id of the test.
     * @return List of TestPlanFeatureResult.
     */
    @GET
    @Path("/{testId}/poll")
    @Produces("application/json")
    public List<TestPlanFeatureResult> getCurrentResult(@PathParam("testId") String testId) {

        return this.runnerManager.getResults(testId);
    }

    /**
     * OAuth callback to get Security code for OIDC flow.
     *
     * @param code auth code.
     * @return
     */
    @GET
    @Path("/callback")
    public Response getAuthCode(@QueryParam("code") String code) {

        this.runnerManager.setContextAttribute("auth_code", code);
        return Response.ok().type(MediaType.TEXT_HTML).entity("Done").build();
    }

}
