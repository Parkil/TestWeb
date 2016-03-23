package util;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Util {
	
	//IE용 웹 드라이버 클래스 반환
	public static WebDriver getIEDriver() {
		StringBuffer sb = new StringBuffer();
		
		sb.append(System.getProperty("user.dir"));
		sb.append(File.separator);
		sb.append("src");
		sb.append(File.separator);
		sb.append("test");
		sb.append(File.separator);
		sb.append("resources");
		sb.append(File.separator);
		sb.append("IEDriverServer.exe");
		
		System.out.println(sb.toString());
		
		System.setProperty("webdriver.ie.driver", sb.toString());
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability("nativeEvents", false);    
		dc.setCapability("unexpectedAlertBehaviour", "accept");
		dc.setCapability("ignoreProtectedModeSettings", true);
		dc.setCapability("disable-popup-blocking", true);
		dc.setCapability("enablePersistentHover", true);

		return new InternetExplorerDriver(dc);
	}
	
	//firegox 용 웹 드라이버 클래스 반환
	public static WebDriver getFireFoxDriver() {
		System.setProperty("webdriver.firefox.bin", "D:/Util/FirefoxPortable/FirefoxPortable.exe");
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability("nativeEvents", false);    
		dc.setCapability("unexpectedAlertBehaviour", "accept");
		dc.setCapability("ignoreProtectedModeSettings", true);
		dc.setCapability("disable-popup-blocking", true);
		dc.setCapability("enablePersistentHover", true);
		return new FirefoxDriver(dc);
	}
}
