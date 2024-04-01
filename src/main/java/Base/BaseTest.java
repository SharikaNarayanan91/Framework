package Base;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	public static WebDriver driver;
	static ExtentReports extent;
	public ExtentTest logger;

	//Initialze the driver
	@BeforeMethod
	@Parameters({"browser","headless"})
	public void initialize(String browser, boolean headless,Method testMethod) {
		extent = new ExtentReports();
		logger = extent.createTest(testMethod.getName());
		try {
			if (browser.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				ChromeOptions options = new ChromeOptions();
			    if (headless) {
	                options.addArguments("--headless"); // Set Chrome to run in headless mode
	            }
			    driver = new ChromeDriver(options);
			    if (headless) {
	                System.out.println("Chrome driver is launched in headless mode");
	            } else {
	                System.out.println("Chrome driver is launched");
	            }
			} else if (browser.equalsIgnoreCase("edge")) {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				System.out.println("Edge driver is launched");
			} else {
				throw new IllegalArgumentException("Unsupported browser: " + browser);
			}
			driver.manage().window().maximize();
		} catch (IllegalArgumentException e) {
			System.out.println("IllegalArgumentException occurred: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.log(Status.FAIL, "Failed to initialize driver: " + e.getMessage());
			throw new RuntimeException("Failed to initialize driver", e);
		}
	}

	//Implementation of extent reports
	@AfterMethod
	public void afterMethod(ITestResult result){

		if(result.getStatus()==ITestResult.FAILURE) {
			logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" - Test Case Failed" , ExtentColor.RED));
			logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable()+" - Test Case Failed" , ExtentColor.RED));
		}else if (result.getStatus()==ITestResult.SKIP) {
			logger.log(Status.SKIP, MarkupHelper.createLabel(result.getThrowable()+" - Test Case Skipped" , ExtentColor.ORANGE));
		}else if (result.getStatus()==ITestResult.SUCCESS) {
			logger.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" - Test Case Passed" , ExtentColor.GREEN));
		}
		driver.quit();
		extent.flush();
	}	
}