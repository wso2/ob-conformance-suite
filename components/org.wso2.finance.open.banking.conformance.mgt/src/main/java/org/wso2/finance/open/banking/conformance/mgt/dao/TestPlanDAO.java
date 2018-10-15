package org.wso2.finance.open.banking.conformance.mgt.dao;

import org.wso2.finance.open.banking.conformance.mgt.dto.TestPlanDTO;
import org.wso2.finance.open.banking.conformance.mgt.testconfig.TestPlan;

import java.util.Map;

/**
 * Interface to create a TestPlanDAO.
 */
public interface TestPlanDAO {
    public int storeTestPlan(String userID, TestPlan testPlan);
    public TestPlan getTestPlan(String userID, int testID);
    public Map<Integer, TestPlanDTO> getTestPlans(String userID);
}
