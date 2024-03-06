package pages;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import org.testng.Assert;


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
	
	/**
	 * Logout from the user
	 */
	public void logOut() {
		webCtrls.buttonClick(userDropdownTab);
		webCtrls.buttonClick(userLogOut);
	}
	
	
	/**
	 * Verify the main tabs in the Dashboard page
	 * @param String[] tabNames
	 */
	public void verifyMainMenuTabs(String[]tabNames) {
	
		for(int i=0;i<tabNames.length;i++) {
			String expectedTabName=tabNames[i];
			String actualTabName=lstMainMenu.get(i).getText();
			Assert.assertEquals(actualTabName,expectedTabName, "Tab "+expectedTabName+" is not present");
			logger.info("Tab "+expectedTabName+" is present");
			webCtrls.addLog("Pass","Tab "+expectedTabName+" is present");
			}
	}
	
	/**
	 * Verify the User Dropdwon name in the Dashboard page
	 * @param userName
	 */
	public void verifyUserDropdownName(String userName) {
		String actualUserName = webCtrls.getText(userDropdownName);
		Assert.assertEquals(actualUserName, userName, "User with UserName : " + userName + " is not created");
		logger.info("User with UserName : " + userName + " is created");
		webCtrls.addLog("Pass", "User with UserName : " + userName + " is created");
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
	@FindBy(xpath="//a[contains(@class,'oxd-main-menu-item')]//span[@class='oxd-text oxd-text--span oxd-main-menu-item--name']")
	public List<WebElement> lstMainMenu;
	@FindBy(className="oxd-userdropdown-name")
	WebElement userDropdownName;
	@FindBy(className="oxd-userdropdown-tab")
	WebElement userDropdownTab;
	@FindBy(xpath="//a[text()='Logout']")
	WebElement userLogOut;
}
