package pages;

import org.openqa.selenium.By;

public class DirectoryPage {
	//input
	public By txtEmployeeName=By.xpath("//input[@placeholder='Type for hints...']");
	public By listEmployeeName=By.xpath("//div[@role='listbox']//*");
	//dropDown
	public By ddlJobeTitle=By.xpath("//label[text()='Job Title']//parent::div//following-sibling::div//div[@class='oxd-select-wrapper']");
	public By ddlLocation=By.xpath("//label[text()='Location']//parent::div//following-sibling::div//div[@class='oxd-select-wrapper']");
	public By ddlGeneralList=By.xpath("//div[@role='listbox']//div");
	//button
	public By btnSearch=By.xpath("//button[text()=' Search ']");

	//element
	public By  eleEmployeeNameInDirectoryCard(String EmployeeName)
	{return By.xpath("//div[@class='orangehrm-directory-card-top']//following-sibling::p[text()='"+EmployeeName+" ']");}
	public By eleEmployeeJobTitleInDirectoryCard(String JobTitle) {return By.xpath("//div[contains(@class,'orangehrm-directory-card')]//p[text()='"+JobTitle+"']");}
	public By eleEmployeeLocationInDirectoryCard(String Location) {return By.xpath("//div[contains(@class,'orangehrm-directory-card')]//p[text()='"+Location+"']");}

}
