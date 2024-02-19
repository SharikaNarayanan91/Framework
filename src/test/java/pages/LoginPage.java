package pages;

import org.openqa.selenium.By;


public class LoginPage {
	//input
	public By txtUsername=By.xpath("//input[@name='username']");
	public By txtPassword=By.xpath("//input[@name='password']");

	//buttons
	public By btnLogin=By.xpath("//button[text()=' Login ']");

	//Elements
	public By eleDashboardTitle=By.xpath("//h6[text()='Dashboard']");
	public By eleInvalidCredentialsError=By.xpath("//i[@class='oxd-icon bi-exclamation-circle oxd-alert-content-icon']//following-sibling::p[text()='Invalid credentials']");

	public By linkMainMenuOptions(String MenuOption){return By.xpath("//span[contains(@class,'main-menu') and text()='"+MenuOption+"']");}

}