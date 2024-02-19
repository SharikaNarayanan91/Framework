package utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.Test;

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

}
