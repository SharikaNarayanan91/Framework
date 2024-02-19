package helper;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Base.BaseTest;

public class WebCtrls extends BaseTest{
	WebElement webElement;

	public void setData(WebElement locator,String value) {
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.visibilityOf(locator));
		locator.sendKeys(value);
	//	driver.findElement(txtUsername).sendKeys(value);
	}
	public String getText(WebElement locator) {
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.visibilityOf(locator));
		
		String text=locator.getText();
		return text;				
	}
	public boolean isDisplayed(WebElement locator)  {
		boolean status=false;
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.visibilityOf(locator));
		try {
			webElement = locator;
			if(webElement.isDisplayed())
				status=true;
			return status;
		}
		catch(Exception e) {
			e.printStackTrace();
			return status;
		}				
	}
	public void buttonClick(WebElement locator) {
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.elementToBeClickable(locator));
		locator.click();
	}
	public  WebDriverWait getWait() {
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.ignoring(NoSuchElementException.class);
		return wait;

	}
	public void autoSuggestiveDropdown(WebElement locator,String value) {
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.visibilityOf(locator));
		setData(locator, value); 
		locator.sendKeys(Keys.ARROW_UP);
		locator.sendKeys(Keys.ENTER);

	}
	public void selectFromDropdown(WebElement locator,String value) {
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.visibilityOf(locator));
		List<WebElement> values=locator.findElements(By.tagName("div"));
		for (WebElement dropdownItem : values) {
			if (dropdownItem.getText().split("\\(")[0].equals(value)) {
				dropdownItem.click();
				break;
			}
		}
	}
	public void scroll() {		
		JavascriptExecutor js = (JavascriptExecutor) BaseTest.driver;
		js.executeScript("window.scrollBy(0,1000)");
	}
	
	public void buttonClick(By locator) {
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.elementToBeClickable(locator));
		driver.findElement(locator).click();
	}
	public boolean isDisplayed(By locator)  {
		boolean status=false;
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		try {
			webElement = driver.findElement(locator);
			if(webElement.isDisplayed())
				status=true;
			return status;
		}
		catch(Exception e) {
			e.printStackTrace();
			return status;
		}				
	}
}
