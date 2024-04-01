package testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BaseTest;
import helper.WebCtrls;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PIMPage;
import pages.TimePage;
import utils.ExcelDataSourceInfo;
import utils.ExcelHelper;
import utils.ReadConfig;

@Listeners(utils.ListenerClass.class)
public class Dashboard extends BaseTest {

	@DataProvider(name = "Dashboard")
	public Object[][] createData(Method method) throws IOException {
		ExcelDataSourceInfo info = method.getAnnotation(ExcelDataSourceInfo.class);
		String testName = info.TestName();
		Object[][] retObjArr = ExcelHelper.getMapArray(this.getClass().getSimpleName(), testName);
		return retObjArr;
	}

	@ExcelDataSourceInfo(TestName = "TC01_Dashboard_verifyDasboardMainMenus")
	@Test(enabled = true, priority = 1, dataProvider = "Dashboard")
	public void TC01_Dashboard_verifyDasboardMainMenus(Map<Object, Object> map) {

		ReadConfig readConfig = new ReadConfig();
		LoginPage loginPage = new LoginPage(driver);
		WebCtrls webCtrls = new WebCtrls();
		DashboardPage dashboardPage = new DashboardPage(driver);
		driver.get(readConfig.readPropertyFile("baseURL"));

		// Decrypt the Encrypted password
		String password = webCtrls.decryptString(readConfig.readPropertyFile("Password"));

		// Login with valid credentials
		loginPage.login(readConfig.readPropertyFile("Username"), password);
		loginPage.verifyLogin();

		String[] tabNames = { (String) map.get("TabName1"), (String) map.get("TabName2"), (String) map.get("TabName3"),
				(String) map.get("TabName4"), (String) map.get("TabName5"), (String) map.get("TabName6"),
				(String) map.get("TabName7"), (String) map.get("TabName8"), (String) map.get("TabName9"),
				(String) map.get("TabName10"), (String) map.get("TabName11") };

		// Select Admin tab
		dashboardPage.verifyMainMenuTabs(tabNames);
	}
	@ExcelDataSourceInfo(TestName = "TC02_Dashboard_PunchInAndPunchOutFromDasboard")
	@Test(enabled = true, priority = 2, dataProvider = "Dashboard")
	public void TC02_Dashboard_PunchInAndPunchOutFromDasboard(Map<Object, Object> map) {

		ReadConfig readConfig = new ReadConfig();
		LoginPage loginPage = new LoginPage(driver);
		WebCtrls webCtrls = new WebCtrls();
		DashboardPage dashboardPage = new DashboardPage(driver);
		PIMPage pimPage = new PIMPage(driver);
		TimePage timePage=new TimePage(driver);
		driver.get(readConfig.readPropertyFile("baseURL"));

		// Decrypt the Encrypted password
		String password = webCtrls.decryptString(readConfig.readPropertyFile("Password"));

		// Login with valid credentials
		loginPage.login(readConfig.readPropertyFile("Username"), password);
		loginPage.verifyLogin();

		//Create an employee with login details
		dashboardPage.clickPIM();
		
		String employeeFirstName=webCtrls.titleCase(webCtrls.genrateRandomAlphaString(6));
		String employeeMiddleName=webCtrls.titleCase(webCtrls.genrateRandomAlphaString(6));
		String firstAndMiddleName=employeeFirstName+ " "+employeeMiddleName;
		map.put("EmployeeFirstName", employeeFirstName);
		map.put("EmployeeMiddleName", employeeMiddleName);
		map.put("newUserName", firstAndMiddleName);
		String newUserName=employeeFirstName+employeeMiddleName;
		String newEmployeePassword=webCtrls.decryptString((String)map.get("NewEmployeePassword"));
		
		//Create Employee with Login details
		String employeeId=pimPage.createEmployeeWithLoginDetails((String) map.get("EmployeeFirstName"), (String) map.get("EmployeeMiddleName"), (String) map.get("EmployeeLastName"),newUserName, newEmployeePassword);
		map.put("EmployeeId",employeeId);
		dashboardPage.clickPIM();
		
		//Log out form current user
		dashboardPage.logOut();		
		
		// Decrypt the Encrypted password
		String newDisplayUserName = (String) map.get("EmployeeFirstName") + " " + (String) map.get("EmployeeLastName");

		// Login with valid credentials
		loginPage.login(newUserName, newEmployeePassword);
		loginPage.verifyLogin();
		dashboardPage.verifyUserDropdownName(newDisplayUserName);
	
		//PunchIn from dashboard
		dashboardPage.clickAttendanceActionIcon();
		
		//PunchIn		
		String time=timePage.punchIn("In");
		String expPunchInTime=webCtrls.getCurrentDate("yyyy-dd-MM")+" - "+time;
		timePage.verifyPunchInTime(expPunchInTime);
		
		//Verify Punch In in Dashboard page
		dashboardPage.clickDashboard();
		String expAttendenceDetails="Punched In: Today at "+time;
		dashboardPage.verifyAttendanceStateAndDetails("Punched In", expAttendenceDetails);
		
		//Punch Out from dashboard
		dashboardPage.clickAttendanceActionIcon();
		
		//PunchOut from Attendence screen		
		String outTime=timePage.punchOut("Out");
		
		//Verify Punch In in Dashboard page
		dashboardPage.clickDashboard();
		String expAttendenceDetails2="Punched Out: Today at "+outTime;
		dashboardPage.verifyAttendanceStateAndDetails("Punched Out", expAttendenceDetails2);		
	}
}
