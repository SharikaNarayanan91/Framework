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
import pages.AdminPage;
import pages.LoginPage;
import utils.ExcelHelper;
import utils.ListenerClass;

@Listeners(utils.ListenerClass.class)
public class Admin extends BaseTest{
	private static Map<String,String>ExcelHelperMap=new HashMap<String,String>();
	private static Logger logger= LogManager.getLogger(Login.class);
	
	@Test(enabled = true,priority = 1)
	public void SearchUser() throws IOException {
		ExcelHelperMap=ExcelHelper.getExcelData(this.getClass().getSimpleName());
		driver.get(ExcelHelperMap.get("URL"));
		
		
		LoginPage loginPage =new LoginPage();
		WebCtrls webCtrls=new WebCtrls();
		AdminPage adminPage=new AdminPage();

		//Login
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
		
		//Select Admin tab
		webCtrls.buttonClick(loginPage.linkMainMenuOptions("Admin"));
		logger.info("Admin tab selected");
		ListenerClass.report.info("Admin tab selected");
		
		//Search for user
		webCtrls.setData(adminPage.txtUsername, ExcelHelperMap.get("Username2"));
		logger.info("Entered Username");
		ListenerClass.report.info("Entered Username");
		webCtrls.buttonClick(adminPage.ddlUserRole);
		webCtrls.selectFromDropdown(adminPage.ddlGeneralList, ExcelHelperMap.get("UserRole"));
		logger.info("Selected User role");
		ListenerClass.report.info("Selected User role");
		webCtrls.buttonClick(adminPage.btnSearch);
		logger.info("Clicked on Search button");
		ListenerClass.report.info("Clicked on Search button");
		webCtrls.scroll();
		
		//Verify the number of records for the user
		Assert.assertTrue(webCtrls.isDisplayed(adminPage.eleRecordsFound(ExcelHelperMap.get("NumberOfUserRecords"))),"Number of records for selected user is not as expected");
		logger.info("Number of records for selected user found as expected : "+ExcelHelperMap.get("NumberOfUserRecords"));
		ListenerClass.report.pass("Number of records for selected user found as expected : "+ ExcelHelperMap.get("NumberOfUserRecords"));
	}
}
