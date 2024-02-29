package utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import Base.BaseTest;

public class ScreenshotHelper extends BaseTest {
	public static String TakeScreenShot() {
		String screenshot =System.getProperty("user.dir")+File.separator+"test-output/screenshots/"+GenerateRandomHelper.GenerateRandomStringWithDateTime()+".jpg";
		File src=((TakesScreenshot)BaseTest.driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(src, new File(screenshot));
		}catch(IOException e) {
			e.printStackTrace();
		}
		return screenshot;
	}
	public static String TakeScreenShot(String fileName) {
		String screenshot =System.getProperty("user.dir")+File.separator+"test-output/screenshots"+"/"+fileName+".jpg";
		File src=((TakesScreenshot)BaseTest.driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(src, new File(screenshot));
		}catch(IOException e) {
			e.printStackTrace();
		}
		return screenshot;
		
	}
	//Capture screenshot base64 image
	public static String CaptureScreenShotBase64() {
		String Base64src=((TakesScreenshot)BaseTest.driver).getScreenshotAs(OutputType.BASE64);
		return Base64src;
	}
}
