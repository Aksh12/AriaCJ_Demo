package com.ariacj.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.ariacj.utilities.ExcelReader;
import com.ariacj.utilities.TestUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	/*
	 * WebDriver - done Properties - done Logs - log4j jar, .log,
	 * log4j.properties, Logger ExtentReports DB Excel Mail ReportNG,
	 * ExtentReports Jenkins
	 * 
	 */

	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel = new ExcelReader();
	public static WebDriverWait wait;
	public ExtentReports rep = com.ariacj.utilities.ExtentManager.getInstance();
	public static ExtentTest test;
	public static String browser;
	

	@BeforeSuite
	public void setUp() {
		System.out.println("setup 1");

		if (driver == null) {

			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("Config file loaded !!!");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

//			try {
//				fis = new FileInputStream(
//						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			try {
				OR.load(fis);
				
				log.debug("OR file loaded !!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			if(System.getenv("browser")!=null && !System.getenv("browser").isEmpty()){
				
				browser = System.getenv("browser");
				System.out.println(browser + " : browser");
			}else{
				
				browser = config.getProperty("browser");
				
			}
			
			config.setProperty("browser", browser);
			
			
			

			if (config.getProperty("browser").equals("firefox")) {

				// System.setProperty("webdriver.gecko.driver", "gecko.exe");
				//driver = new FirefoxDriver();

			} else if (config.getProperty("browser").equalsIgnoreCase("chrome")) {

				//System.out.println("under webdrivermanage");
			
				WebDriverManager.chromedriver().setup();
			
				//System.getProperty("C:\\Users\\Apurva\\eclipse\\AraiaCJ_LocatioAvailable\\chromedriver.exe");
			   driver=new ChromeDriver();
				System.out.println("driver launched");
				log.debug("Chrome Launched !!!");
			} else if (config.getProperty("browser").equals("ie")) {

				//System.setProperty("webdriver.ie.driver",
					//	System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\IEDriverServer.exe");
				//driver = new InternetExplorerDriver();

			}

			driver.get(config.getProperty("testsiteurl"));
			System.out.println();
			log.debug("Navigated to : " + config.getProperty("testsiteurl"));
			//driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 30);
		}

	}

//	public void click(String locator) {
//
//		if (locator.endsWith("_CSS")) {
//			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
//		} else if (locator.endsWith("_XPATH")) {
//			driver.findElement(By.xpath(OR.getProperty(locator))).click();
//		} else if (locator.endsWith("_ID")) {
//			driver.findElement(By.id(OR.getProperty(locator))).click();
//		}
//		test.log(LogStatus.INFO, "Clicking on : " + locator);
//	}
//
//	public void type(String locator, String value) {
//
//		if (locator.endsWith("_CSS")) {
//			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
//		} else if (locator.endsWith("_XPATH")) {
//			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
//		} else if (locator.endsWith("_ID")) {
//			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
//		}
//
//		test.log(LogStatus.INFO, "Typing in : " + locator + " entered value as " + value);
//
//	}
//	
//	static WebElement dropdown;
//
//	public void select(String locator, String value) {
//
//		if (locator.endsWith("_CSS")) {
//			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
//		} else if (locator.endsWith("_XPATH")) {
//			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
//		} else if (locator.endsWith("_ID")) {
//			dropdown = driver.findElement(By.id(OR.getProperty(locator)));
//		}
//		
//		Select select = new Select(dropdown);
//		select.selectByVisibleText(value);
//
//		test.log(LogStatus.INFO, "Selecting from dropdown : " + locator + " value as " + value);
//
//	}
//
//	public boolean isElementPresent(By by) {
//
//		try {
//
//			driver.findElement(by);
//			return true;
//
//		} catch (NoSuchElementException e) {
//
//			return false;
//
//		}
//
//	}
//
//	public static void verifyEquals(String expected, String actual) throws IOException {
//
//		try {
//
//			Assert.assertEquals(actual, expected);
//
//		} catch (Throwable t) {
//
//			TestUtil.captureScreenshot();
//			// ReportNG
//			Reporter.log("<br>" + "Verification failure : " + t.getMessage() + "<br>");
//			Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img src=" + TestUtil.screenshotName
//					+ " height=200 width=200></img></a>");
//			Reporter.log("<br>");
//			Reporter.log("<br>");
//			// Extent Reports
//			test.log(LogStatus.FAIL, " Verification failed with exception : " + t.getMessage());
//			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
//
//		}
//
//	}

	@AfterSuite
	public void tearDown() {

		if (driver != null) {
			driver.quit();
		}

		log.debug("test execution completed !!!");
	}
}

