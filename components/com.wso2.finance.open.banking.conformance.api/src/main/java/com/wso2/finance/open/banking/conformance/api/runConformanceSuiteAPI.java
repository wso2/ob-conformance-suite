/*
 * Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import com.google.gson.Gson;
import com.wso2.finance.open.banking.conformance.mgt.testconfig.TestPlan;
import com.wso2.finance.open.banking.conformance.test.core.CoreTestRunner;


/**
 * This is the Microservice resource class.
 * See <a href="https://github.com/wso2/msf4j#getting-started">https://github.com/wso2/msf4j#getting-started</a>
 * for the usage of annotations.
 *
 * @since 1.0.0-SNAPSHOT
 */
@Path("/run")
public class runConformanceSuiteAPI {

    private Gson gson = new Gson();

    @POST
    @Path("/testplan")
    @Consumes("application/json")
    @Produces("application/json")
    public String runTestPlan(TestPlan plan){
        return gson.toJson(CoreTestRunner.runTestPlan(plan));
    }
}
