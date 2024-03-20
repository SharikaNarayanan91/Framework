package utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {
	Properties pro;
	public  ReadConfig()
	{
		File src=new File(System.getProperty("user.dir")+File.separator+"Configurations/config.properties");
		try {
			FileInputStream fis=new FileInputStream(src);
			pro=new Properties();
			pro.load(fis);
			
		}catch(Exception e) {
			System.out.println("Exception is "+e.getMessage());
		}
	}
	//Read the key-value pair from property file
	public String readPropertyFile(String key) {
		String value=pro.getProperty(key);
		return value;
	}
}
