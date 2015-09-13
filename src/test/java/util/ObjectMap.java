package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;

public class ObjectMap {
	private Properties prop;
	
	public ObjectMap(String path) {
		FileInputStream fis = null;
		prop = new Properties();
		
		try {
			fis = new FileInputStream(path);
			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(fis != null) fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public By getLocator(String ElementName) throws Exception {
		String locator = prop.getProperty(ElementName);
		
		String locator_type	 = locator.split(">")[0];
		String locator_value = locator.split(">")[1];
		
		if(locator_type.toLowerCase().equals("id")) {
			return By.id(locator_value);
		}else if(locator_type.toLowerCase().equals("name")) {
			return By.name(locator_value);
		}else if(locator_type.toLowerCase().equals("classname")) {
			return By.className(locator_value);
		}else if(locator_type.toLowerCase().equals("tagname") || locator_type.toLowerCase().equals("tag")) {
			return By.tagName(locator_value);
		}else if(locator_type.toLowerCase().equals("linktext") || locator_type.toLowerCase().equals("link")) {
			return By.linkText(locator_value);
		}else if(locator_type.toLowerCase().equals("partiallinktext")) {
			return By.partialLinkText(locator_value);
		}else if(locator_type.toLowerCase().equals("css") || locator_type.toLowerCase().equals("cssselector")) {
			return By.cssSelector(locator_value);
		}else if(locator_type.toLowerCase().equals("xpath")) {
			return By.xpath(locator_value);
		}else {
			throw new Exception(String.format("Locator type '%1$s' not defined", locator_type));
		}
	}
}
