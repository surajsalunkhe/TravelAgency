package TestRunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
/*
Author: Suraj Salunkhe
Date:18th Dec 2021
*/
@RunWith(Cucumber.class)
@CucumberOptions(
		features = { "./src/test/resources/featureFiles/" },
		glue = {"stepDefination", "appHooks" },
		plugin = { "pretty", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" },
		dryRun = false
)
public class TestRunner {

}
