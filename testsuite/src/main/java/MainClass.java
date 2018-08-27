import cucumber.api.cli.Main;

import java.io.IOException;

//import java.io.IOException;

public class MainClass {

    public static void main(String[] args ) {
        // do something here...
        System.out.println("Start Test Suite..!test.feature");

        String[] argv = new String[]{"-g", "steps", "./src/test/resources/features/test.feature"};
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
