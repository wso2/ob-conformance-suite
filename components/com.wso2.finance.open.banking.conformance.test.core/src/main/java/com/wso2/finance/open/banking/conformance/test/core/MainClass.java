package com.wso2.finance.open.banking.conformance.test.core;

import cucumber.api.cli.Main;
import com.wso2.finance.open.banking.conformance.test.core.utilities.Log;


public class MainClass {

    public static void main(String[] args ) {
        Log.info("Starting Tests..!");

        /*String[] argv = new String[]{"-g", "com.wso2.finance.open.banking.conformance.test.core.steps",
                "components/com.wso2.finance.open.banking.conformance.test.core/src/main/resources/features/test.feature"
        }
        ;*/

        String[] argv = new String[]{"-g", "com.wso2.finance.open.banking.conformance.test.core.steps.v1_0_0",
                "components/com.wso2.finance.open.banking.conformance.test.core/src/main/resources/features/v1_0_0/opendata/atms.feature"
        }
                ;

        //String[] argv = new String[]{"-h"};
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Main.main(argv);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
       /* try {
            byte exitstatus = Main.run(argv, contextClassLoader);
        } catch (IOException e) {
            e.printStackTrace();
        }*/


    }

}
