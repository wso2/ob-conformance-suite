

package testrunners;

import org.testng.annotations.Test;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;



@CucumberOptions(features={"src/test/resources/features"}
        ,glue={"steps"}
        ,plugin = {"pretty", "html:target/cucumber"}
)
@Test
public class SampleTestRunner extends AbstractTestNGCucumberTests{

}
