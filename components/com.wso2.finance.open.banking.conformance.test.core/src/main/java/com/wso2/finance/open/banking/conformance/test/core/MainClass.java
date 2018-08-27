package com.wso2.finance.open.banking.conformance.test.core;

import cucumber.api.cli.Main;

import java.io.IOException;

//import java.io.IOException;

public class MainClass {

    public static void main(String[] args ) {
        // do something here...
        System.out.println("Start Test Suite..!");

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
