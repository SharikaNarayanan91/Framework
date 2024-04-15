package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import helper.WebCtrls;
import testcases.Login;


public class LoginPage {
	private static Logger logger = LogManager.getLogger(Login.class);
	WebCtrls webCtrls = new WebCtrls();

	WebDriver ldriver;

	public LoginPage(WebDriver rdriver) {
		ldriver = rdriver;
		PageFactory.initElements(rdriver, this);
	}
	DashboardPage dashboardPage=new DashboardPage(ldriver);
	
	/**
	 * Login to the site
	 * 
	 * @param userName
	 * @param password
	 */
	public void login(String userName, String password) {
		webCtrls.setData(txtUsername, userName);
		webCtrls.setEncryptedData(txtPassword, password);
		webCtrls.buttonClick(btnLogin);
	}

	/**
	 * Verify login
	 */
	public void verifyLogin() {
		Assert.assertTrue(webCtrls.isDisplayed(eleDashboardTitle), "HRM Login not successfull");
		logger.info("HRM login successfull");
		webCtrls.addLog("Pass","HRM login successfull");
	}

	/**
	 * Verify the Error message
	 * @param expectedErrorMessage
	 */	
	public void verifyErrorMessage(String expectedErrorMessage) {
		String ActualErrorMessage = webCtrls.getText(eleInvalidCredentialsError);
		Assert.assertEquals(ActualErrorMessage, expectedErrorMessage, "Error message displayed is not as expected");
		logger.info("Error message displayed as expected : " + ActualErrorMessage);
		webCtrls.addLog("Pass","Error message displayed as expected : " + ActualErrorMessage);
	}
	
	//inputs
	@FindBy(name="username")
	WebElement txtUsername;
	@FindBy(name="password")
	WebElement txtPassword;
	
	// buttons
	@FindBy(css="[type=submit]")
	WebElement btnLogin;
	
	//elements
	@FindBy(xpath = "//p[contains(@class,'alert-content-text')]")
	WebElement eleInvalidCredentialsError;
	@FindBy(xpath = "//h6[text()='Dashboard']")
	WebElement eleDashboardTitle;
}