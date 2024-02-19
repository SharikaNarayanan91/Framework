package testcases;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BaseTest;
import helper.WebCtrls;
import pages.AdminPage;
import pages.LoginPage;
import utils.ExcelHelper;
import utils.ListenerClass;
import utils.ReadConfig;

@Listeners(utils.ListenerClass.class)
public class Admin extends BaseTest{
	private static Map<String,String>ExcelHelperMap=new HashMap<String,String>();
	private static Logger logger= LogManager.getLogger(Login.class);
	
	@Test(enabled = true,priority = 1)
	public void SearchUser() throws IOException {
		ExcelHelperMap=ExcelHelper.getExcelData(this.getClass().getSimpleName());		
		
		ReadConfig readConfid=new ReadConfig();
		LoginPage loginPage =new LoginPage(driver);
		AdminPage adminPage=new AdminPage(driver);
		WebCtrls webCtrls=new WebCtrls();

		driver.get(readConfid.getApplicationURL());

		//Login
		loginPage.login(readConfid.getUserName(), readConfid.getPassword());
		loginPage.verifyLogin();
		
		//Select Admin tab
		webCtrls.buttonClick(loginPage.linkMainMenuOptions("Admin"));
		logger.info("Admin tab selected");
		ListenerClass.report.info("Admin tab selected");
		
		//Search for user
		adminPage.searchUser(ExcelHelperMap.get("Username2"), ExcelHelperMap.get("UserRole"));
		webCtrls.scroll();
		
		//Verify the number of records for the user
		adminPage.verifyUserCount(ExcelHelperMap.get("NumberOfUserRecords"));
		}
}
