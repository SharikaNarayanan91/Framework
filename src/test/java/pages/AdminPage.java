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
import testcases.Admin;

public class AdminPage {
	private static Logger logger = LogManager.getLogger(Admin.class);
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
		webCtrls.scroll();
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
	
	/**
	 * Navigate to screen under Job Menu
	 * @param option -Pass the name of the screen/option
	 */
	public void selectOptionFromJobMenu(String menuItem) {
		webCtrls.selectFromChevronDropdown(topBarJob, menuItem);	
	}
	
	/**
	 * Create a Job Title
	 * @param jobTitle
	 * @param jobDescription
	 */
	public void createJobTitle(String jobTitle,String jobDescription) {
		webCtrls.buttonClick(btnAdd);
		webCtrls.setData(txtJobTitle, jobTitle);
		webCtrls.setData(txtJobDescription, jobDescription);
		webCtrls.scrollToElement(btnSave);
		webCtrls.buttonClick(btnSave);
		webCtrls.wait(2);
	}
	/**
	 * Verify Job title
	 * @param jobTitle
	 */
	public void verifyJobTitle(String jobTitle) {
		webCtrls.wait(3);
		String jobTitleLocator="//div[@role='cell']//div[text()='"+jobTitle+"']";
		WebElement jobTitleElement=webCtrls.Ctrl(By.xpath(jobTitleLocator));
		Assert.assertTrue(jobTitleElement.isDisplayed(),"Job title '"+jobTitle+"' is not added");
		logger.info("Job title '"+jobTitle+"' is added");
		webCtrls.addLog("Pass","Job title '"+jobTitle+"' is added");
	}
	
	/**
	 * Verify Job description against the job title
	 * @param jobTitle
	 * @param jobDescription
	 */
	public void verifyJobDescription(String jobTitle,String jobDescription) {
		WebElement jobDescriptionElement=webCtrls.Ctrl(By.xpath("//div[text()='"+jobTitle+"']//parent::div//following-sibling::div[@role='cell']//span"));
		String actualJobDescription=webCtrls.getText(jobDescriptionElement);
		Assert.assertEquals(actualJobDescription,jobDescription,"Job description is not as expected");
		logger.info("Job Description '"+jobDescription+"' is added for job title "+jobTitle);
		webCtrls.addLog("Pass","Job Description '"+jobDescription+"' is added for job title "+jobTitle);
	}
	
	/**
	 * Delete the job title
	 * @param jobTitle
	 */
	public void deleteJobTitle(String jobTitle) {
		WebElement deleteJobTitleElement=webCtrls.Ctrl(By.xpath("//div[text()='"+jobTitle+"']//parent::div//following-sibling::div[@role='cell']//i[@class='oxd-icon bi-trash']"));
		webCtrls.buttonClick(deleteJobTitleElement);
		webCtrls.wait(2);
		webCtrls.buttonClick(dlgBoxYesButton);
	}
	
	/**
	 * Create an employment status
	 * @param employmentStatus
	 */
	public void createEmploymentStatus(String employmentStatus) {
		webCtrls.buttonClick(btnAdd);
		webCtrls.setData(txtName, employmentStatus);
		webCtrls.buttonClick(btnSave);
		webCtrls.wait(2);
	}
	
	/**
	 * Verify Employment status
	 * @param jobTitle
	 */
	public void verifyEmploymentStatus(String employmentStatus) {
		webCtrls.wait(3);
		String statusLocator="//div[@role='cell']//div[text()='"+employmentStatus+"']";
		WebElement statusElement=webCtrls.Ctrl(By.xpath(statusLocator));
		Assert.assertTrue(statusElement.isDisplayed(),"Employment status '"+employmentStatus+"' is not added");
		logger.info("Employment status '"+employmentStatus+"' is added");
		webCtrls.addLog("Pass","Employment status '"+employmentStatus+"' is added");
	}	
	/**
	 * Delete the Employment status
	 * @param jobTitle
	 */
	public void deleteEmploymentStatus(String employmentStatus) {
		WebElement deleteEmploymentStatusElement=webCtrls.Ctrl(By.xpath("//div[text()='"+employmentStatus+"']//parent::div//following-sibling::div[@role='cell']//i[@class='oxd-icon bi-trash']"));
		webCtrls.buttonClick(deleteEmploymentStatusElement);
		webCtrls.wait(2);
		webCtrls.buttonClick(dlgBoxYesButton);
	}
	/**
	 * Create a Job Category
	 * @param jobCategory
	 */
	public void createJobCategory(String jobCategory) {
		webCtrls.buttonClick(btnAdd);
		webCtrls.setData(txtName, jobCategory);
		webCtrls.buttonClick(btnSave);
		webCtrls.wait(2);
	}
	
	/**
	 * Verify Job Category
	 * @param jobCategory
	 */
	public void verifyJobCategory(String jobCategory) {
		webCtrls.wait(3);
		String jobCategoryLocator="//div[@role='cell']//div[text()='"+jobCategory+"']";
		WebElement jobCategoryElement=webCtrls.Ctrl(By.xpath(jobCategoryLocator));
		Assert.assertTrue(jobCategoryElement.isDisplayed(),"Job Category '"+jobCategory+"' is not added");
		logger.info("Job Category '"+jobCategory+"' is added");
		webCtrls.addLog("Pass","Job Category '"+jobCategory+"' is added");
	}	
	/**
	 * Delete the Job Category
	 * @param jobCategory
	 */
	public void deleteJobCategory(String jobCategory) {
		WebElement deleteJobCategoryElement=webCtrls.Ctrl(By.xpath("//div[text()='"+jobCategory+"']//parent::div//following-sibling::div[@role='cell']//i[@class='oxd-icon bi-trash']"));
		webCtrls.buttonClick(deleteJobCategoryElement);
		webCtrls.wait(2);
		webCtrls.buttonClick(dlgBoxYesButton);
	}
	
	/**
	 * Create a Work shift
	 * @param shiftName
	 * @param fromTime
	 * @param toTime
	 */
	public String createWorkShift(String shiftName,String fromTime,String toTime) {
		webCtrls.buttonClick(btnAdd);
		webCtrls.setData(txtShiftName, shiftName);
		webCtrls.setData(txtFromTime, fromTime);
		webCtrls.setData(txtToTime, toTime);
		String duration=webCtrls.getText(eleWorkDuration);
		webCtrls.scrollToElement(btnSave);
		webCtrls.buttonClick(btnSave);
		webCtrls.wait(2);
		return duration;
	}
	
	/**
	 * Verify work shift
	 * @param shiftName
	 * @param fromTime
	 * @param toTime
	 * @param duration
	 */
	public void verifyWorkShift(String shiftName,String fromTime,String toTime,String duration) {
		webCtrls.wait(8);
		String shiftNameLocator="//div[@role='cell']//div[text()='"+shiftName+"']";
		WebElement shiftNameElement=webCtrls.Ctrl(By.xpath(shiftNameLocator));
		Assert.assertTrue(shiftNameElement.isDisplayed(),"Work shift '"+shiftName+"' is not added");
		logger.info("Work sift '"+shiftName+"' is added");
		
		WebElement fromTimeElement=webCtrls.Ctrl(By.xpath("(//div[text()='"+shiftName+"']//parent::div//following-sibling::div[@role='cell'])[1]"));
		String actualFromTime=webCtrls.getText(fromTimeElement);
		Assert.assertEquals(actualFromTime,fromTime,"From time against shift '"+shiftName+"' is not added as expected");
		logger.info("From time against shift '"+shiftName+"' is as expected");
		
		WebElement toTimeElement=webCtrls.Ctrl(By.xpath("(//div[text()='"+shiftName+"']//parent::div//following-sibling::div[@role='cell'])[2]"));
		String actualToTime=webCtrls.getText(toTimeElement);
		Assert.assertEquals(actualToTime,toTime,"To time against shift '"+shiftName+"' is not added as expected");
		logger.info("To time against shift '"+shiftName+"' is as expected");

		WebElement durationElement=webCtrls.Ctrl(By.xpath("(//div[text()='"+shiftName+"']//parent::div//following-sibling::div[@role='cell'])[3]"));
		String actualDuration=webCtrls.getText(durationElement);
		Assert.assertEquals(actualDuration,duration,"Duration against shift '"+shiftName+"' is not added as expected");
		logger.info("Duration against shift '"+shiftName+"' is as expected");

		//logger.info("Work sift '"+shiftName+"' is added with From Time : '"+fromTime+"', To time : '"+toTime+"' and Duration : '"+duration+"'");
		webCtrls.addLog("Pass","Work sift '"+shiftName+"' is added with From Time : '"+fromTime+"', To time : '"+toTime+"' and Duration : '"+duration+"'");
	}	
	/**
	 * Delete the work shift
	 * @param shiftName
	 */
	public void deleteWorkShift(String shiftName) {
		WebElement deleteWorkShiftElement=webCtrls.Ctrl(By.xpath("//div[text()='"+shiftName+"']//parent::div//following-sibling::div[@role='cell']//i[@class='oxd-icon bi-trash']"));
		webCtrls.buttonClick(deleteWorkShiftElement);
		webCtrls.wait(2);
		webCtrls.buttonClick(dlgBoxYesButton);
	}
	
	/**
	 * Create a Pay Grade
	 * @param payGrade
	 * @param currency
	 * @param minSalary
	 * @param maxSalary
	 */
	public void createPayGrade(String payGrade,String currency,String minSalary,String maxSalary) {
		webCtrls.buttonClick(btnAdd);
		webCtrls.setData(txtName, payGrade);
		webCtrls.buttonClick(btnSave);
		webCtrls.wait(2);
		webCtrls.buttonClick(btnAdd);
		webCtrls.selectFromDropdown(drpdwnCurrency, currency);
		webCtrls.setData(txtMinSalary, minSalary);
		webCtrls.setData(txtMaxSalary, maxSalary);
		webCtrls.scrollToElement(btnSaveCurrency);
		webCtrls.javaScriptClick(btnSaveCurrency);
		webCtrls.wait(2);
		webCtrls.scrollToElement(btnCancel);
		webCtrls.javaScriptClick(btnCancel);
		webCtrls.wait(2);
	}
	
	/**
	 * Verify Pay Grade
	 * @param payGrade
	 * @param currency
	 */
	public void verifyPayGrade(String payGrade,String currency) {
		String payGradeLocator="//div[@role='cell']//div[text()='"+payGrade+"']";
		WebElement payGradeElement=webCtrls.Ctrl(By.xpath(payGradeLocator));
		Assert.assertTrue(payGradeElement.isDisplayed(),"Pay Grade '"+payGrade+"' is not added");
		
		WebElement currencyElement=webCtrls.Ctrl(By.xpath("//div[text()='"+payGrade+"']//parent::div//following-sibling::div[@role='cell']"));
		String actualCurrency=webCtrls.getText(currencyElement);
		Assert.assertEquals(actualCurrency,currency,"Currency added against the pay grade '"+payGrade+"' is not as expected");
		
		logger.info("Currency added against the pay grade '"+payGrade+"' is as expected : "+currency);
		webCtrls.addLog("Pass","Currency added against the pay grade '"+payGrade+"' is as expected : "+currency);
	}	

	/**
	 * Delet Pay grade
	 * 
	 * @param payGrade
	 */
	public void deletePayGrade(String payGrade) {
		WebElement deletePayGradeElement=webCtrls.Ctrl(By.xpath("//div[text()='"+payGrade+"']//parent::div//following-sibling::div[@role='cell']//i[@class='oxd-icon bi-trash']"));
		webCtrls.buttonClick(deletePayGradeElement);
		webCtrls.wait(2);
		webCtrls.buttonClick(dlgBoxYesButton);
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
	@FindBy(xpath="//span[contains(text(),'Job')]")
	WebElement topBarJob;
	@FindBy(xpath="//label[text()='Job Title']//parent::div//following-sibling::div//input[contains(@class,'oxd-input oxd-input')]")
	WebElement txtJobTitle;
	@FindBy(xpath="//label[text()='Job Description']//parent::div//following-sibling::div//textarea[contains(@class,'oxd-textarea')]")
	WebElement txtJobDescription;
	@FindBy(xpath = "//div[contains(@class,'dialog-popup')]//button[contains(.,'Yes')]")
	WebElement dlgBoxYesButton;	
	@FindBy(xpath="//label[text()='Name']//parent::div//following-sibling::div//input[contains(@class,'oxd-input oxd-input')]")
	WebElement txtName;
	@FindBy(xpath="//label[text()='Shift Name']//parent::div//following-sibling::div//input[contains(@class,'oxd-input oxd-input')]")
	WebElement txtShiftName;
	@FindBy(xpath="//label[text()='From']//parent::div//following-sibling::div//input[contains(@class,'oxd-input oxd-input')]")
	WebElement txtFromTime;
	@FindBy(xpath="//label[text()='To']//parent::div//following-sibling::div//input[contains(@class,'oxd-input oxd-input')]")
	WebElement txtToTime;
	@FindBy(xpath="//label[text()='Duration Per Day']//parent::div//following-sibling::div//p")
	WebElement eleWorkDuration;
	@FindBy(xpath="//label[text()='Currency']//parent::div//following-sibling::div//div[@class='oxd-select-text-input']")
	WebElement drpdwnCurrency;
	@FindBy(xpath="//label[text()='Minimum Salary']//parent::div//following-sibling::div//input[contains(@class,'oxd-input oxd-input')]")
	WebElement txtMinSalary;
	@FindBy(xpath="//label[text()='Maximum Salary']//parent::div//following-sibling::div//input[contains(@class,'oxd-input oxd-input')]")
	WebElement txtMaxSalary;
	@FindBy(xpath="//button[text()=' Cancel ']")
	WebElement btnCancel;
	@FindBy(xpath="//h6[text()='Add Currency']//parent::div//button[text()=' Save ']")
	WebElement btnSaveCurrency;
	
	
	public By eleRecordsFound(String count) {
		return By.xpath("//span[text()='(" + count + ") Record Found']");
	}
	public By eleTableRecord(int index) {
		return By.xpath("(//div[@class='oxd-table-cell oxd-padding-cell' and @role='cell']//div)["+index+"]");
	}

}
