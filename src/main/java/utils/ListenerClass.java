package utils;
import java.io.File;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class ListenerClass implements ITestListener {

	private String pathFail;
	private String pathPass;
	private static boolean skip;

	public static ExtentTest report;
	ExtentReports extent=ResultHelper.getReportObject();
	private static Logger logger= LogManager.getLogger();


	ThreadLocal<ExtentTest> extentTest =new ThreadLocal<ExtentTest>();

	String testOutputLocation=System.getProperty("user.dir")+File.separator+"test-output/reports";

	@Override
	public void onTestStart(ITestResult result) {
			
		if(checkIfFolderExists(testOutputLocation))
		{
			if(!skip) {
			File newDirName = new File(testOutputLocation.replace("reports", "") + "/Backup" + GenerateRandomHelper.GenerateRandomStringWithDateTime());
			File dir = new File(testOutputLocation);
			 dir.renameTo(newDirName);
			 skip = true;
			}
		}
		else
		{
			skip = true;
		}
		    
		report= extent.createTest(result.getMethod().getMethodName());
		extentTest.set(report);		
		logger.info("Test started sucessfully");
		report.info("Test Started Successfully");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
			
		pathPass = ScreenshotHelper.TakeScreenShot();
		
		extentTest.get().log(Status.PASS," Test Passed :"+result.getMethod().getMethodName());	
		
		// Comment this if screenshot of pass cases not required		
		extentTest.get().addScreenCaptureFromPath(pathPass, result.getMethod().getMethodName());
		
		if(checkIfFolderExists(testOutputLocation + "/" + result.getMethod().getMethodName()))
		{
			Iterator it = FileUtils.iterateFiles(new File(testOutputLocation + "/" + result.getMethod().getMethodName()), null, false);
	        while(it.hasNext()){
	        	
	        	String FileName = ((File) it.next()).getName();
	        	String dec = FileName.replace(result.getMethod().getMethodName(), "").replace(".jpg", "");
	        	String path = testOutputLocation + "/" + result.getMethod().getMethodName() + "/" + FileName;
	        	extentTest.get().addScreenCaptureFromPath(path, dec);	            
	        }
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {

		pathFail = ScreenshotHelper.TakeScreenShot();

		extentTest.get().log(Status.FAIL, "Test Failed");
		extentTest.get().fail(result.getThrowable());		
		extentTest.get().addScreenCaptureFromPath(pathFail, result.getMethod().getMethodName());

		if(checkIfFolderExists(testOutputLocation + "/" + result.getMethod().getMethodName()))
		{
			Iterator it = FileUtils.iterateFiles(new File(testOutputLocation + "/" + result.getMethod().getMethodName()), null, false);
			while(it.hasNext()){

				String FileName = ((File) it.next()).getName();
				String dec = FileName.replace(result.getMethod().getMethodName(), "").replace(".jpg", "");
				String path = testOutputLocation + "/" + result.getMethod().getMethodName() + "/" + FileName;
				extentTest.get().addScreenCaptureFromPath(path, dec);	            
			}
		}
	
	}

	@Override public void onFinish(ITestContext context) { 
		extent.flush();
	}

	public static boolean checkIfFolderExists(String folderName) {
		boolean found = false;

		try { File file = new File(folderName); if (file.exists() &&
				file.isDirectory()) { found = true; } } 
		catch (Exception e) {
			e.printStackTrace(); }

		return found; }
}
