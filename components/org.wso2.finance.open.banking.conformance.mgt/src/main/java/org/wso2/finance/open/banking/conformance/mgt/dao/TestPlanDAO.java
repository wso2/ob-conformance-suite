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

package org.wso2.finance.open.banking.conformance.mgt.dao;

import org.wso2.finance.open.banking.conformance.mgt.dto.TestPlanDTO;
import org.wso2.finance.open.banking.conformance.mgt.testconfig.TestPlan;

import java.util.Map;

/**
 * Interface to create a TestPlanDAO.
 */
public interface TestPlanDAO {

    /**
     *This method will store the given test-plan in the testPlan table.
     * @param userID : User ID of the current user
     * @param testPlan : TestPlan object
     * @return the generated test-plan id (from auto increment column of the testPlan table)
     */
    public int storeTestPlan(String userID, TestPlan testPlan);

    /**
     *This method will return the test plan object when the testID is given.
     * @param testID : testID of the requested test plan.
     * @return the requested test plan object
     */
    public TestPlan getTestPlan(int testID);

    /**
     *This method will return all the test plans along with their corresponding
     *reports belonging to a particular user.
     * @param userID : User ID of the current user.
     * @return a map containing testPlan IDs and the corresponding testPlanDTOs.
     */
    public Map<Integer, TestPlanDTO> getTestPlans(String userID);
}
