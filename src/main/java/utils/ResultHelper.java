package utils;

import java.io.File;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ResultHelper {
	static ExtentReports extent;
	//Create an instance of ExtenReports
	public static ExtentReports getReportObject() {
		String path =System.getProperty("user.dir")+File.separator+"test-output/reports";
		ExtentSparkReporter reporter=new ExtentSparkReporter(path);
		reporter.config().setReportName("Automation Results");
		reporter.config().setDocumentTitle("Automation Test Results");
		extent=new ExtentReports();
		extent.attachReporter(reporter);	
		extent.setSystemInfo("Tester", "Test");
		return extent; 
	}
}
