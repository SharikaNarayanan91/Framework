package testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.jcajce.provider.asymmetric.ec.GMSignatureSpi.sha256WithSM2;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

import Base.BaseTest;
import helper.WebCtrls;
import pages.AdminPage;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PIMPage;
import pages.TimePage;
import utils.ExcelDataSourceInfo;
import utils.ExcelHelper;
import utils.ReadConfig;

@Listeners(utils.ListenerClass.class)
public class Time extends BaseTest {
	private static Logger logger = LogManager.getLogger(Time.class);

	@DataProvider(name = "Time")
	public Object[][] createData(Method method) throws IOException {
		ExcelDataSourceInfo info = method.getAnnotation(ExcelDataSourceInfo.class);
		String testName = info.TestName();
		Object[][] retObjArr = ExcelHelper.getMapArray(this.getClass().getSimpleName(), testName);
		return retObjArr;
	}

	@ExcelDataSourceInfo(TestName = "TC01_Time_CreateTimesheet")
	@Test(enabled = true, priority = 1, dataProvider = "Time")
	public void TC01_Time_CreateTimesheet(Map<Object, Object> map) throws IOException {

		ReadConfig readConfig = new ReadConfig();
		LoginPage loginPage = new LoginPage(driver);
		WebCtrls webCtrls = new WebCtrls();
		AdminPage adminPage = new AdminPage(driver);
		PIMPage pimPage = new PIMPage(driver);
		DashboardPage dashboardPage = new DashboardPage(driver);
		TimePage timePage = new TimePage(driver);

		driver.get(readConfig.readPropertyFile("baseURL"));

		// Decrypt the encrypted password
		String password = webCtrls.decryptString(readConfig.readPropertyFile("Password"));

		// Login with valid credentials
		loginPage.login(readConfig.readPropertyFile("Username"), password);

		// Verify the Login
		loginPage.verifyLogin();
		
		// Select PIM tab
		dashboardPage.clickPIM();
		
		//Create Employee without Login details
		String employeeId=pimPage.createEmployee((String) map.get("EmployeeFirstName"), (String) map.get("EmployeeMiddleName"), (String) map.get("EmployeeLastName"));
		map.put("EmployeeId",employeeId);
		
		dashboardPage.clickPIM();
		
		String employeeFullName=(String) map.get("EmployeeFirstName")+ " "+(String) map.get("EmployeeMiddleName")+" "+(String) map.get("EmployeeLastName");
		map.put("EmployeeFullName", employeeFullName);
		
		//Clock on the Time menu
		dashboardPage.clickTime();
		
		//View the timesheet
		timePage.viewEmployeeTimesheet(employeeFullName);
		
		//create a timesheet
		String[] workHours= {(String) map.get("MonHours"),(String) map.get("TueHours"),(String) map.get("WedHours"),(String) map.get("ThrsHours"),(String) map.get("FriHours")};
		String[] projects= {(String)map.get("Project")};
		String[] activities= {(String)map.get("Activity")};
		int rows=Integer.parseInt((String)map.get("NumOfRows"));
		timePage.createTimesheet(rows,projects, activities, workHours);
		timePage.verifySavedTimesheet(projects, activities);
		timePage.verifyTimesheetStatus((String) map.get("TimesheetStatus"));
	}
	@ExcelDataSourceInfo(TestName = "TC02_Time_CreateTimesheetWithMultipleProjects")
	@Test(enabled = true, priority = 2, dataProvider = "Time")
	public void TC02_Time_CreateTimesheetWithMultipleProjects(Map<Object, Object> map) throws IOException {

		ReadConfig readConfig = new ReadConfig();
		LoginPage loginPage = new LoginPage(driver);
		WebCtrls webCtrls = new WebCtrls();
		AdminPage adminPage = new AdminPage(driver);
		PIMPage pimPage = new PIMPage(driver);
		DashboardPage dashboardPage = new DashboardPage(driver);
		TimePage timePage = new TimePage(driver);

		driver.get(readConfig.readPropertyFile("baseURL"));

		// Decrypt the encrypted password
		String password = webCtrls.decryptString(readConfig.readPropertyFile("Password"));

		// Login with valid credentials
		loginPage.login(readConfig.readPropertyFile("Username"), password);

		// Verify the Login
		loginPage.verifyLogin();
		
		// Select PIM tab
		dashboardPage.clickPIM();
		
		//Create Employee without Login details
		String employeeId=pimPage.createEmployee((String) map.get("EmployeeFirstName"), (String) map.get("EmployeeMiddleName"), (String) map.get("EmployeeLastName"));
		map.put("EmployeeId",employeeId);
		
		dashboardPage.clickPIM();
		
		String employeeFullName=(String) map.get("EmployeeFirstName")+ " "+(String) map.get("EmployeeMiddleName")+" "+(String) map.get("EmployeeLastName");
		map.put("EmployeeFullName", employeeFullName);
		
		//Clock on the Time menu
		dashboardPage.clickTime();
		
		//View the timesheet
		timePage.viewEmployeeTimesheet(employeeFullName);
		
		//create a timesheet
		String[] projects= {(String)map.get("Project1"),(String)map.get("Project2")};
		String[] activities= {(String)map.get("Activity1"),(String)map.get("Activity2")};
		String[] workHours= {(String) map.get("MonHours"),(String) map.get("TueHours"),(String) map.get("WedHours"),(String) map.get("ThrsHours"),(String) map.get("FriHours")};
		int rows=Integer.parseInt((String)map.get("NumOfRows"));
		timePage.createTimesheet(rows,projects,activities, workHours);
		timePage.verifySavedTimesheet(projects, activities);
		timePage.verifyTimesheetStatus((String) map.get("TimesheetStatus"));
	}
	@ExcelDataSourceInfo(TestName = "TC03_Time_SubmitAndApproveTimesheet")
	@Test(enabled = true, priority = 3, dataProvider = "Time")
	public void TC03_Time_SubmitAndApproveTimesheet(Map<Object, Object> map) throws IOException {

		ReadConfig readConfig = new ReadConfig();
		LoginPage loginPage = new LoginPage(driver);
		WebCtrls webCtrls = new WebCtrls();
		AdminPage adminPage = new AdminPage(driver);
		PIMPage pimPage = new PIMPage(driver);
		DashboardPage dashboardPage = new DashboardPage(driver);
		TimePage timePage = new TimePage(driver);

		driver.get(readConfig.readPropertyFile("baseURL"));

		// Decrypt the encrypted password
		String password = webCtrls.decryptString(readConfig.readPropertyFile("Password"));

		// Login with valid credentials
		loginPage.login(readConfig.readPropertyFile("Username"), password);

		// Verify the Login
		loginPage.verifyLogin();
		
		// Select PIM tab
		dashboardPage.clickPIM();
		
		//Create Employee without Login details
		String employeeId=pimPage.createEmployee((String) map.get("EmployeeFirstName"), (String) map.get("EmployeeMiddleName"), (String) map.get("EmployeeLastName"));
		map.put("EmployeeId",employeeId);
		
		dashboardPage.clickPIM();
		
		String employeeFullName=(String) map.get("EmployeeFirstName")+ " "+(String) map.get("EmployeeMiddleName")+" "+(String) map.get("EmployeeLastName");
		map.put("EmployeeFullName", employeeFullName);
		
		//Clock on the Time menu
		dashboardPage.clickTime();
		
		//View the timesheet
		timePage.viewEmployeeTimesheet(employeeFullName);
		
		//create a timesheet
		String[] projects= {(String)map.get("Project")};
		String[] activities= {(String)map.get("Activity")};
		String[] workHours= {(String) map.get("MonHours"),(String) map.get("TueHours"),(String) map.get("WedHours"),(String) map.get("ThrsHours"),(String) map.get("FriHours")};
		int rows=Integer.parseInt((String)map.get("NumOfRows"));
		timePage.createTimesheet(rows,projects,activities, workHours);
		timePage.verifySavedTimesheet(projects, activities);
		timePage.verifyTimesheetStatus((String) map.get("TimesheetStatus1"));
		timePage.submitTimesheet();
		timePage.verifyTimesheetStatus((String) map.get("TimesheetStatus2"));
		timePage.approveTimesheet((String)map.get("ApprovalComment"));
		timePage.verifyTimesheetStatus((String) map.get("TimesheetStatus3"));

		
		
	}
}
