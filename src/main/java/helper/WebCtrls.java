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

	public void setData(By locator,String value) {
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		driver.findElement(locator).sendKeys(value);
	}
	public String getText(By locator) {
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		String text=driver.findElement(locator).getText();
		return text;				
	}
	public boolean isDisplayed(By locator) {
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
	public void buttonClick(By locator) {
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.elementToBeClickable(locator));
		driver.findElement(locator).click();
	}
	public  WebDriverWait getWait() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.ignoring(NoSuchElementException.class);
		return wait;

	}
	public void autoSuggestiveDropdown(By locator,String value) {
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		setData(locator, value);
		driver.findElement(locator).sendKeys(Keys.ARROW_UP);
		driver.findElement(locator).sendKeys(Keys.ENTER);

	}
	public void selectFromDropdown(By locator,String value) {
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		List<WebElement> values= driver.findElements(locator);
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
}
