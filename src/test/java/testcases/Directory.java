package testcases;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BaseTest;
import helper.WebCtrls;
import pages.DirectoryPage;
import pages.LoginPage;
import utils.ExcelHelper;
import utils.ListenerClass;
@Listeners(utils.ListenerClass.class)
public class Directory extends BaseTest {
	private static Map<String,String>ExcelHelperMap=new HashMap<String,String>();
	private static Logger logger= LogManager.getLogger(Login.class);
	@Test
	public void SearchEmployee() throws IOException {
		ExcelHelperMap=ExcelHelper.getExcelData(this.getClass().getSimpleName());
		driver.get(ExcelHelperMap.get("URL"));
		
		LoginPage loginPage =new LoginPage();
		DirectoryPage directoryPage=new DirectoryPage();
		WebCtrls webCtrls=new WebCtrls();
		
		webCtrls.setData(loginPage.txtUsername, ExcelHelperMap.get("Username"));
		webCtrls.setData(loginPage.txtPassword, ExcelHelperMap.get("Password"));
		logger.info("Entered user credentials");
		ListenerClass.report.info("Entered user credentials");
		
		webCtrls.buttonClick(loginPage.btnLogin);
		logger.info("Clicked on Login button");
		ListenerClass.report.info("Clicked on Login button");
		
		Assert.assertTrue(webCtrls.isDisplayed(loginPage.eleDashboardTitle), "HRM Login not successfull");
		logger.info("HRM login successfull");
		ListenerClass.report.pass("HRM login successfull");	
		
		webCtrls.buttonClick(loginPage.linkMainMenuOptions("Directory"));
		logger.info("Directory tab selected");
		ListenerClass.report.info("Directory tab selected");
		
		webCtrls.setData(directoryPage.txtEmployeeName, ExcelHelperMap.get("EmployeeName"));
		webCtrls.buttonClick(directoryPage.txtEmployeeName);
		webCtrls.selectFromDropdown(directoryPage.ddlGeneralList, ExcelHelperMap.get("EmployeeName"));
		logger.info("Employee name selected");
		ListenerClass.report.info("Employee name selected");
		
		webCtrls.autoSuggestiveDropdown(directoryPage.ddlJobeTitle, ExcelHelperMap.get("JobTitle"));
		logger.info("Job Title selected");
		ListenerClass.report.info("Job Title selected");
		
		webCtrls.autoSuggestiveDropdown(directoryPage.ddlLocation, ExcelHelperMap.get("Location"));
		logger.info("Location selected");
		ListenerClass.report.info("Location selected");
		
		webCtrls.buttonClick(directoryPage.btnSearch);
		logger.info("Search button clicked");
		ListenerClass.report.info("Search button selected");
		
		Assert.assertTrue(webCtrls.isDisplayed(directoryPage.eleEmployeeNameInDirectoryCard(ExcelHelperMap.get("EmployeeFullName"))), "Employee name is not as selected");
		logger.info("Employee name displayed successfully");
		ListenerClass.report.pass("Employee name displayed successfully");
		
		Assert.assertTrue(webCtrls.isDisplayed(directoryPage.eleEmployeeJobTitleInDirectoryCard(ExcelHelperMap.get("JobTitle"))), "Employee Job Title is not as selected");
		logger.info("Job title displayed successfully");
		ListenerClass.report.pass("Job title displayed successfully");
		
		Assert.assertTrue(webCtrls.isDisplayed(directoryPage.eleEmployeeLocationInDirectoryCard(ExcelHelperMap.get("Location"))), "Employee Location is not as selected");
		logger.info("Location displayed successfully");
		ListenerClass.report.pass("Location displayed successfully");
		
	}
}
