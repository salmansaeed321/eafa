package com.project.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.languagetool.JLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.Rule;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.spelling.SpellingCheckRule;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;

import com.project.utility.EmailVerification;
import com.project.utility.SendEmail;
import com.project.utility.ZipCompressor;

import cucumber.api.Scenario;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

/**
 * @author Salman Saeed
 * @email salmansaeed321@hotmail.com
 * @version 3.1
 **/

public class CodeBase {

	protected WebDriver webDriver;
	@Value("${webdriver.wait.sleepInMillis:500}")
	private long sleepInMillis;
	@Value("${webdriver.wait.timeOutInSeconds:2}")
	private long timeOutInSeconds;
	public static Properties OR;
	public static Properties Config;
	public static FileInputStream fis;
	private static Logger logger = LogManager.getLogger();

	// Appium Mobile
	private String reportDirectory = "reports";
	private String reportFormat = "xml";
	private String testName = "Untitled";
	protected AndroidDriver<MobileElement> androidDriver = null;

	// WebDriver wait
	public WebDriver webDriver() {
		return webDriver;
	}

	public WebDriverWait driverWait() {
		return driverWait(webDriver, timeOutInSeconds, sleepInMillis);
	}

	public WebDriverWait driverWait(long timeOutInSeconds) {
		return driverWait(webDriver, timeOutInSeconds, sleepInMillis);
	}

	public WebDriverWait driverWait(long timeOutInSeconds, long sleepInMillis) {
		return driverWait(webDriver, timeOutInSeconds, sleepInMillis);
	}

	public WebDriverWait driverWait(WebDriver webDriver, long timeOutInSeconds, long sleepInMillis) {
		return new WebDriverWait(webDriver, timeOutInSeconds, sleepInMillis);
	}

	public ExpectedCondition<WebElement> elementBy(By locator) {
		return ExpectedConditions.presenceOfElementLocated(locator);
	}

	// Mobile APPIUM /// ===========================
	public WebDriverWait androidDriverWait() {
		return androidDriverWait(androidDriver, timeOutInSeconds, sleepInMillis);
	}

	public WebDriverWait androidDriverWait(long timeOutInSeconds) {
		return androidDriverWait(androidDriver, timeOutInSeconds, sleepInMillis);
	}

	public WebDriverWait androidDriverWait(long timeOutInSeconds, long sleepInMillis) {
		return androidDriverWait(androidDriver, timeOutInSeconds, sleepInMillis);
	}

	public WebDriverWait androidDriverWait(AndroidDriver<MobileElement> androidDriver, long timeOutInSeconds,
			long sleepInMillis) {
		return new WebDriverWait(androidDriver, timeOutInSeconds, sleepInMillis);
	}

	public ExpectedCondition<WebElement> androidElementBy(By locator) {
		return ExpectedConditions.presenceOfElementLocated(locator);
	}

	public CodeBase() {
		// loading property files
		if (OR == null) {
			OR = new Properties();
			try {
				fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\OR.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				OR.load(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Config = new Properties();
			try {
				fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\Config.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				Config.load(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// In case of web test case opens url in selected browser - defined in
	// Config.properties file
	public void navigateToApp() {
		
		if (Config.getProperty("browser").equals("Chrome")) {
			logger.debug("Staring Chrome browser...");
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\testdata\\drivers\\chromedriver.exe");
			webDriver = new ChromeDriver();

		} else if (Config.getProperty("browser").equals("Firefox")) {
			logger.debug("Staring Firefox browser...");
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "\\testdata\\drivers\\geckodriver.exe");
			webDriver = new FirefoxDriver();
		}

		// Hook to close browser after execution
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

			public void run() {
				logger.debug("Closing all " + Config.getProperty("browser") + " browsers...");
				webDriver.quit();
			}
		}));

		webDriver.manage().window().maximize();
		webDriver.manage().deleteAllCookies();

		logger.debug("Browser: " + Config.getProperty("browser") + ", Page load: " + Config.getProperty("host.url"));
		webDriver.navigate().to(Config.getProperty("host.url"));

		// Attempts multiple times to verify the page has successfully loaded.
		// page-load-confirmation is defined in Config.properties file
		for (int y = 0; y <= 2; y++) {
			try {
				driverWait().until(elementBy(By.xpath(Config.getProperty("page-load-confirmation"))));
				break;
			} catch (Exception e) {
				logger.debug("Page load issue, Timed out :" + y + "/2");
				webDriver.navigate().to(Config.getProperty("host.url"));
			}
		}
	}

	// Appium configuration - Details are defined in Config.properties file
	public void navigateToMobileApp() throws MalformedURLException {
		logger.debug("Starting Android application...");
		DesiredCapabilities dc = new DesiredCapabilities();

		dc.setCapability("reportDirectory", reportDirectory);
		dc.setCapability("reportFormat", reportFormat);
		dc.setCapability("testName", testName);
		dc.setCapability("deviceName", Config.getProperty("device-name"));
		dc.setCapability(AndroidMobileCapabilityType.PLATFORM, Config.getProperty("platform"));
		dc.setCapability(MobileCapabilityType.UDID, Config.getProperty("device-udid"));
		dc.setCapability(MobileCapabilityType.APP, Config.getProperty("app-apk-path"));
		dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, Config.getProperty("app-package-name"));
		dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, Config.getProperty("app-activity-name"));
		androidDriver = new AndroidDriver<MobileElement>(new URL(Config.getProperty("android.host.url")), dc);
		new WebDriverWait(androidDriver, 10).until(
				ExpectedConditions.presenceOfElementLocated(By.id(Config.getProperty("app-launch-confirmation"))));
	}

	// Method to provide input in text fields. Gets 'text' and 'locator' from
	// Cucumber feature files
	public void input(String text, By locator) {
		boolean result = false;

		if (webDriver != null) {
			try {
				logger.debug("page input: " + text + ", " + locator);

				driverWait().until(elementBy(locator)).clear();
				driverWait().until(elementBy(locator)).sendKeys(text);
				result = true;
			} catch (Exception e) {
				logger.error("page input: ELEMENT NOT FOUND :" + text + ", " + locator + ", " + e.getMessage());
			}
		}

		if (androidDriver != null && result == false) {
			try {
				logger.debug("mobile input: " + text + ", " + locator);
				androidDriverWait().until(androidElementBy(locator)).sendKeys(text);
				result = true;
			} catch (Exception e) {
				logger.error("mobile input: " + e.getMessage());
			}
		}
	}

	public void click(By locator) {
		boolean result = false;

		if (webDriver != null) {

			WebElement element = driverWait().until(elementBy(locator));
			logger.debug("page click: " + element.getText() + ", " + locator);

			try {
				if (result == false) {
					try {
						WebDriverWait wait = new WebDriverWait(webDriver, 2, 200);
						wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
						result = true;
					} catch (Exception e) {
					}
				}
				if (result == false) {
					try {
						element.click();
						result = true;
					} catch (Exception e) {
					}
				}
				if (result == false) {
					try {
						driverWait().until(elementBy(locator)).click();
						result = true;
					} catch (Exception e) {
					}
				}
				if (result == false) {
					try {
						driverWait().until(elementBy(locator)).sendKeys(Keys.ENTER);
						result = true;
					} catch (Exception e) {
					}
				}
				if (result == false) {
					try {
						Actions actions = new Actions(webDriver);
						WebElement menu = webDriver.findElement(locator);
						actions.moveToElement(menu);

						WebElement subMenu = webDriver.findElement(locator);
						actions.moveToElement(subMenu);
						actions.click().build().perform();
						result = true;
					} catch (Exception e) {
					}
				}
				if (result == false) {
					try {
						JavascriptExecutor jse = (JavascriptExecutor) webDriver;
						jse.executeScript(
								"window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));");
						driverWait().until(elementBy(locator)).click();
						result = true;
					} catch (Exception e) {
					}
				}
			} catch (Exception e) {
				logger.debug("Issue in click: " + e.getMessage());
				e.printStackTrace();
				logger.error("Could not found element to click at xpath: " + locator);
			}
		}

		if (androidDriver != null && result == false) {
			try {
				logger.debug("mobile click: " + locator);
				androidDriverWait().until(androidElementBy(locator)).click();
				result = true;
			} catch (Exception e) {
				logger.error("mobile click " + e.getMessage());
			}
		}
	}

	public void dropdown(String text, By locator) {
		boolean result = false;
		if (webDriver != null) {
			try {
				logger.debug("page dropdown: " + locator + "; " + text);
				Select dropdown = new Select(driverWait().until(elementBy(locator)));
				dropdown.selectByVisibleText(text);
				result = true;

			} catch (Exception e) {
				logger.error("DROPDOWN VALUE NOT FOUND :" + text + ", " + locator);
			}
		}
		if (androidDriver != null && result == false) {
			try {
				logger.debug("mobile dropdown: " + locator + "; " + text);
				androidDriverWait().until(androidElementBy(locator)).click();
				// Select item "by name" from drop down list.
				androidDriver.findElement(By.name(text)).click();
			} catch (Exception e) {
				logger.error("mobile dropdown error" + e.getMessage());
			}
		}
		// dropdown.getFirstSelectedOption();
		// List<WebElement> options = dropdown.getOptions();
		// System.out.println(options.size());
		// for(int i=0; i < options.size(); i++){
		// System.out.println(options.get(i).getText());
	}

	public void radioButton(By locator) {
		boolean result = false;
		if (webDriver != null) {
			try {
				logger.debug("page radioButton: " + locator);

				WebDriverWait wait = new WebDriverWait(webDriver, 15);
				wait.until(ExpectedConditions.elementToBeClickable(locator));

				// List<WebElement> radio =
				// webDriver.findElements(By.name("transferMethod"));
				List<WebElement> radio = webDriver.findElements(locator);
				radio.get(0).click();
				result = true;
				/*
				 * try { logger.debug(radio.get(0).getAttribute("value"));
				 * logger.debug(radio.get(0).getAttribute("checked")); } catch (Exception e) { }
				 */
			} catch (Exception e) {
				logger.error("RADIO BUTTON VALUE NOT FOUND :" + locator);
			}
		}
		if (androidDriver != null && result == false) {
			try {
				logger.debug("mobile radioButton: " + locator);
				List<MobileElement> radio = androidDriver.findElements(locator);
				radio.get(0).click();
			} catch (Exception e) {
				logger.error("mobile radio button error: " + e.getMessage());
			}
		}
	}

	public void checkbox(By locator) {
		boolean result = false;
		if (webDriver != null) {
			try {
				logger.debug("page checkbox: " + locator);
				WebElement element = driverWait().until(elementBy(locator));
				element.click();
				result = true;
			} catch (NoSuchElementException e) {
				logger.error("Checkbox: element not found" + e.getMessage());
				Assert.fail("CHECKBOX ELEMENT NOT FOUND :" + locator);
			}
		}
		if (androidDriver != null && result == false) {
			logger.debug("mobile checkbox: " + locator);
			androidDriverWait().until(androidElementBy(locator)).click();
		}
	}

	// Method to scroll till the element so that user can interact with that
	public void scrollToElement(By locator) throws Throwable {
		logger.debug("page scroll click: " + locator);
		WebElement element = driverWait().until(elementBy(locator));
		((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void verifyExpectedTextIsDisplayed(String expectedText, By locator) {
		boolean result = false;
		if (webDriver != null) {
			logger.debug("page - verifyExpectedTextIsDisplayed: " + locator + "; Expected: " + expectedText
					+ " Actual: " + driverWait().until(elementBy(locator)).getText());
			String results = driverWait().until(elementBy(locator)).getText();
			Assert.assertTrue("Results for \"" + results + "\" are shown",
					driverWait().until(elementBy(locator)).getText().contains(expectedText));
			result = true;
		}
		
		if (androidDriver != null && result == false) {
			logger.debug("mobile - verifyExpectedTextIsDisplayed: " + locator + "; Expected: " + expectedText
					+ " Actual: " + androidDriverWait().until(androidElementBy(locator)).getText());
			String results = androidDriverWait().until(androidElementBy(locator)).getText();
			Assert.assertTrue("Results for \"" + results + "\" are shown",
					androidDriverWait().until(androidElementBy(locator)).getText().contains(expectedText));
		}
	}

	// This verifies user action e.g. user clicks and requires a verification
	// that element x should be displayed after click
	public void verifyUserAction(By locator) {

		Boolean result = driverWait().until(elementBy(locator)).isDisplayed();
		if (result.equals(true)) {
			logger.info("User action has been verified");
			
		} else {
			Assert.fail("Could not find the value by locator: " + locator);
		}
	}

	// Method to upload document / file
	public void uploadid(By locator, String filename) {

		logger.debug("uploadid: " + locator + "; " + filename);
		try {
			WebElement upload = driverWait().until(elementBy(locator));
			upload.sendKeys(System.getProperty("user.dir") + "//testdata//drivers//" + filename);
			
		} catch (Exception e) {
			logger.error("Exception in file uploading: " + e.getMessage());
		}
	}

	public void acceptAlertMsg() {
		WebDriverWait wait = new WebDriverWait(webDriver, 15);
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = webDriver.switchTo().alert();
		alert.accept();
	}
	
	// Switch to frame by locator
	// This helps when an element is in iframe and not reachable otherwise
	public void iframeSwitch(By locator) {
		WebElement frame = driverWait().until(elementBy(locator));
		webDriver.switchTo().frame(frame);
	}
	


	public void takeScreenshotOnError(Scenario scenario) throws IOException {
		boolean result = false;
		if (scenario.isFailed()) {
			if (webDriver != null) {
				try {
					logger.debug("Taking webpage screenshot...");
					// TakesScreenshot screenShot = (TakesScreenshot)
					// webDriver();
					// File source =
					// screenShot.getScreenshotAs(OutputType.FILE);

					WebDriver augmentedDriver = new Augmenter().augment(webDriver);
					File source = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddhhmmss");
					String dateAsString = simpleDateFormat.format(new Date());
					logger.debug(dateAsString);
					String dest = System.getProperty("user.dir") + "/screenshots/" + scenario.getName() + ".png";
					File destination = new File(dest);
					FileUtils.copyFile(source, destination);
					result = true;

					try {
						scenario.write("Current Page URL: " + webDriver.getCurrentUrl());
						// byte[] screenshot =
						// getScreenshotAs(OutputType.BYTES);
						byte[] screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
						scenario.embed(screenshot, "image/png");
					} catch (WebDriverException somePlatformsDontSupportScreenshots) {
						logger.error(somePlatformsDontSupportScreenshots.getMessage());
					}

				} catch (WebDriverException wde) {
					logger.error("Error in taking screenshot: " + wde.getMessage());
				} catch (ClassCastException cce) {
					cce.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (androidDriver != null && result == false) {
			try {
				logger.debug("Taking mobile screenshot...");
				File srcFile = androidDriver.getScreenshotAs(OutputType.FILE);
				String filename = UUID.randomUUID().toString();
				File targetFile = new File(
						System.getProperty("user.dir") + "/screenshots/" + scenario.getName() + filename + ".png");
				FileUtils.copyFile(srcFile, targetFile);
			} catch (Exception e) {
				logger.error("mobile app screenshot could not be taken");
			}
		}
	}

	// Method to open any link in a new tab
	public void openNewTab(By locator) {
		logger.debug("Opening a new tab by clicking at: " + locator);
		Actions builder = new Actions(this.webDriver);
		WebElement subMenu = driverWait().until(elementBy(locator));
		Action ctrlClick = builder.keyDown(Keys.CONTROL).moveToElement(subMenu).click().build();
		ctrlClick.perform();
	}

	// Switch between tabs by providing tab index - starts from 0
	public void switchTab(int tabIndex) {
		logger.debug("Swtitching to tab: " + tabIndex);
		ArrayList<String> tabs = new ArrayList<String>(webDriver.getWindowHandles());
		webDriver.switchTo().window(tabs.get(tabIndex));
	}

	// Open any url in existing browser/tab
	public void navigateToSpecificUrl(String navigationUrl) {
		logger.debug("Navigating to: " + navigationUrl);
		webDriver.navigate().to(navigationUrl);
	}

	// Method to check spellings of the whole page
	public void spellChecker() {
		try {
			logger.debug("Checking page spellings ...");
			WebElement body = driverWait().until(elementBy(By.tagName("body")));
			String bodyText = body.getText();
			logger.debug(body.getText());

			// Comment / uncomment British or American Dictionary as per
			// requirement
			JLanguageTool langTool = new JLanguageTool(new BritishEnglish());
			// JLanguageTool langTool = new JLanguageTool(new
			// AmericanEnglish());
			for (Rule rule : langTool.getAllActiveRules()) {
				if (rule instanceof SpellingCheckRule) {
					List<String> wordsToIgnore = Arrays.asList("Vowpay");
					((SpellingCheckRule) rule).addIgnoreTokens(wordsToIgnore);
				}
			}
			List<RuleMatch> matches = langTool.check(bodyText);
			for (RuleMatch match : matches) {
				logger.debug("Potential error at characters " + match.getFromPos() + "-" + match.getToPos() + ": "
						+ match.getMessage());
				logger.debug("Suggested correction(s): " + match.getSuggestedReplacements());

			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}

	public String getText(By locator) {
		// To be used in some other method to get and use displayed text
		String text = null;
		boolean result = false;

		if (webDriver != null) {
			try {
				text = driverWait().until(elementBy(locator)).getText();
				result = true;
				return text;
			} catch (Exception e) {
				logger.error("web get text" + e.getMessage());
			}
		}

		if (androidDriver != null && result == false) {
			try {
				text = androidDriverWait().until(androidElementBy(locator)).getText();
				return text;
			} catch (Exception e) {
				logger.error("mobile get text: " + e.getMessage());
			}
		}
		return text;
	}

	public void emailVerification(String tokenOrOtp, String email, String password, String emailSubject)
			throws Exception {
		EmailVerification.getEmail("tokenOrOtp", "email", "password", "emailSubject");
	}

	public By getElementByType(String locator) {
		By typeElement = null;
		String locatorValue = locator.substring(locator.lastIndexOf('-') + 1);
		if (locatorValue.contains("xpath")) {
			typeElement = By.xpath(OR.getProperty(locator));

		} else if (locatorValue.contains("id")) {
			typeElement = By.id(OR.getProperty(locator));

		} else if (locatorValue.contains("linktext")) {
			typeElement = By.linkText(OR.getProperty(locator));

		} else if (locatorValue.contains("css")) {
			typeElement = By.cssSelector(OR.getProperty(locator));
		}

		return typeElement;
	}

	// To zip generated report
	public void zipReport() {
		logger.debug("Creating Report.zip file...");
		ZipCompressor.zip();
	}

	// To send email with report attached
	public void sendEmailWithAttachment() {
		logger.debug("Sending email with attachment...");
		SendEmail.sendEmail();

	}

	// To quit browser after test execution
	public void closeDriver() {
		if (webDriver != null) {
			logger.debug("Closing " + Config.getProperty("browser") + " browser...");
			webDriver.quit();
		}
		if (androidDriver != null) {
			logger.debug("Closing Android app...");
			androidDriver.quit();
		}
	}
}
