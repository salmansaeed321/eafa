package com.project.userflow;

import org.openqa.selenium.By;
import com.project.pages.CodeBase;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author Salman Saeed
 * @email salmansaeed321@hotmail.com
 * @version 3.1
 **/

public class StepDefinitions extends CodeBase {

	// Below method will execute after each scenario
	@After
	public void embedScreenshot(Scenario scenario) throws Exception {
		takeScreenshotOnError(scenario);
		closeDriver();
//		SendEmail.sendEmail();
	}

	@Given("^user opens \"([^\"]*)\" application$")
	public void user_opens_the_application(String platform) throws Throwable {
		if (platform.equals("web")) {
			navigateToApp();
			
		} else if (platform.equals("mobile")) {
			navigateToMobileApp();
		}
	}

	@When("^user clicks on \"([^\"]*)\"$")
	public void user_clicks_on(String locator) throws Throwable {
		By element = getElementByType(locator);
		click(element);
		}
		

	@When("^user enters \"([^\"]*)\" as \"([^\"]*)\"$")
	public void user_enters_as(String text, String locator) throws Throwable {
		By element = getElementByType(locator);
		input(text, element);
	}
		
	@And("^user selects radio button \"([^\"]*)\"$")
	public void user_selects_radio_button(String locator) throws Throwable {

		By element = getElementByType(locator);
		radioButton(element);
	}

	@And("^user selects \"([^\"]*)\" from \"([^\"]*)\" dropdown$")
	public void user_selects_dropdown(String text, String locator) throws Throwable {

		By element = getElementByType(locator);
		dropdown(text, element);		
	}

	@Then("^user verifies the action by \"([^\"]*)\"$")
	public void user_verifies_the_action(String locator) throws Throwable {

		By element = getElementByType(locator);
		verifyUserAction(element);
	}

	@Then("^verify \"([^\"]*)\" text is displayed at \"([^\"]*)\"$")
	public void verify_text_is_displayed_at(String expectedText, String locator) throws Throwable {

		By element = getElementByType(locator);
		verifyExpectedTextIsDisplayed(expectedText, element);
	}

	@And("^user switches to iframe \"([^\"]*)\"$")
	public void user_switches_to_iframe(String locator) {

		By element = getElementByType(locator);
		iframeSwitch(element);
	}

	@And("^user clicks on a checkbox \"([^\"]*)\"$")
	public void user_clicks_on_a_checkbox(String locator) {

		By element = getElementByType(locator);
		checkbox(element);
	}

	@And("^user uploads \"([^\"]*)\" and selects file \"([^\"]*)\"$")
	public void user_uploads(String locator, String filename) throws Throwable {
		By element = getElementByType(locator);
		uploadid(element, filename);
	}

	@And("^user accepts the alert$")
	public void alertAccept() {
		acceptAlertMsg();
	}

	@And("^user verifies page spellings$")
	public void verify_page_spellings() {
		spellChecker();
	}

	@And("^user opens a new tab by clicking \"([^\"]*)\"$")
	public void user_opens_a_new_tab(String locator) {
		By element = getElementByType(locator);
		openNewTab(element);
	}

	@And("^user switches to tab \"([^\"]*)\"$")
	public void user_switches_to_tab(int tabIndex) {
		switchTab(tabIndex);
	}

	@And("^user navigates to \"([^\"]*)\"$")
	public void user_navigates_to(String navigationUrl) {
		navigateToSpecificUrl(OR.getProperty(navigationUrl));
	}
	
	@And("^user gets \"([^\"]*)\" against \"([^\"]*)\" and \"([^\"]*)\" with subject \"([^\"]*)\"$")
	public void user_gets_token_or_otp_from_email(String tokenOrOtp, String email, String password, String emailSubject) throws Exception{
		emailVerification("tokenOrOtp", "email", "password", "emailSubject");
	}

	@And("^user quits the browser$")
	public void close_browser() {
		closeDriver();
	}

}
