package com.baseClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.extentReports.DateUtils;
import com.extentReports.ExtentReportManager;

public class BaseClass {

	public WebDriver driver;
	public Properties properties;
	public ExtentTest logger;
	public ExtentReports report = ExtentReportManager.getReportInstance();

	public HashMap<String, String> map = new HashMap<String, String>();
	SoftAssert softAssert = new SoftAssert();


	public void invokeBrowser(String browserName) throws IOException {

		try {
			if (browserName.equalsIgnoreCase("Chrome")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\Driver\\chromedriver.exe");
				driver = new ChromeDriver();
			} else if (browserName.equalsIgnoreCase("Mozila")) {
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "\\src\\main\\resources\\driversgeckodriver");
				driver = new FirefoxDriver();
			} else {
				driver = new SafariDriver();

			}

		} catch (Exception e) {
			reportFail(e.getMessage());
			e.printStackTrace();
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
		driver.manage().window().maximize();

		// load properties file

		if (properties == null) {
			properties = new Properties();
			try {
				FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
						+ "\\src\\main\\resources\\com\\objectRepo\\objectRepository.properties");
				properties.load(fis);
			} catch (IOException e) {
				reportFail(e.getMessage());
				e.printStackTrace();
			}
		}
		
	}
	/*********************************Browser Close************************/
	public void tearDown() {
		driver.close();
	}

	public void quitBrowser() {
		driver.quit();
	}
	
	
	/*********************************Open URL ***************************/
		public void openUrl(String webURL) throws IOException {
			try {
				driver.get(properties.getProperty(webURL));
				reportPass(webURL + "Identified Successfully");
			} catch (Exception e) {
				reportFail(e.getMessage());
				e.printStackTrace();
			}
		}
	/****************** Identify Element ***********************/

	public WebElement getElement(String locatorKey) {
		WebElement element = null;
		if (locatorKey.endsWith("_Id")) {

			element = driver.findElement(By.id(properties.getProperty(locatorKey)));

		} else if (locatorKey.endsWith("_Xpath")) {

			element = driver.findElement(By.xpath(properties.getProperty(locatorKey)));

		} else if (locatorKey.endsWith("_ClassName")) {

			element = driver.findElement(By.className(properties.getProperty(locatorKey)));

		} else if (locatorKey.endsWith("_CSS")) {

			element = driver.findElement(By.cssSelector(properties.getProperty(locatorKey)));

		} else if (locatorKey.endsWith("_LinkText")) {

			element = driver.findElement(By.linkText(properties.getProperty(locatorKey)));

		} else if (locatorKey.endsWith("_PartialLinkText")) {

			element = driver.findElement(By.partialLinkText(properties.getProperty(locatorKey)));

		} else if (locatorKey.endsWith("_Name")) {

			element = driver.findElement(By.name(properties.getProperty(locatorKey)));
		} else {

		}
		return element;
	}

	/****************** Reporting Functions ***********************/

	public void reportFail(String reportString) throws IOException {

		logger.log(Status.FAIL, reportString);
		takeScreenShotOnFailure();
		Assert.fail(reportString);
	}

	public void reportPass(String reportString) {

		logger.log(Status.PASS, reportString);
	}

	@AfterMethod
	public void afterTest() {
		softAssert.assertAll();
		driver.quit();
	}

	/****************** ScreenShot Functions  ***********************/
	public void takeScreenShotOnFailure() throws IOException {

		TakesScreenshot takeScreenShot = (TakesScreenshot) driver;
		File source = takeScreenShot.getScreenshotAs(OutputType.FILE);

		File destination = new File(
				System.getProperty("user.dir") + "\\ScreenShot" + DateUtils.getTimeStamp() + ".png");
        FileUtils.copyFile(source, destination);
		logger.addScreenCaptureFromPath(
				System.getProperty("user.dir") + "\\ScreenShot" + DateUtils.getTimeStamp() + ".png");

	}
	
	/****************** Assertion Functions ***********************/
	public void assertTrue(boolean flag) {
		softAssert.assertTrue(flag);
	}

	public void assertfalse(boolean flag) {
		softAssert.assertFalse(flag);
	}

	public void assertequals(String actual, String expected) throws IOException {
		try {
			logger.log(Status.INFO, "Assertion : Actual is -" + actual + " And Expacted is - " + expected);
			softAssert.assertEquals(actual, expected);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}
	
	
	
	
	/****************** Enter Text  ***********************/
	public void enterText(String locatorValue, String data) throws IOException {
		try {
			getElement(locatorValue).sendKeys(data);
			reportPass(data + "entered successfully in" + locatorValue);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/****************** Enter Value ***********************/
	public void elementClick(String locatorValue) throws IOException {
		try {
			getElement(locatorValue).click();
			reportPass(locatorValue + "clicked successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

}