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

	/**
	 * Search for a user with user name and user role
	 * @param userName
	 * @param userRole
	 */
	public void searchUser(String userName, String userRole) {
		if (userName.length() > 1) {
			webCtrls.setData(txtUsername, userName);
				}
		if (userRole.length() > 1) {
			webCtrls.selectFromDropdown(ddlUserRole, userRole);
			}
		webCtrls.buttonClick(btnSearch);
	}

	/**
	 * Verify the total number of users displayed
	 * @param expectedCount
	 */
	public void verifyUserCount(String expectedCount) {
		Assert.assertTrue(webCtrls.isDisplayed(eleRecordsFound(expectedCount)),
				"Number of records for selected user is not as expected");
		logger.info("Number of records for selected user found as expected : " + expectedCount);
		webCtrls.addLog("Pass","Number of records for selected user found as expected : " + expectedCount);
	}

	/**
	 * Verify the records in the table
	 * @param requiredColumnName
	 * @param expectedRecord
	 */
	public void verifyTableRecord(String requiredColumnName,String expectedRecord) {
	
		int index = 0;
		switch(requiredColumnName) {
		case "UserName":
			index=3;
			break;
		case "UserRole":
			index=4;
			break;
		case "EmployeeName":
			index=5;
			break;
		case "Status":
			index=6;
			break;			
		}
			
		String actualRecord=webCtrls.getText(eleTableRecord(index));
		Assert.assertEquals(actualRecord,expectedRecord,
				"The "+requiredColumnName+" displayed of the selected user is not as expected");
		logger.info("The "+requiredColumnName+" displayed of the selected user is as expected : " + expectedRecord);
		webCtrls.addLog("Pass","The "+requiredColumnName+" displayed of the selected user is as expected : " + expectedRecord);
	}
	
	/**
	 * Create a system user
	 * @param userRole
	 * @param employeeName
	 * @param status
	 * @param username
	 * @param password
	 */
	public void createSystemUser(String userRole,String employeeName,String status,String username,String password) {
		webCtrls.buttonClick(btnAdd);
		webCtrls.selectFromDropdown(ddlUserRole, userRole);
		webCtrls.selectFromAutosuggestiveDropdown(txtEmployeeName, employeeName);
		webCtrls.selectFromDropdown(ddlStatus, status);
		webCtrls.setData(txtUsername, username);
		webCtrls.setEncryptedData(txtPassword, password);
		webCtrls.setEncryptedData(txtConfirmPassword, password);
		webCtrls.buttonClick(btnSave);
	}
	// inputs
	@FindBy(xpath="//label[text()='Username']//parent::div//following-sibling::div//input[contains(@class,'oxd-input oxd-input')]")
	WebElement txtUsername;
	@FindBy(xpath = "//label[text()='User Role']//parent::div//following-sibling::div//div[@class='oxd-select-text-input']")
	WebElement ddlUserRole;
	@FindBy(xpath = "//div[@role='listbox']//span")
	WebElement ddlGeneralList;
	@FindBy(xpath = "//label[text()='Status']//parent::div//following-sibling::div//div[@class='oxd-select-text-input']")
	WebElement ddlStatus;
	@FindBy(xpath="//label[text()='Employee Name']//parent::div//following-sibling::div//input[contains(@placeholder,'Type for hints...')]")
	WebElement txtEmployeeName;
	@FindBy(xpath="//label[text()='Password']//parent::div//following-sibling::div//input[contains(@class,'oxd-input oxd-input')]")
	WebElement txtPassword;
	@FindBy(xpath="//label[text()='Confirm Password']//parent::div//following-sibling::div//input[contains(@class,'oxd-input oxd-input')]")
	WebElement txtConfirmPassword;
	// buttons
	@FindBy(xpath = "//button[text()=' Search ']")
	WebElement btnSearch;
	@FindBy(xpath="//button[text()=' Add ']")
	WebElement btnAdd;
	@FindBy(xpath="//button[text()=' Save ']")
	WebElement btnSave;
	
	public By eleRecordsFound(String count) {
		return By.xpath("//span[text()='(" + count + ") Record Found']");
	}
	public By eleTableRecord(int index) {
		return By.xpath("(//div[@class='oxd-table-cell oxd-padding-cell' and @role='cell']//div)["+index+"]");
	}

}
