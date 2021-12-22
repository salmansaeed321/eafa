package com.project.userflow;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * @author Salman Saeed
 * @email salmansaeed321@hotmail.com
 * @version 3.1
 **/

@RunWith(Cucumber.class)
@CucumberOptions(
		// To generate report in .json and .html formats
		// .json format is also mandatory for cucumber main reporting
		plugin = { "json:target/cucumber.json", "html:target/cucumber.html" },
		
		// To define feature files folder
		features = "src/main/resources/features",

		// We can comment/uncomment tags as per requirement. Only uncommented tags will be excuted.
		// tags = { "@UploadDocNegativeFlows,@uopladDoc1"}
			tags = { "@Google"})

public class CodeRunner {

}
