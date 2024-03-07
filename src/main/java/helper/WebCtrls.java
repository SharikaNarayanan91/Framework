package helper;

import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import Base.BaseTest;
import utils.GenerateRandomHelper;
import utils.ListenerClass;
import utils.ScreenshotHelper;

public class WebCtrls extends BaseTest {
	private static Logger logger = LogManager.getLogger(WebCtrls.class);
	GenerateRandomHelper genRandomHlper=new GenerateRandomHelper();

	WebElement webElement;
	/**
	 * Set data
	 * @param locator
	 * @param value
	 */
	public void setData(WebElement locator, String value) {
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.visibilityOf(locator));
		locator.sendKeys(value);
		String locatorName=locator.getAttribute("name");
		if(locatorName.length()!=0) {
		logger.info("Entered "+locatorName+" as " + value);
		addLog("Info","Entered "+locatorName+" as " + value);
		}
		else {
			logger.info("Entered "+locator+" as " + value);
			addLog("Info","Entered "+locator+" as " + value);
		}		
	}
	/**
	 * Set data for encrypted value(e.g.Password)
	 * @param locator
	 * @param value
	 */
	public void setEncryptedData(WebElement locator, String value) {
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.visibilityOf(locator));
		locator.sendKeys(value);
		String locatorName=locator.getAttribute("name");
		if(locatorName.length()!=0) {
			logger.info("Entered "+locatorName+" as " + encryptString(value));
			addLog("Info","Entered "+locatorName+" as " + encryptString(value));
			}
			else {
				logger.info("Entered "+locator+" as " + encryptString(value));
				addLog("Info","Entered "+locator+" as " + encryptString(value));
			}
	}
	/**
	 * Get text 
	 * @param WebElement locator
	 */
	public String getText(WebElement locator) {
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.visibilityOf(locator));
		String text = locator.getText();
		return text;
	}
	/**
	 * To check whether element is displayed
	 * @param WebElement locator
	 */
	public boolean isDisplayed(WebElement locator) {
		boolean status = false;
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.visibilityOf(locator));
		try {
			webElement = locator;
			if (webElement.isDisplayed())
				status = true;
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			return status;
		}
	}
	/**
	 * To click a button/link
	 * @param WebElement locator
	 */
	public void buttonClick(WebElement locator) {
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.elementToBeClickable(locator));
		String buttonName=locator.getText();
		locator.click();
		if(buttonName.length()!=0) {
		logger.info("Clicked on "+buttonName);
		addLog("Info","Clicked on "+buttonName);
		}else {
			logger.info("Clicked on "+locator);
			addLog("Info","Clicked on "+locator);
		}
			
	}
	/**
	 * To get a wait
	 * @param WebElement locator
	 */
	public WebDriverWait getWait() {
		WebDriverWait wait = new WebDriverWait(driver, 200);
		wait.ignoring(NoSuchElementException.class);
		return wait;

	}

	/**
	 * To select element from dropdwon
	 * @param WebElement locator
	 * @param value
	 */
	public void selectFromDropdown(WebElement locator, String value) {
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.visibilityOf(locator));
        locator.click();
        List<WebElement> dropdownOptions = driver.findElements(By.xpath("//div[@role='option']"));
        for (WebElement option : dropdownOptions) {
            if (option.getText().equalsIgnoreCase(value)) {
                option.click();
                return;
            }
        }
		logger.info("Selected "+value+" from dropdown");
		addLog("Info","Selected "+value+" from dropdown");
	}
	/**
	 * To scroll to the bottom of the page
	 */
	public void scroll() {
		JavascriptExecutor js = (JavascriptExecutor) BaseTest.driver;
		js.executeScript("window.scrollBy(0,1000)");
	}
	/**
	 * To click a button/link
	 * @param By locator
	 */
	public void buttonClick(By locator) {
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.elementToBeClickable(locator));
		String buttonName=driver.findElement(locator).getText();
		driver.findElement(locator).click();
		if(buttonName.length()!=0) {
			logger.info("Clicked on "+buttonName);
			addLog("Info","Clicked on "+buttonName);
			}else {
				logger.info("Clicked on "+locator);
				addLog("Info","Clicked on "+locator);
			}
	}
	/**
	 * To check whether element is displayed
	 * @param By locator
	 */
	public boolean isDisplayed(By locator) {
		boolean status = false;
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		try {
			webElement = driver.findElement(locator);
			if (webElement.isDisplayed())
				status = true;
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			return status;
		}
	}
	/**
	 * Log info and screenshot to report
	 * @param String status :(Pass 'Info'-log status INFO and 'Pass'-log status PASS)
	 * @param Strng title
	 */
	public void addLog(String status, String title) {
		String Base64Code = ScreenshotHelper.CaptureScreenShotBase64();
		switch (status) {
		case "Pass":
			ListenerClass.report.log(Status.PASS,
					MediaEntityBuilder.createScreenCaptureFromBase64String(Base64Code, "\n" + title).build());
			break;

		case "Info":
			ListenerClass.report.log(Status.INFO,
					MediaEntityBuilder.createScreenCaptureFromBase64String(Base64Code, "\n" + title).build());
			break;
		}
	}
	/**
	 * Encrypt a string
	 * @param arg : Value to be encrypted
	 */
	public String encryptString(String arg) {
		byte[]encryptedArg=Base64.getEncoder().encode(arg.getBytes());
		String encryptedValue=new String(encryptedArg);
		return encryptedValue;
	}
	/**
	 * Decrypt a string
	 * @param arg : Value to be decrypted
	 */	
	public String decryptString(String arg) {
		byte[] decryptedArg = Base64.getDecoder().decode(arg.getBytes());
		String decryptedValue = new String(decryptedArg);
		return decryptedValue;
	}
	/**
	 * Get text 
	 * @param By locator
	 */
	public String getText(By locator) {
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		String text =driver.findElement(locator).getText();
		return text;		
	}
	/**
	 * To wait for a duration
	 * @param duration
	 */
	public void wait(int duration) {
		try {
			Thread.sleep(duration*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * To scroll to an element
	 * @param WebElement element
	 */
	public void scrollToElement(WebElement element) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		// Execute JavaScript to scroll to the element
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	/**
	 * To generate random number
	 * @param length
	 */
	public int genrateRandomNumber(int length) {
		int randomNum = genRandomHlper.GenerateRandomNumber(length);
		return randomNum;
	}
	/**
	 * To generate random alpha string
	 * @param length
	 */
	public String genrateRandomAlphaString(int length) {
		String randomString = genRandomHlper.GenerateRandomAlphaString(length);
		return randomString;
	}
	/**
	 * To change the string to Title case format
	 * @param String param
	 */
	public String titleCase(String param) {
		 String text=Character.toTitleCase(param.charAt(0))+param.substring(1).toLowerCase();
		 return text;
	 }
	/**
	 * Javascipt click a button/link
	 * @param WebElement locator
	 */
	public void javaScriptClick(WebElement locator) {
		String buttonName = locator.getText();
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", locator);
		if(buttonName.length()!=0) {
			logger.info("Clicked on "+buttonName);
			addLog("Info","Clicked on "+buttonName);
			}else {
				logger.info("Clicked on "+locator);
				addLog("Info","Clicked on "+locator);
			}
	}

	/**
	 * Find element
	 * @param by
	 */
	public WebElement Ctrl(By by) {
		return driver.findElement(by);
	}

	/**
	 * Clear text by selecting and deleting original text
	 * 
	 * @param locator
	 */
	public void clearTextByDeleting(WebElement locator) {
		locator.sendKeys(Keys.CONTROL + "a");
		locator.sendKeys(Keys.DELETE);
	}

	/**
	 * To select element from autosuggestive dropdown
	 * 
	 * @param WebElement inputLocator
	 * @param value
	 */
	public void selectFromAutosuggestiveDropdown(WebElement inputLocator, String value) {
		inputLocator.sendKeys(value);
		wait(2);
		String suggestionElement = "//div[@role='option']";
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(suggestionElement)));
		List<WebElement> dropdownOptions = driver.findElements(By.xpath(suggestionElement));
		for (WebElement option : dropdownOptions) {
			if (option.getText().equalsIgnoreCase(value)) {

				option.click();
				return;
			}
		}
		logger.info("Selected " + value + " from dropdown");
		addLog("Info", "Selected " + value + " from dropdown");
	}
	/**
	 * To select value from chevron dropdwon
	 * @param WebElement locator
	 * @param value
	 */
	public void selectFromChevronDropdown(WebElement locator, String value) {
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.visibilityOf(locator));
        locator.click();
        List<WebElement> dropdownOptions = driver.findElements(By.xpath("//a[@role='menuitem']"));
        for (WebElement option : dropdownOptions) {
            if (option.getText().equalsIgnoreCase(value)) {
                option.click();
                return;
            }
        }
		logger.info("Selected "+value+" from chevron dropdown");
		addLog("Info","Selected "+value+" from chevron dropdown");
	}
}
