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
import pages.PIMPage;
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

	@ExcelDataSourceInfo(TestName = "TC01_Admin_SearchUserByNameAndRole")
	@Test(enabled = true, priority = 1, dataProvider = "Admin")
	public void TC01_Admin_SearchUserByNameAndRole(Map<Object, Object> map) throws IOException {

		ReadConfig readConfig = new ReadConfig();
		LoginPage loginPage = new LoginPage(driver);
		AdminPage adminPage = new AdminPage(driver);
		WebCtrls webCtrls = new WebCtrls();
		DashboardPage dashboardPage = new DashboardPage(driver);
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

		// Verify the Empoyee name of the selected user
		adminPage.verifyTableRecord("EmployeeName", (String) map.get("EmployeeName"));
	}

	@ExcelDataSourceInfo(TestName = "TC02_Admin_CreateSystemUser")
	@Test(enabled = true, priority = 2, dataProvider = "Admin")
	public void TC02_Admin_CreateSystemUser(Map<Object, Object> map) throws IOException {

		ReadConfig readConfig = new ReadConfig();
		LoginPage loginPage = new LoginPage(driver);
		AdminPage adminPage = new AdminPage(driver);
		PIMPage pimPage = new PIMPage(driver);
		WebCtrls webCtrls = new WebCtrls();
		DashboardPage dashboardPage = new DashboardPage(driver);
		driver.get(readConfig.readPropertyFile("baseURL"));

		// Decrypt the Encrypted password
		String password = webCtrls.decryptString(readConfig.readPropertyFile("Password"));

		// Login with valid credentials
		loginPage.login(readConfig.readPropertyFile("Username"), password);
		loginPage.verifyLogin();

		// Create an employee in PIM
		dashboardPage.clickPIM();
		pimPage.createEmployee((String) map.get("EmployeeFirstName"), (String) map.get("EmployeeMiddleName"),
				(String) map.get("EmployeeLastName"));

		// Select Admin tab
		dashboardPage.clickAdmin();

		// Create a user
		String employeeName=(String) map.get("EmployeeFirstName")+" "+(String) map.get("EmployeeMiddleName")+" "+(String) map.get("EmployeeLastName");
		String employeeFirstAndLastName=(String) map.get("EmployeeFirstName")+" "+(String) map.get("EmployeeLastName");
		String username=(String) map.get("EmployeeFirstName")+webCtrls.genrateRandomAlphaString(6);
		String userPassword = webCtrls.decryptString((String) map.get("UserPassword"));
		adminPage.createSystemUser((String) map.get("UserRole"), employeeName,
				(String) map.get("Status"),username, userPassword);

		dashboardPage.clickAdmin();

		// Search for user
		adminPage.searchUser(username, (String) map.get("UserRole"));
		webCtrls.scroll();

		// Verify the Empoyee name of the selected user
		adminPage.verifyTableRecord("EmployeeName", employeeFirstAndLastName);
		adminPage.verifyTableRecord("UserName", username);
		adminPage.verifyTableRecord("UserRole", (String) map.get("UserRole"));
		adminPage.verifyTableRecord("Status", (String) map.get("Status"));
	}
}
