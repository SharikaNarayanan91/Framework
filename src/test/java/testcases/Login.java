package testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

import Base.BaseTest;
import helper.WebCtrls;
import pages.LoginPage;
import utils.ExcelHelper;
import utils.ListenerClass;
@Listeners(utils.ListenerClass.class)
public class Login extends BaseTest{
	private static Map<String,String>ExcelHelperMap=new HashMap<String,String>();
	private static Logger logger= LogManager.getLogger(Login.class);

	@Test(enabled = true,priority = 1)
	public void HRMLogin_ValidCredentials() throws IOException {
		ExcelHelperMap=ExcelHelper.getExcelData(this.getClass().getSimpleName());
		driver.get(ExcelHelperMap.get("URL"));

		LoginPage loginPage=new LoginPage();
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
	}

	@Test(enabled = true,priority = 2)
	public void HRMLogin_InvalidCredentials() throws IOException {
		ExcelHelperMap=ExcelHelper.getExcelData(this.getClass().getSimpleName());
		driver.get(ExcelHelperMap.get("URL"));

		LoginPage loginPage=new LoginPage();
		WebCtrls webCtrls=new WebCtrls();

		webCtrls.setData(loginPage.txtUsername, ExcelHelperMap.get("InvalidUsername"));
		webCtrls.setData(loginPage.txtPassword, ExcelHelperMap.get("Password"));
		logger.info("Entered user credentials");
		ListenerClass.report.info("Entered user credentials");

		webCtrls.buttonClick(loginPage.btnLogin);
		logger.info("Clicked on Login button");
		ListenerClass.report.info("Clicked on Login button");

		Assert.assertTrue(webCtrls.isDisplayed(loginPage.eleInvalidCredentialsError), "Error message not displayed");
		logger.info("Error Message displayed");
		ListenerClass.report.pass("Error Message displayed");
	}
}
