package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import helper.WebCtrls;
import testcases.Login;
import utils.ListenerClass;

public class LoginPage {
	private static Logger logger = LogManager.getLogger(Login.class);
	WebCtrls webCtrls = new WebCtrls();

	WebDriver ldriver;

	public LoginPage(WebDriver rdriver) {
		ldriver = rdriver;
		PageFactory.initElements(rdriver, this);
	}

	// input
	@FindBy(xpath = "//input[@name='username']")
	WebElement txtUsername;

	@FindBy(xpath = "//input[@name='password']")
	WebElement txtPassword;

	// buttons
	@FindBy(xpath = "//button[text()=' Login ']")
	WebElement btnLogin;

	// Elements
	@FindBy(xpath = "//h6[text()='Dashboard']")
	WebElement eleDashboardTitle;

	@FindBy(xpath = "//p[contains(@class,'alert-content-text')]")
	WebElement eleInvalidCredentialsError;
	
	public By linkMainMenuOptions(String MenuOption) {
		return By.xpath("//span[contains(@class,'main-menu') and text()='" + MenuOption + "']");
	}

	public void login(String username, String password) {
		webCtrls.setData(txtUsername, username);
		webCtrls.setData(txtPassword, password);
		logger.info("Entered user credentials");
		ListenerClass.report.info("Entered user credentials");

		webCtrls.buttonClick(btnLogin);
		logger.info("Clicked on Login button");
		ListenerClass.report.info("Clicked on Login button");
	}

	public void verifyLogin() {
		Assert.assertTrue(webCtrls.isDisplayed(eleDashboardTitle), "HRM Login not successfull");
		logger.info("HRM login successfull");
		ListenerClass.report.pass("HRM login successfull");
	}

	public void verifyErrorMessage(String expectedErrorMessage) {
		String ActualErrorMessage = webCtrls.getText(eleInvalidCredentialsError);
		Assert.assertEquals(ActualErrorMessage, expectedErrorMessage, "Error message displayed is not as expected");
		logger.info("Error message displayed as expected : " + ActualErrorMessage);
		ListenerClass.report.pass("Error message displayed as expected : " + ActualErrorMessage);
	}
}