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

package org.wso2.finance.open.banking.conformance.mgt.dto;

import org.wso2.finance.open.banking.conformance.mgt.models.Report;
import org.wso2.finance.open.banking.conformance.mgt.testconfig.TestPlan;

import java.util.List;

/**
 * DTO for TestPlan.
 */
public class TestPlanDTO {

    int testId;
    TestPlan testPlan;
    List<Report> reports;

    public TestPlanDTO(int testId, TestPlan testPlan, List<Report> reports) {

        this.testId = testId;
        this.testPlan = testPlan;
        this.reports = reports;
    }

    /**
     *
     * @return testId
     */
    public int getTestId() {

        return testId;
    }

    /**
     *
     * @return testPlan
     */
    public TestPlan getTestPlan() {

        return testPlan;
    }

    /**
     *
     * @return results of each iteration
     */
    public List<Report> getReports() {

        return reports;
    }
}
