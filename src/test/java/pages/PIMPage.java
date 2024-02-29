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

public class PIMPage {
	private static Logger logger = LogManager.getLogger(Login.class);
	WebCtrls webCtrls = new WebCtrls();

	WebDriver ldriver;

	public PIMPage(WebDriver rdriver) {
		ldriver = rdriver;
		PageFactory.initElements(rdriver, this);
	}
	
	@FindBy(xpath="//label[text()='Employee Name']//parent::div//following-sibling::div//input")
	WebElement txtEmployeeName;
	@FindBy(css = "[type=submit]")
	WebElement submit;
	@FindBy(css = "[type=reset]")
	WebElement reset;
	@FindBy(xpath="//button[text()=' Add ']")
	WebElement addButton;
	@FindBy(xpath="oxd-text oxd-text--h6 orangehrm-main-title")
	WebElement addEmployeeTitle;
	@FindBy(name="firstName")
	WebElement txtFirstName;
	@FindBy(name="middleName")
	WebElement txtMiddleName;	
	@FindBy(name="lastName")
	WebElement txtLastName;
	@FindBy(xpath="//label[text()='Employee Id']//parent::div//following-sibling::div//input[contains(@class,'oxd-input oxd-input')]")
	WebElement txtEmpId;
	public By eleTableRecord(int index) {
		return By.xpath("(//div[@class='oxd-table-cell oxd-padding-cell' and @role='cell']//div)["+index+"]");
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
}
