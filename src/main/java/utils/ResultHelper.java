package utils;

import java.io.File;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ResultHelper {
	static ExtentReports extent;
	public static ExtentReports getReportObject() {
		String path =System.getProperty("user.dir")+File.separator+"test-output/reports";
		ExtentSparkReporter reporter=new ExtentSparkReporter(path);
		reporter.config().setReportName("Automation Results");
		reporter.config().setDocumentTitle("Test Results");
		extent=new ExtentReports();
		extent.attachReporter(reporter);	
		extent.setSystemInfo("Tester", "Test");
		return extent; 
	}

}
