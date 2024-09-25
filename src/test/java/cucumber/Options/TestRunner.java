package cucumber.Options;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
//"tags" is the way to run specific scenarios market with the same tag
@CucumberOptions(
        //like testng you can run entire folder or specific file
        features = "src/test/java/features",// "src/test/java/features/placeValidations.feature"
        glue={"stepDefinitions"},
        plugin = "json:target/jsonReports/cucumber-report.json"
        //tags = "@DeletePlace"
)

public class TestRunner {

}
