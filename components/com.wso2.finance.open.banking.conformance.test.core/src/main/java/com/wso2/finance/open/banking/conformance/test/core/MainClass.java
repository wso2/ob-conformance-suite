package com.wso2.finance.open.banking.conformance.test.core;

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

public class MainClass {

    public static void main(String[] args ) {
        Log.info("Starting Tests..!");

        try {
            Specification specification = XmlHelper.unmarshallSepcificationXML(new File("components/" +
                    "com.wso2.finance.open.banking.conformance.mgt/src/main/resources/example_spec.xml"));

            List<Specification> specList = new ArrayList<>();
            specList.add(specification);
            specList.add(specification);
            specList.add(specification);

           /* OutputStream output = new FileOutputStream("components/" +
                    "com.wso2.finance.open.banking.conformance.mgt/src/main/resources/out.xml");

            XmlHelper.marshallSpecification(specification,output);*/

            TestPlan plan = new TestPlan(specList);
            Context.getInstance().init(plan);

            TestPlanRunner planRunner = new TestPlanRunner(plan);
            planRunner.runTestPlan();
            

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
