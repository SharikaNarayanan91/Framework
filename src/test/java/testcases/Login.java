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
import pages.LoginPage;
import utils.ExcelDataSourceInfo;
import utils.ExcelHelper;
import utils.ReadConfig;
@Listeners(utils.ListenerClass.class)
public class Login extends BaseTest{
	private static Logger logger= LogManager.getLogger(Login.class);
	
	@DataProvider(name="Login")
	public Object[][]createData(Method method) throws IOException{
		ExcelDataSourceInfo info=method.getAnnotation(ExcelDataSourceInfo.class);
		String testName=info.TestName();
		Object[][] retObjArr=ExcelHelper.getMapArray(this.getClass().getSimpleName(), testName);
		return retObjArr;
	}

	@ExcelDataSourceInfo(TestName = "TC01_Login_ValidCredentials")
	@Test(enabled = true,priority = 1,dataProvider ="Login" )
	public void TC01_Login_ValidCredentials(Map<Object, Object> map) throws IOException {
		ReadConfig readConfid=new ReadConfig();
		LoginPage loginPage=new LoginPage(driver);
		WebCtrls ctrls=new WebCtrls();
		driver.get(readConfid.getApplicationURL());
		
		//Decrypt the encrypted password
		String password=ctrls.decryptString(readConfid.getPassword());
		
		//Login with valid credentials
		loginPage.login(readConfid.getUserName(),password);
		
		//Verify the Login
		loginPage.verifyLogin();
	}

	@ExcelDataSourceInfo(TestName = "TC02_Login_InvalidCredentials")
	@Test(enabled = true,priority = 2,dataProvider ="Login")
	public void TC02_Login_InvalidCredentials(Map<Object, Object> map) throws IOException {
		
		ReadConfig readConfid=new ReadConfig();
		LoginPage loginPage=new LoginPage(driver);
		WebCtrls ctrls=new WebCtrls();

		driver.get(readConfid.getApplicationURL());
		
		//Decrypt the Encrypted password
		String password=ctrls.decryptString((String) map.get("Password"));
		
		//Login with invalid credentials
		loginPage.login( (String) map.get("Username"), password);

		//Verify the error message		
		loginPage.verifyErrorMessage((String) map.get("ErrorMessage"));
	}
	
}
