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
import utils.ExcelDataSourceInfo;
import utils.ExcelHelper;
import utils.ReadConfig;

@Listeners(utils.ListenerClass.class)
public class Dashboard extends BaseTest {
	private static Logger logger = LogManager.getLogger(Login.class);

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
}
