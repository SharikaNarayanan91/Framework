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
import testcases.Recruitment;

public class RecruitmentPage {
	private static Logger logger = LogManager.getLogger(Recruitment.class);
	WebCtrls webCtrls = new WebCtrls();

	WebDriver ldriver;

	public RecruitmentPage(WebDriver rdriver) {
		ldriver = rdriver;
		PageFactory.initElements(rdriver, this);
	}

	/**
	 * Click on the Vacancies tab
	 */
	public void clickVacanciesTab() {
		webCtrls.wait(3);
		webCtrls.buttonClick(tabVacancies);
	}

	/**
	 * Click on the Candidates tab
	 */
	public void clickCandidatesTab() {
		webCtrls.wait(3);
		webCtrls.buttonClick(tabCandidates);
	}

	/**
	 * Search a vacancy by vacancy name
	 * @param vacancy
	 */
	public void searchVacancyByVacancyName(String vacancy) {
		webCtrls.wait(2);
		webCtrls.selectFromDropdown(ddlVacancy, vacancy);
		webCtrls.buttonClick(btnSearch);
	}

	/**
	 * Search a vacancy
	 * @param jobTitle
	 * @param vacancy
	 * @param hiringManager
	 * @param status
	 */
	public void searchVacancy(String jobTitle, String vacancy, String hiringManager, String status) {
		webCtrls.wait(2);
		webCtrls.selectFromDropdown(ddlJobTitle, jobTitle);
		webCtrls.selectFromDropdown(ddlVacancy, vacancy);
		webCtrls.selectFromDropdown(ddlHiringManager, hiringManager);
		webCtrls.selectFromDropdown(ddlStatus, status);
		webCtrls.buttonClick(btnSearch);
	}

	/**
	 * Add vacancy
	 * 
	 * @param vacancyName
	 * @param jobTitle
	 * @param description
	 * @param hiringManager
	 * @param numOfPositions
	 */
	public void addVacancy(String vacancyName, String jobTitle, String description, String hiringManager,
			String numOfPositions) {
		webCtrls.buttonClick(btnAdd);
		webCtrls.setData(txtVacancyName, vacancyName);
		webCtrls.selectFromDropdown(ddlJobTitle, jobTitle);
		webCtrls.setData(txtDescription, description);
		webCtrls.selectFromAutosuggestiveDropdown(txtHiringManager, hiringManager);
		webCtrls.setData(txtNumOfPositions, numOfPositions);
		webCtrls.scrollToElement(btnSave);
		webCtrls.buttonClick(btnSave);
	}

	/**
	 * Verify the records in the Vacancy table
	 * 
	 * @param requiredColumnName
	 * @param expectedRecord
	 */
	public void verifyVacancyTableRecord(String requiredColumnName, String expectedRecord) {

		int index = 0;
		switch (requiredColumnName) {
		case "Vacancy":
			index = 3;
			break;
		case "JobTitle":
			index = 4;
			break;
		case "HiringManager":
			index = 5;
			break;
		case "Status":
			index = 6;
			break;
		}

		String actualRecord = webCtrls.getText(eleTableRecord(index));
		Assert.assertEquals(actualRecord, expectedRecord,
				"The " + requiredColumnName + " displayed of the selected user is not as expected");
		logger.info("The " + requiredColumnName + " displayed of the selected user is as expected : " + expectedRecord);
		webCtrls.addLog("Pass",
				"The " + requiredColumnName + " displayed of the selected user is as expected : " + expectedRecord);
	}

	/**
	 * Delete a vacancy
	 * 
	 * @param vacancy
	 */
	public void deleteVacancy(String vacancy) {
		webCtrls.wait(2);
		WebElement deleteVacancyElement = webCtrls.Ctrl(By.xpath("//div[text()='" + vacancy
				+ "']//parent::div//following-sibling::div[@role='cell']//i[@class='oxd-icon bi-trash']"));
		webCtrls.buttonClick(deleteVacancyElement);
		webCtrls.wait(2);
		webCtrls.buttonClick(btnDlgBoxYes);
	}

	/**
	 * Add a candidate
	 * 
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 * @param vacancy
	 * @param email
	 * @param contactNumber
	 * @param keywords
	 * @param notes
	 */
	public void addCandidate(String firstName, String middleName, String lastName, String vacancy, String email,
			String contactNumber,String resume, String keywords, String notes) {
		webCtrls.buttonClick(btnAdd);
		webCtrls.wait(2);
		webCtrls.setData(txtFirstName, firstName);
		webCtrls.setData(txtMiddleName, middleName);
		webCtrls.setData(txtLastName, lastName);
		webCtrls.selectFromDropdown(ddlVacancy, vacancy);
		webCtrls.setData(txtEmail, email);
		webCtrls.setData(txtContactNumber, contactNumber);
		if(resume.length()!=0) {
			webCtrls.uploadFile(txtResume, resume);
		}
		webCtrls.setData(txtKeywords, keywords);
		webCtrls.setData(txtNotes, notes);
		webCtrls.scrollToElement(btnSave);
		webCtrls.buttonClick(btnSave);
	}

	/**
	 * Search a candidate by Candidate name and vacancy
	 * 
	 * @param candidateName
	 * @param jobTitle
	 */
	public void searchCandidateByNameAndVacancy(String candidateName, String vacancyName) {
		webCtrls.wait(2);
		webCtrls.selectFromDropdown(ddlVacancy, vacancyName);
		webCtrls.selectFromAutosuggestiveDropdown(ddlCandidateName, candidateName);
		webCtrls.buttonClick(btnSearch);
	}

	/**
	 * Verify the records in the Candidates table
	 * 
	 * @param requiredColumnName
	 * @param expectedRecord
	 */
	public void verifyCandidatesTableRecord(String requiredColumnName, String expectedRecord) {

		int index = 0;
		switch (requiredColumnName) {
		case "Vacancy":
			index = 3;
			break;
		case "Candidate":
			index = 4;
			break;
		case "HiringManager":
			index = 5;
			break;
		case "DateOfApplication":
			index = 6;
			break;
		case "Status":
			index = 7;
			break;
		}

		String actualRecord = webCtrls.getText(eleTableRecord(index));
		Assert.assertEquals(actualRecord, expectedRecord,
				"The " + requiredColumnName + " displayed of the selected candidate is not as expected");
		logger.info("The " + requiredColumnName + " displayed of the selected candidate is as expected : " + expectedRecord);
		webCtrls.addLog("Pass",
				"The " + requiredColumnName + " displayed of the selected candidate is as expected : " + expectedRecord);
	}

	/**
	 * Delete a candidate
	 * 
	 * @param candidateName
	 */
	public void deleteCandidate(String candidateName) {
		WebElement deleteCandidateElement = webCtrls.Ctrl(By.xpath("//div[text()='" + candidateName
				+ "']//parent::div//following-sibling::div[@role='cell']//i[@class='oxd-icon bi-trash']"));
		webCtrls.buttonClick(deleteCandidateElement);
		webCtrls.wait(2);
		webCtrls.buttonClick(btnDlgBoxYes);
	}
	/**
	 * View a candidate
	 * 
	 * @param candidateName
	 */
	public void viewCandidate(String candidateName) {
		webCtrls.wait(1);
		WebElement viewCandidateElement = webCtrls.Ctrl(By.xpath("//div[text()='" + candidateName
				+ "']//parent::div//following-sibling::div[@role='cell']//i[@class='oxd-icon bi-eye-fill']"));
		webCtrls.buttonClick(viewCandidateElement);
	}
	/**
	 * Short-list a candidate
	 */
	public void shortlistCandidate() {
		webCtrls.wait(1);
		webCtrls.buttonClick(btnShortlist);
		webCtrls.wait(1);
		webCtrls.buttonClick(btnSave);
	}
	
	/**
	 * Verify Status of Candidate
	 * @param expStatus
	 */
	public void verifyCandidateStatus(String expStatus) {
		webCtrls.wait(8);
		webCtrls.scrollToElement(eleStatus);
		String actualFullStatus=webCtrls.getText(eleStatus);
		String actualStatus=actualFullStatus.split(":")[1].trim();
		Assert.assertEquals(actualStatus, expStatus,"Status of Candidate displayed is not as expected");
		logger.info("Status of Candidate displayed is as expected : "+expStatus);
		webCtrls.addLog("Pass","Status of Candidate displayed is as expected : "+expStatus);
	}

	/**
	 * Schedule an Interview for candidate
	 * 
	 * @param interviewTitle
	 * @param interviewer
	 * @param dateOfInterview
	 * @param timeOfInterview
	 * @param notes
	 */
	public void scheduleInterview(String interviewTitle,String interviewer,String dateOfInterview,String timeOfInterview,String notes) {
		webCtrls.wait(1);
		webCtrls.buttonClick(btnScheduleInterview);
		webCtrls.wait(1);
		webCtrls.setData(txtInterviewTitle, interviewTitle);
		webCtrls.selectFromAutosuggestiveDropdown(txtInterviewer, interviewer);
		webCtrls.setData(txtDateOfInterview, dateOfInterview);
		webCtrls.setData(txtTimeOfInterview, timeOfInterview);
		if (notes.length()!=0) {
		webCtrls.scrollToElement(txtNotes);
		webCtrls.setData(txtNotes, notes);
		}
		webCtrls.buttonClick(btnSave);
	}
	
	/**
	 * Get the record from the Candidates table
	 * 
	 * @param requiredColumnName
	 */
	public String getRecordCandidatesTable(String requiredColumnName) {

		int index = 0;
		switch (requiredColumnName) {
		case "Vacancy":
			index = 3;
			break;
		case "Candidate":
			index = 4;
			break;
		case "HiringManager":
			index = 5;
			break;
		case "DateOfApplication":
			index = 6;
			break;
		case "Status":
			index = 7;
			break;
		}

		String requiredRecord = webCtrls.getText(eleTableRecord(index));
		logger.info("The " + requiredColumnName + " of the selected candidate is : " + requiredRecord);
		webCtrls.addLog("INFO",
				"The " + requiredColumnName + " of the selected candidate is : " + requiredRecord);
		return requiredRecord;
	}

	/**
	 * Mark the interview Status
	 * 
	 * @param status :Pass/Fail
	 * @param notes
	 */
	public void markInterviewStatus(String status, String notes) {
		webCtrls.wait(1);
		if (status.contains("Pass")) {
			webCtrls.buttonClick(btnMarkInterviewPassed);
		} else {
			webCtrls.buttonClick(btnMarkInterviewFailed);
		}
		if (notes.length() != 0) {
			webCtrls.scrollToElement(txtNotes);
			webCtrls.setData(txtNotes, notes);
		}
		webCtrls.buttonClick(btnSave);
	}
	/**
	 * Offer the job
	 * @param notes
	 */
	public void offerJob(String notes) {
		webCtrls.wait(1);
		webCtrls.buttonClick(btnOfferJob);
		webCtrls.wait(1);
		if (notes.length()!=0) {
		webCtrls.scrollToElement(txtNotes);
		webCtrls.setData(txtNotes, notes);
		}
		webCtrls.buttonClick(btnSave);
	}
	/**
	 * Hire the candidate
	 * @param notes
	 */
	public void hireCandidate(String notes) {
		webCtrls.wait(1);
		webCtrls.buttonClick(btnHire);
		webCtrls.wait(1);
		if (notes.length()!=0) {
		webCtrls.scrollToElement(txtNotes);
		webCtrls.setData(txtNotes, notes);
		}
		webCtrls.buttonClick(btnSave);
	}
	/**
	 * Reject a Candidate
	 * @param notes
	 */
	public void rejectCandidate(String notes) {
		webCtrls.wait(1);
		webCtrls.buttonClick(btnReject);
		webCtrls.wait(1);
		if (notes.length()!=0) {
		webCtrls.scrollToElement(txtNotes);
		webCtrls.setData(txtNotes, notes);
		}
		webCtrls.buttonClick(btnSave);
	}
	
	// inputs
	@FindBy(xpath = "//label[text()='Vacancy Name']//parent::div//following-sibling::div//input[contains(@class,'oxd-input oxd-input')]")
	WebElement txtVacancyName;
	@FindBy(xpath = "//textarea[contains(@class,'oxd-textarea oxd-textarea')]")
	WebElement txtDescription;
	@FindBy(xpath = "//label[text()='Hiring Manager']//parent::div//following-sibling::div//input[contains(@placeholder,'Type for hints...')]")
	WebElement txtHiringManager;
	@FindBy(xpath = "//label[text()='Number of Positions']//parent::div//following-sibling::div//input[contains(@class,'oxd-input oxd-input')]")
	WebElement txtNumOfPositions;
	@FindBy(name = "firstName")
	WebElement txtFirstName;
	@FindBy(name = "middleName")
	WebElement txtMiddleName;
	@FindBy(name = "lastName")
	WebElement txtLastName;
	@FindBy(xpath = "//label[text()='Email']//parent::div//following-sibling::div//input[contains(@class,'oxd-input oxd-input')]")
	WebElement txtEmail;
	@FindBy(xpath = "//label[text()='Contact Number']//parent::div//following-sibling::div//input[contains(@class,'oxd-input oxd-input')]")
	WebElement txtContactNumber;
	@FindBy(xpath = "//label[text()='Keywords']//parent::div//following-sibling::div//input[contains(@class,'oxd-input oxd-input')]")
	WebElement txtKeywords;
	@FindBy(xpath = "//label[text()='Notes']//parent::div//following-sibling::div//textarea[contains(@class,'oxd-textarea')]")
	WebElement txtNotes;
	@FindBy(xpath = "//label[text()='Date of Application']//parent::div//following-sibling::div//input")
	WebElement txtDateOfApplication;
	@FindBy(xpath = "//label[text()='Interview Title']//parent::div//following-sibling::div//input[contains(@class,'oxd-input oxd-input')]")
	WebElement txtInterviewTitle;
	@FindBy(xpath = "//label[text()='Interviewer']//parent::div//following-sibling::div//input[contains(@placeholder,'Type for hints...')]")
	WebElement txtInterviewer;
	@FindBy(xpath = "//label[text()='Date']//parent::div//following-sibling::div//input[contains(@class,'oxd-input oxd-input')]")
	WebElement txtDateOfInterview;
	@FindBy(xpath = "//label[text()='Time']//parent::div//following-sibling::div//input[contains(@class,'oxd-input oxd-input')]")
	WebElement txtTimeOfInterview;
	@FindBy(xpath="//input[@type='file']")
	WebElement txtResume;
	
	//drop downs
	@FindBy(xpath = "//label[text()='Job Title']//parent::div//following-sibling::div//div[@class='oxd-select-text-input']")
	WebElement ddlJobTitle;
	@FindBy(xpath = "//label[text()='Vacancy']//parent::div//following-sibling::div//div[@class='oxd-select-text-input']")
	WebElement ddlVacancy;
	@FindBy(xpath = "//label[text()='Hiring Manager']//parent::div//following-sibling::div//div[@class='oxd-select-text-input']")
	WebElement ddlHiringManager;
	@FindBy(xpath = "//label[text()='Status']//parent::div//following-sibling::div//div[@class='oxd-select-text-input']")
	WebElement ddlStatus;
	@FindBy(xpath = "//label[text()='Candidate Name']//parent::div//following-sibling::div//input[contains(@placeholder,'Type for hints...')]")
	WebElement ddlCandidateName;
	
	// buttons & tabs
	@FindBy(xpath = "//button[text()=' Search ']")
	WebElement btnSearch;
	@FindBy(xpath = "//a[text()='Vacancies']")
	WebElement tabVacancies;
	@FindBy(xpath = "//a[text()='Candidates']")
	WebElement tabCandidates;
	@FindBy(xpath = "//button[text()=' Add ']")
	WebElement btnAdd;
	@FindBy(xpath = "//button[text()=' Save ']")
	WebElement btnSave;
	@FindBy(xpath = "//div[contains(@class,'dialog-popup')]//button[contains(.,'Yes')]")
	WebElement btnDlgBoxYes;
	@FindBy(xpath = "//button[text()=' Shortlist ']")
	WebElement btnShortlist;
	@FindBy(xpath = "//button[text()=' Schedule Interview ']")
	WebElement btnScheduleInterview;
	@FindBy(xpath = "//button[text()=' Mark Interview Passed ']")
	WebElement btnMarkInterviewPassed;
	@FindBy(xpath = "//button[text()=' Offer Job ']")
	WebElement btnOfferJob;
	@FindBy(xpath = "//button[text()=' Hire ']")
	WebElement btnHire;
	@FindBy(xpath = "//button[text()=' Mark Interview Failed ']")
	WebElement btnMarkInterviewFailed;
	@FindBy(xpath = "//button[text()=' Reject ']")
	WebElement btnReject;

	//elements
	@FindBy(xpath = "//p[contains(@class,'oxd-text oxd-text--p oxd-text--subtitle')]")
	WebElement eleStatus;
	public By eleTableRecord(int index) {
		return By.xpath("(//div[@class='oxd-table-cell oxd-padding-cell' and @role='cell']//div)[" + index + "]");
	}

}
