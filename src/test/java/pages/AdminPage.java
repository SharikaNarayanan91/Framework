package pages;

import org.openqa.selenium.By;

public class AdminPage {
	//inputs
	public By txtUsername=By.xpath("//label[text()='Username']//parent::div//following-sibling::div//input[contains(@class,'oxd-input')]");

	//dropDown
	public By ddlUserRole=By.xpath("//label[text()='User Role']//parent::div//following-sibling::div//div[@class='oxd-select-text-input']");
	public By ddlGeneralList=By.xpath("//div[@role='listbox']//div");
	public By ddlStatus =By.xpath("//label[text()='Status']//parent::div//following-sibling::div//div[@class='oxd-select-text-input']");
	
	//buttons
	public By btnSearch=By.xpath("//button[text()=' Search ']");
	
	//elements
	public By eleUsername=By.xpath("//div[text()='Username']//following-sibling::div[text()='Cheeku']");
	public By eleUserRole=By.xpath("//div[text()='User Role']//following-sibling::div[text()='Admin']");
	public By eleRecordsFound(String count) {return By.xpath("//span[text()='("+count+") Record Found']");}
}
