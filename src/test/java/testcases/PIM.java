package testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BaseTest;
import helper.WebCtrls;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PIMPage;
import utils.ExcelDataSourceInfo;
import utils.ExcelHelper;
import utils.ReadConfig;

@Listeners(utils.ListenerClass.class)
public class PIM extends BaseTest{
	private static Logger logger = LogManager.getLogger(Login.class);

	@DataProvider(name = "PIM")
	public Object[][] createData(Method method) throws IOException {
		ExcelDataSourceInfo info = method.getAnnotation(ExcelDataSourceInfo.class);
		String testName = info.TestName();
		Object[][] retObjArr = ExcelHelper.getMapArray(this.getClass().getSimpleName(), testName);
		return retObjArr;
	}

	@ExcelDataSourceInfo(TestName = "TC01_PIM_CreateEmployeeWithoutCreatingLoginDetails")
	@Test(enabled = true, priority = 1, dataProvider = "PIM")
	public void TC01_PIM_CreateEmployeeWithoutCreatingLoginDetails(Map<Object, Object> map) throws IOException, InterruptedException {

		ReadConfig readConfig = new ReadConfig();
		LoginPage loginPage = new LoginPage(driver);
		PIMPage pimPage = new PIMPage(driver);
		WebCtrls webCtrls = new WebCtrls();
		DashboardPage dashboardPage =new DashboardPage(driver);		
		
		driver.get(readConfig.readPropertyFile("baseURL"));

		// Decrypt the Encrypted password
		String password = webCtrls.decryptString(readConfig.readPropertyFile("Password"));

		// Login with valid credentials
		loginPage.login(readConfig.readPropertyFile("Username"), password);
		loginPage.verifyLogin();

		// Select PIM tab
		dashboardPage.clickPIM();
		
		//Create Employee without Login details
		String employeeFirstName=webCtrls.titleCase(webCtrls.genrateRandomAlphaString(6));
		String employeeMiddleName=webCtrls.titleCase(webCtrls.genrateRandomAlphaString(6));
		map.put("EmployeeFirstName", employeeFirstName);
		map.put("EmployeeMiddleName", employeeMiddleName);
		String employeeId=pimPage.createEmployee((String) map.get("EmployeeFirstName"), (String) map.get("EmployeeMiddleName"), (String) map.get("EmployeeLastName"));
		map.put("EmployeeId",employeeId);
		
		dashboardPage.clickPIM();
		
		// Search for employee
		String FirstAndMiddleName=(String) map.get("EmployeeFirstName")+ " "+(String) map.get("EmployeeMiddleName");
		map.put("EmployeeFirst&LastName", FirstAndMiddleName);
		pimPage.searchEmployeeWithEmployeeName(FirstAndMiddleName);
		webCtrls.scroll();

		//Verify the Empoyee name of the listed employee
		pimPage.verifyTableRecord("FirstAndMiddleName", FirstAndMiddleName);
		
		//Verify the Employee last name of the listed employee
		pimPage.verifyTableRecord("LastName", (String) map.get("EmployeeLastName"));
		map.put("EmployeeLastName", (String) map.get("EmployeeLastName"));
	
		//Verify the Empoyee Id of the listed employee
		pimPage.verifyTableRecord("Id", employeeId);
	}
	
	@ExcelDataSourceInfo(TestName = "TC02_PIM_SearchEmployeeWithEmployeeId")
	@Test(enabled = true, priority = 2, dataProvider = "PIM")
	public void TC02_PIM_SearchEmployeeWithEmployeeId(Map<Object, Object> map) throws IOException, InterruptedException {

		ReadConfig readConfig = new ReadConfig();
		LoginPage loginPage = new LoginPage(driver);
		PIMPage pimPage = new PIMPage(driver);
		WebCtrls webCtrls = new WebCtrls();
		DashboardPage dashboardPage =new DashboardPage(driver);		
		
		driver.get(readConfig.readPropertyFile("baseURL"));

		// Decrypt the Encrypted password
		String password = webCtrls.decryptString(readConfig.readPropertyFile("Password"));

		// Login with valid credentials
		loginPage.login(readConfig.readPropertyFile("Username"), password);
		loginPage.verifyLogin();

		// Select PIM tab
		dashboardPage.clickPIM();
	
		// Search for employee
		String employeeFirstName=webCtrls.titleCase(webCtrls.genrateRandomAlphaString(6));
		String employeeMiddleName=webCtrls.titleCase(webCtrls.genrateRandomAlphaString(6));
		String firstAndMiddleName=employeeFirstName+ " "+employeeMiddleName;
		String employeeId=pimPage.createEmployee(employeeFirstName,employeeMiddleName , (String) map.get("EmployeeLastName"));
		dashboardPage.clickPIM();
		pimPage.searchEmployeeWithEmployeeId(employeeId);
		webCtrls.scroll();

		//Verify the Empoyee name of the listed employee
		pimPage.verifyTableRecord("FirstAndMiddleName", firstAndMiddleName);
		
		//Verify the Employee last name of the listed employee
		pimPage.verifyTableRecord("LastName", (String) map.get("EmployeeLastName"));
	
		//Verify the Empoyee Id of the listed employee
		pimPage.verifyTableRecord("Id",employeeId);
		
	}
}
