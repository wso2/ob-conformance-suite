package com.wso2.finance.open.banking.conformance.test.core;

import com.google.gson.JsonObject;
import com.wso2.finance.open.banking.conformance.mgt.helpers.XmlHelper;
import com.wso2.finance.open.banking.conformance.mgt.models.Specification;
import com.wso2.finance.open.banking.conformance.mgt.models.TestPlan;
import com.wso2.finance.open.banking.conformance.test.core.testrunners.TestPlanRunner;
import cucumber.api.cli.Main;
import com.wso2.finance.open.banking.conformance.test.core.utilities.Log;

import java.io.File;
import java.io.FileNotFoundException;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.util.Map;

public class CoreTestRunner {

    public static Map<String,List<JsonObject>> runTestPlan(TestPlan testPlan){
        Context.getInstance().init(testPlan);

        TestPlanRunner planRunner = new TestPlanRunner(testPlan);
        return planRunner.runTestPlan();
    }
}
