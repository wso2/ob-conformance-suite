package com.wso2.finance.open.banking.conformance.test.core;

import com.google.gson.JsonObject;
import com.wso2.finance.open.banking.conformance.mgt.testconfig.TestPlan;
import com.wso2.finance.open.banking.conformance.test.core.testrunners.TestPlanRunner;

import java.util.List;
import java.util.Map;

public class CoreTestRunner {

    public static Map<String,List<JsonObject>> runTestPlan(TestPlan testPlan){
        Context.getInstance().init(testPlan);

        TestPlanRunner planRunner = new TestPlanRunner(testPlan);
        return planRunner.runTestPlan();
    }
}
