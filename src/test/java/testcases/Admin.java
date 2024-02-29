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
import pages.DashboardPage;
import pages.LoginPage;
import utils.ExcelDataSourceInfo;
import utils.ExcelHelper;
import utils.ListenerClass;
import utils.ReadConfig;

@Listeners(utils.ListenerClass.class)
public class Admin extends BaseTest {
	private static Logger logger = LogManager.getLogger(Login.class);

	@DataProvider(name = "Admin")
	public Object[][] createData(Method method) throws IOException {
		ExcelDataSourceInfo info = method.getAnnotation(ExcelDataSourceInfo.class);
		String testName = info.TestName();
		Object[][] retObjArr = ExcelHelper.getMapArray(this.getClass().getSimpleName(), testName);
		return retObjArr;
	}

	@ExcelDataSourceInfo(TestName = "TC01_Admin_SearchUser")
	@Test(enabled = true, priority = 1, dataProvider = "Admin")
	public void TC01_Admin_SearchUser(Map<Object, Object> map) throws IOException {

		ReadConfig readConfig = new ReadConfig();
		LoginPage loginPage = new LoginPage(driver);
		AdminPage adminPage = new AdminPage(driver);
		WebCtrls webCtrls = new WebCtrls();
		DashboardPage dashboardPage =new DashboardPage(driver);		
		driver.get(readConfig.readPropertyFile("baseURL"));

		// Decrypt the Encrypted password
		String password = webCtrls.decryptString(readConfig.readPropertyFile("Password"));

		// Login with valid credentials
		loginPage.login(readConfig.readPropertyFile("Username"), password);
		loginPage.verifyLogin();

		// Select Admin tab
		dashboardPage.clickAdmin();

		// Search for user
		adminPage.searchUser((String) map.get("Username"), (String) map.get("UserRole"));
		webCtrls.scroll();

		// Verify the number of records for the user
		adminPage.verifyUserCount((String) map.get("NumberOfUserRecords"));
		
		//Verify the Empoyee name of the selected user
		adminPage.verifyTableRecord("EmployeeName", (String) map.get("EmployeeName"));
	}
}
