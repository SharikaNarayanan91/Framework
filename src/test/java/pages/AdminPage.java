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

public class AdminPage {
	private static Logger logger = LogManager.getLogger(Login.class);
	WebCtrls webCtrls = new WebCtrls();

	WebDriver ldriver;

	public AdminPage(WebDriver rdriver) {
		ldriver = rdriver;
		PageFactory.initElements(rdriver, this);
	}

	// inputs
	@FindBy(xpath = "//label[text()='Username']//parent::div//following-sibling::div//input[contains(@class,'oxd-input')]")
	WebElement txtUsername;

	@FindBy(xpath = "//label[text()='User Role']//parent::div//following-sibling::div//div[@class='oxd-select-text-input']")
	WebElement ddlUserRole;

	@FindBy(xpath = "//div[@role='listbox']//div")
	WebElement ddlGeneralList;

	@FindBy(xpath = "//label[text()='Status']//parent::div//following-sibling::div//div[@class='oxd-select-text-input']")
	WebElement ddlStatus;

	// buttons
	@FindBy(xpath = "//button[text()=' Search ']")
	WebElement btnSearch;

	// elements
	@FindBy(xpath = "//button[text()=' //div[text()='Username']//following-sibling::div[text()='Cheeku'] ']")
	WebElement eleUsername;
	@FindBy(xpath = "//div[text()='User Role']//following-sibling::div[text()='Admin']")
	WebElement eleUserRole;

	public By eleRecordsFound(String count) {
		return By.xpath("//span[text()='(" + count + ") Record Found']");
	}

	public void searchUser(String userName, String userRole) {
		if (userName.length() > 1) {
			webCtrls.setData(txtUsername, userName);
			logger.info("Entered Username : " + userName);
			ListenerClass.report.info("Entered Username : " + userName);
		}
		if (userRole.length() > 1) {
			webCtrls.buttonClick(ddlUserRole);
			webCtrls.selectFromDropdown(ddlGeneralList, userRole);
			logger.info("Selected User role : " + userRole);
			ListenerClass.report.info("Selected User role : " + userRole);
		}
		webCtrls.buttonClick(btnSearch);
		logger.info("Clicked on Search button");
		ListenerClass.report.info("Clicked on Search button");
	}

	public void verifyUserCount(String expectedCount) {
		Assert.assertTrue(webCtrls.isDisplayed(eleRecordsFound(expectedCount)),
				"Number of records for selected user is not as expected");
		logger.info("Number of records for selected user found as expected : " + expectedCount);
		ListenerClass.report.pass("Number of records for selected user found as expected : " + expectedCount);
	}
}
