package Base;

import java.io.File;
import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;
import jdk.jfr.internal.Logger;

public class BaseTest {
	public static WebDriver driver;
	static ExtentReports extent;
	public ExtentTest logger;

	@BeforeMethod
	@Parameters("browser")
	public void initialize(String browser,Method testMethod ) {
		extent=new ExtentReports();
		logger=extent.createTest(testMethod.getName());

		if(browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			System.out.println("Chrome driver is launched");
		}
		else if(browser.equalsIgnoreCase("edge")) {
			WebDriverManager.chromedriver().setup();
			driver = new EdgeDriver();
			System.out.println("Edge driver is launched");
		}
		driver.manage().window().maximize();
	}  

	@AfterMethod
	public void afterMethod(ITestResult result){

		if(result.getStatus()==ITestResult.FAILURE) {
			logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" - Test Case Failed" , ExtentColor.RED));
			logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable()+" - Test Case Failed" , ExtentColor.RED));
		}else if (result.getStatus()==ITestResult.SKIP) {
			logger.log(Status.SKIP, MarkupHelper.createLabel(result.getThrowable()+" - Test Case Skipped" , ExtentColor.ORANGE));
		}else if (result.getStatus()==ITestResult.SUCCESS) {
			logger.log(Status.PASS, MarkupHelper.createLabel(result.getThrowable()+" - Test Case Passed" , ExtentColor.GREEN));
		}
		driver.quit();
		extent.flush();
	}	
}