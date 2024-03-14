package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.github.dockerjava.api.model.Driver;

import helper.WebCtrls;
import testcases.Login;
import utils.ListenerClass;

public class RecruitmentPage {
	private static Logger logger = LogManager.getLogger(RecruitmentPage.class);
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
	 * Search a vacancy
	 * @param jobTitle
	 * @param vacancy
	 * @param hiringManager
	 * @param status
	 */
	public void searchVacancy(String jobTitle,String vacancy,String hiringManager,String status) {
		webCtrls.wait(2);
		webCtrls.selectFromDropdown(ddlJobTitle, jobTitle);
		webCtrls.selectFromDropdown(ddlVacancy, vacancy);
		webCtrls.selectFromDropdown(ddlHiringManager, hiringManager);
		webCtrls.selectFromDropdown(ddlStatus, status);
		webCtrls.buttonClick(btnSearch);
	}

	/**
	 * Add vacancy
	 * @param vacancyName
	 * @param jobTitle
	 * @param description
	 * @param hiringManager
	 * @param numOfPositions
	 */
	public void addVacancy(String vacancyName,String jobTitle,String description,String hiringManager,String numOfPositions) {
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
	 * Verify the records in the table
	 * @param requiredColumnName
	 * @param expectedRecord
	 */
	public void verifyTableRecord(String requiredColumnName,String expectedRecord) {
	
		int index = 0;
		switch(requiredColumnName) {
		case "Vacancy":
			index=3;
			break;
		case "JobTitle":
			index=4;
			break;
		case "HiringManager":
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
	 * Delete vacancy
	 * 
	 * @param vacancy
	 */
	public void deleteVacancy(String vacancy) {
		WebElement deletevacancyElement=webCtrls.Ctrl(By.xpath("//div[text()='"+vacancy+"']//parent::div//following-sibling::div[@role='cell']//i[@class='oxd-icon bi-trash']"));
		webCtrls.buttonClick(deletevacancyElement);
		webCtrls.wait(2);
		webCtrls.buttonClick(dlgBoxYesButton);
	}
	
	// inputs
	@FindBy(xpath="//label[text()='Vacancy Name']//parent::div//following-sibling::div//input[contains(@class,'oxd-input oxd-input')]")
	WebElement txtVacancyName;
	@FindBy(xpath = "//label[text()='Job Title']//parent::div//following-sibling::div//div[@class='oxd-select-text-input']")
	WebElement ddlJobTitle;
	@FindBy(xpath="//textarea[contains(@class,'oxd-textarea oxd-textarea')]")
	WebElement txtDescription;
	@FindBy(xpath="//label[text()='Hiring Manager']//parent::div//following-sibling::div//input[contains(@placeholder,'Type for hints...')]")
	WebElement txtHiringManager;
	@FindBy(xpath="//label[text()='Number of Positions']//parent::div//following-sibling::div//input[contains(@class,'oxd-input oxd-input')]")
	WebElement txtNumOfPositions;
	@FindBy(xpath = "//label[text()='Vacancy']//parent::div//following-sibling::div//div[@class='oxd-select-text-input']")
	WebElement ddlVacancy;
	@FindBy(xpath = "//label[text()='Hiring Manager']//parent::div//following-sibling::div//div[@class='oxd-select-text-input']")
	WebElement ddlHiringManager;
	@FindBy(xpath = "//label[text()='Status']//parent::div//following-sibling::div//div[@class='oxd-select-text-input']")
	WebElement ddlStatus;
	
	// buttons
	@FindBy(xpath = "//button[text()=' Search ']")
	WebElement btnSearch;
	@FindBy(xpath = "//a[text()='Vacancies']")
	WebElement tabVacancies;
	@FindBy(xpath="//button[text()=' Add ']")
	WebElement btnAdd;
	@FindBy(xpath="//button[text()=' Save ']")
	WebElement btnSave;
	@FindBy(xpath = "//div[contains(@class,'dialog-popup')]//button[contains(.,'Yes')]")
	WebElement dlgBoxYesButton;	
	
	public By eleTableRecord(int index) {
		return By.xpath("(//div[@class='oxd-table-cell oxd-padding-cell' and @role='cell']//div)["+index+"]");
	}

}
