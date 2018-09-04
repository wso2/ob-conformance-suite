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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainClass {

    public static void main(String[] args ) {
        Log.info("Starting Tests..!");

        /*String[] argv = new String[]{"-g", "com.wso2.finance.open.banking.conformance.test.core.steps",
                "components/com.wso2.finance.open.banking.conformance.test.core/src/main/resources/features/test.feature"
        }
        ;*/

      /*  String[] argv = new String[]{"-g", "com.wso2.finance.open.banking.conformance.test.core.steps.v1_0_0",
                "components/com.wso2.finance.open.banking.conformance.test.core/src/main/resources/features/v1_0_0/opendata/atms.feature"
        }
                ;*/

        //String[] argv = new String[]{"-h"};
      /*  ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Main.main(argv);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }*/
       /* try {
            byte exitstatus = Main.run(argv, contextClassLoader);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        try {
            Specification specification = XmlHelper.unmarshallSepcificationXML(new File("components/" +
                    "com.wso2.finance.open.banking.conformance.mgt/src/main/resources/example_spec.xml"));

            List<Specification> specList = new ArrayList<>();
            specList.add(specification);
            specList.add(specification);
            specList.add(specification);


            TestPlan plan = new TestPlan(specList);

            TestPlanRunner planRunner = new TestPlanRunner(plan);
            planRunner.runTestPlan();
            

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



    }

}
