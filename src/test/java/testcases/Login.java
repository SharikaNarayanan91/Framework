package testcases;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

import Base.BaseTest;
import pages.LoginPage;
import utils.ExcelHelper;
import utils.ReadConfig;
@Listeners(utils.ListenerClass.class)
public class Login extends BaseTest{
	private static Map<String,String>ExcelHelperMap=new HashMap<String,String>();
	private static Logger logger= LogManager.getLogger(Login.class);

	@Test(enabled = true,priority = 1)
	public void Login_ValidCredentials() throws IOException {
		ExcelHelperMap=ExcelHelper.getExcelData(this.getClass().getSimpleName());
		ReadConfig readConfid=new ReadConfig();
		LoginPage loginPage=new LoginPage(driver);
		
		driver.get(readConfid.getApplicationURL());

		loginPage.login(readConfid.getUserName(),readConfid.getPassword());
		loginPage.verifyLogin();
	}

	@Test(enabled = true,priority = 2)
	public void Login_InvalidCredentials() throws IOException {
		ExcelHelperMap=ExcelHelper.getExcelData(this.getClass().getSimpleName());
		
		ReadConfig readConfid=new ReadConfig();
		LoginPage loginPage=new LoginPage(driver);

		driver.get(readConfid.getApplicationURL());
		loginPage.login(ExcelHelperMap.get("Username1"), readConfid.getPassword());
		loginPage.verifyErrorMessage(ExcelHelperMap.get("ErrorMessage"));
	}
}
