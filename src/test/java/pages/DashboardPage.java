package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import helper.WebCtrls;
import testcases.Login;

public class DashboardPage {
	private static Logger logger = LogManager.getLogger(Login.class);
	WebCtrls webCtrls = new WebCtrls();

	WebDriver ldriver;

	public DashboardPage(WebDriver rdriver) {
		ldriver = rdriver;
		PageFactory.initElements(rdriver, this);
	}

	@FindBy(className = "oxd-input oxd-input")
	WebElement txtSearch;
	@FindBy(xpath = "//span[text()='Admin']")
	WebElement tabAdmin;
	@FindBy(xpath = "//span[text()='PIM']")
	WebElement tabPIM;
	@FindBy(xpath = "//span[text()='Leave']")
	WebElement tabLeave;
	@FindBy(xpath = "//span[text()='Time']")
	WebElement tabTime;
	@FindBy(xpath = "//span[text()='Recruitment']")
	WebElement tabRecruitment;
	@FindBy(xpath = "//span[text()='My Info']")
	WebElement tabMyInfo;
	@FindBy(xpath = "//span[text()='Performance']")
	WebElement tabPerformance;
	@FindBy(xpath = "//span[text()='Dashboard']")
	WebElement tabDashboard;
	@FindBy(xpath = "//span[text()='Directory']")
	WebElement tabDirectory;
	@FindBy(xpath = "//span[text()='Maintenance']")
	WebElement tabMaintenance;
	@FindBy(xpath = "//span[text()='Claim']")
	WebElement tabClaim;
	@FindBy(xpath = "//span[text()='Buzz']")
	WebElement tabBuzz;
	@FindBy(xpath = "//h6[text()='Dashboard']")
	WebElement eleDashboardTitle;

	/**
	 * Click on the Admin tab
	 */
	public void clickAdmin() {
		webCtrls.buttonClick(tabAdmin);
	}

	/**
	 * Click on the PIM tab
	 */
	public void clickPIM() {
		webCtrls.buttonClick(tabPIM);
	}

	/**
	 * Click on the Dashboard tab
	 */
	public void clickDashboard() {
		webCtrls.buttonClick(tabDashboard);
	}
}
