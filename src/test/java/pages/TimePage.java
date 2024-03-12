package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.beust.jcommander.Parameter;
import com.github.dockerjava.api.model.Driver;
import com.github.dockerjava.core.MediaType;

import helper.WebCtrls;
import testcases.Login;
import utils.ListenerClass;
import utils.ScreenshotHelper;

public class TimePage {
	private static Logger logger = LogManager.getLogger(TimePage.class);
	WebCtrls webCtrls = new WebCtrls();

	WebDriver ldriver;

	public TimePage(WebDriver rdriver) {
		ldriver = rdriver;
		PageFactory.initElements(rdriver, this);
	}
	DashboardPage dashboardPage=new DashboardPage(ldriver);
	
	/**
	 * View Employee Timesheet
	 * @param employeeName
	 */
	public void viewEmployeeTimesheet(String employeeName) {
		webCtrls.wait(2);
		webCtrls.selectFromAutosuggestiveDropdown(txtEmployeeName, employeeName);
		webCtrls.buttonClick(btnView);	
	}
	/**
	 * Create timesheet
	 * @param project
	 * @param activity
	 * @param workHours
	 */
	public void createTimesheet(String project, String activity, String[] workHours) {
		webCtrls.buttonClick(btnCreateTimesheet);
		webCtrls.buttonClick(btnEdit);
		webCtrls.wait(1);
		WebElement projElement = webCtrls
				.Ctrl(By.xpath("//div[contains(@class,'oxd-autocomplete-text-input')]//input"));
		WebElement actElement = webCtrls.Ctrl(By.xpath("//div[contains(@class,'oxd-select-text oxd-select-text')]"));

		webCtrls.selectFromAutosuggestiveDropdown(projElement, project);
		webCtrls.wait(1);
		webCtrls.selectFromDropdown(actElement, activity);
		for (int i = 1; i <=workHours.length; i++) {
			WebElement workHr = webCtrls.Ctrl(By.xpath(
					"((//div[contains(@class,'oxd-autocomplete-text-input')]//ancestor::td)[1]//following-sibling::td//input[contains(@class,'oxd-input oxd-input')])["
							+ i + "]"));
			webCtrls.setData(workHr, workHours[i-1]);
		}
		webCtrls.buttonClick(btnSave);
		
	}
	/**
	 * Verify Timesheet Saved
	 * @param project
	 * @param activity
	 */
	public void verifySavedTimesheet(String project,String activity) {
		webCtrls.wait(5);
		WebElement projectElement=webCtrls.Ctrl(By.xpath("//span[contains(text(),'"+project+"')]"));
		Assert.assertTrue(webCtrls.isDisplayed(projectElement), "Project Added is not displayed");
		logger.info("Project Added to timesheet is displayed as expected : "+project);
		webCtrls.addLog("Pass","Project Added to timesheet is displayed as expected : "+project);
	
		WebElement actElement=webCtrls.Ctrl(By.xpath("//span[contains(text(),'"+project+"')]//parent::td//following-sibling::td//span[contains(text(),'"+activity+"')]"));
		Assert.assertTrue(webCtrls.isDisplayed(actElement), "Activity Added is not displayed");
		logger.info("Activity Added to timesheet is displayed as expected : "+activity);
		webCtrls.addLog("Pass","Activity Added to timesheet is displayed as expected : "+activity);
	}
	/**
	 * Verify Status of Timesheet
	 * @param expStatus
	 */
	public void verifyTimesheetStatus(String expStatus) {
		String actualFullStatus=webCtrls.getText(eleStatus);
		String actualStatus=actualFullStatus.split(":")[1].trim();
		Assert.assertEquals(actualStatus, expStatus,"Status of timesheet displayed is not as expected");
		logger.info("Status of timesheet displayed is as expected : "+expStatus);
		webCtrls.addLog("Pass","Status of timesheet displayed is as expected : "+expStatus);
	}
	
	// buttons
	@FindBy(xpath = "//button[text()=' Create Timesheet ']")
	WebElement btnCreateTimesheet;
	@FindBy(xpath = "//p[contains(@class,'oxd-text oxd-text--p oxd-text--subtitle')]")
	WebElement eleStatus;
	@FindBy(xpath = "//button[text()=' Edit ']")
	WebElement btnEdit;
	@FindBy(xpath="//button[text()=' Save ']")
	WebElement btnSave;
	@FindBy(xpath="//label[text()='Employee Name']//parent::div//following-sibling::div//input[contains(@placeholder,'Type for hints...')]")
	WebElement txtEmployeeName;
	@FindBy(xpath="//button[text()=' View ']")
	WebElement btnView;
}