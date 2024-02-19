package utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {

	Properties pro;
	
	public ReadConfig()
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
	
	public String getApplicationURL(){
	
		String url=pro.getProperty("baseURL");
		return url;
	}
	
	public String getUserName(){
		
		String userName=pro.getProperty("Username");
		return userName;
	}
 public String getPassword(){
		
		String password=pro.getProperty("Password");
		return password;
	}
}
