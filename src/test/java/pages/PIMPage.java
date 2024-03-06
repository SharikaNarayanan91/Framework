package pages;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import helper.WebCtrls;
import testcases.Login;

public class PIMPage {
	private static Logger logger = LogManager.getLogger(Login.class);
	WebCtrls webCtrls = new WebCtrls();

	WebDriver ldriver;

	public PIMPage(WebDriver rdriver) {
		ldriver = rdriver;
		PageFactory.initElements(rdriver, this);
	}
	
	/**
	 * Search employee with Employee Name
	 * 
	 * @param employeeName
	 */
	public void searchEmployeeWithEmployeeName(String employeeName) {
		webCtrls.wait(10);
		webCtrls.setData(txtEmployeeName, employeeName);
		webCtrls.buttonClick(submit);
	}
	/**
	 * Search employee with Employee Id
	 * @param employeeId
	 */
	public void searchEmployeeWithEmployeeId(String employeeId)  {
		webCtrls.wait(10);
		webCtrls.setData(txtEmpId, employeeId);
		webCtrls.buttonClick(submit);	
}
	/**
	 * Verify the records in the table
	 * @param requiredColumnName 
	 * @param expectedRecord
	 */
	public void verifyTableRecord(String requiredColumnName,String expectedRecord) {
	
		int index = 0;
		switch(requiredColumnName) {
		case "Id":
			index=3;
			break;
		case "FirstAndMiddleName":
			index=4;
			break;
		case "LastName":
			index=5;
			break;
		case "JobTitle":
			index=6;
			break;		
		case "EmploymentStatus":
			index=7;
			break;
		case "SubUnit":
			index=8;
			break;	
		case "Supervisor":
			index=9;
			break;
		}
			
		String actualRecord=webCtrls.getText(eleTableRecord(index));
		Assert.assertEquals(actualRecord,expectedRecord,
				"The "+requiredColumnName+" displayed for the selected user is not as expected");
		logger.info("The "+requiredColumnName+" displayed for the selected user is as expected : " + expectedRecord);
		webCtrls.addLog("Pass","The "+requiredColumnName+" displayed for the selected user is as expected : " + expectedRecord);
	}
	/**
	 * Create an employee
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 */
	public String createEmployee(String firstName,String middleName,String lastName) {
		webCtrls.wait(5);
		addButton.click();
		webCtrls.setData(txtFirstName, firstName);
		webCtrls.setData(txtMiddleName, middleName);
		webCtrls.setData(txtLastName, lastName);
		String empId=txtEmpId.getAttribute("value");
		webCtrls.buttonClick(submit);
		return empId;
	}

	/**
	 * Create an employee with Login Details
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 * @param userName
	 * @param password
	 */
	public String createEmployeeWithLoginDetails(String firstName,String middleName,String lastName,String userName,String password) {
		webCtrls.wait(5);
		addButton.click();
		webCtrls.setData(txtFirstName, firstName);
		webCtrls.setData(txtMiddleName, middleName);
		webCtrls.setData(txtLastName, lastName);
		webCtrls.javaScriptClick(chkboxCreateLogin);
		webCtrls.setData(txtUsername, userName);
		webCtrls.setEncryptedData(txtPassword, password);
		webCtrls.setEncryptedData(txtConfirmPassword, password);
		String empId=txtEmpId.getAttribute("value");
		webCtrls.javaScriptClick(submit);
		webCtrls.wait(10);
		return empId;
	}
	/**
	 * Delete an employee
	 * @param employeeName
	 */
	public void	deleteEmployee(String employeeName) {
		WebElement deleteIcon=webCtrls.Ctrl(By.xpath("//div[text()='"+employeeName+"']//parent::div//following-sibling::div//i[@class='oxd-icon bi-trash']"));
		webCtrls.buttonClick(deleteIcon);
		webCtrls.wait(2);
		webCtrls.buttonClick(dlgBoxYesButton);
		webCtrls.wait(2);
	}
	/**
	 * Click edit icon against an employee
	 * @param employeeName
	 */
	public void	clickEditIconAgainstEmployee(String employeeName) {
		WebElement editIcon=webCtrls.Ctrl(By.xpath("//div[text()='"+employeeName+"']//parent::div//following-sibling::div//i[@class='oxd-icon bi-pencil-fill']"));
		webCtrls.buttonClick(editIcon);
		webCtrls.wait(2);
	}
	/**
	 * Edit employee name under Personal details 
	 * @param fieldName
	 * @param fieldValue
	 */
	public void editEmployeeNameUnderPersonalDetails(String fieldName,String fieldValue) {	
		WebElement editField=webCtrls.Ctrl(By.xpath("//input[@name='"+fieldName+"']"));
		webCtrls.clearTextByDeleting(editField);
		webCtrls.wait(5);
		webCtrls.setData(editField, fieldValue);
	}
	
	/**
	 * Click Save button on edit details section
	 * */
	public void clickSaveOnEditDetails() {
		webCtrls.scrollToElement(submit);
		webCtrls.javaScriptClick(submit);
		webCtrls.wait(3);
	}
	/**
	 * Verify 'No Records Found' is displayed when search results are not found
	 * */
	public void verifyNoRecords() {
		webCtrls.scroll();
		Assert.assertTrue(webCtrls.isDisplayed(txtNoRecordsFound), "'No Records Found' not displayed for the selected user");
		logger.info("'No Records Found' displayed for the selected user");
		webCtrls.addLog("Pass","'No Records Found' displayed for the selected user");
	}

	@FindBy(xpath="//label[text()='Employee Name']//parent::div//following-sibling::div//input")
 	WebElement txtEmployeeName;
	@FindBy(css = "[type=submit]")
	WebElement submit;
	@FindBy(css = "[type=reset]")
	WebElement reset;
	@FindBy(xpath="//button[text()=' Add ']")
	WebElement addButton;
	@FindBy(name="firstName")
	WebElement txtFirstName;
	@FindBy(name="middleName")
	WebElement txtMiddleName;	
	@FindBy(name="lastName")
	WebElement txtLastName;
	@FindBy(xpath="//label[text()='Employee Id']//parent::div//following-sibling::div//input[contains(@class,'oxd-input oxd-input')]")
	WebElement txtEmpId;
	@FindBy(xpath="//label[text()='Username']//parent::div//following-sibling::div//input[contains(@class,'oxd-input oxd-input')]")
	WebElement txtUsername;
	@FindBy(xpath="//label[text()='Password']//parent::div//following-sibling::div//input[contains(@class,'oxd-input oxd-input')]")
	WebElement txtPassword;
	@FindBy(xpath="//label[text()='Confirm Password']//parent::div//following-sibling::div//input[contains(@class,'oxd-input oxd-input')]")
	WebElement txtConfirmPassword;
	@FindBy(css = "[type=checkbox]")
	WebElement chkboxCreateLogin;
	@FindBy(xpath="//i[@class='oxd-icon bi-trash']")
	WebElement iconDelete;
	@FindBy(xpath = "//div[contains(@class,'dialog-popup')]//button[contains(.,'Yes')]")
	WebElement dlgBoxYesButton;
	@FindBy(xpath = "//span[text()='No Records Found']")
	WebElement txtNoRecordsFound;
	public By eleTableRecord(int index) {
		return By.xpath("(//div[@class='oxd-table-cell oxd-padding-cell' and @role='cell']//div)["+index+"]");
	}
	
}
