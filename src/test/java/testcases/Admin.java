package testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BaseTest;
import helper.WebCtrls;
import pages.AdminPage;
import pages.LoginPage;
import utils.ExcelDataSourceInfo;
import utils.ExcelHelper;
import utils.ListenerClass;
import utils.ReadConfig;

@Listeners(utils.ListenerClass.class)
public class Admin extends BaseTest{
	private static Logger logger= LogManager.getLogger(Login.class);
	
	@DataProvider(name="Admin")
	public Object[][]createData(Method method) throws IOException{
		ExcelDataSourceInfo info=method.getAnnotation(ExcelDataSourceInfo.class);
		String testName=info.TestName();
		Object[][] retObjArr=ExcelHelper.getMapArray(this.getClass().getSimpleName(), testName);
		return retObjArr;
	}

	@ExcelDataSourceInfo(TestName = "SearchUser")
	@Test(enabled = true,priority = 1,dataProvider="Admin")
	public void SearchUser(Map<Object, Object> map) throws IOException {

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
		webCtrls.addLog("Admin tab selected");
		
		//Search for user
		adminPage.searchUser((String) map.get("Username"), (String) map.get("UserRole"));
		webCtrls.scroll();
		
		//Verify the number of records for the user
		adminPage.verifyUserCount((String) map.get("NumberOfUserRecords"));
		}
}
