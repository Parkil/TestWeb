package util;

import java.io.File;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Util {
	
	/**IE용 웹 드라이버 클래스 반환
	 * @return
	 */
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
	
	/**firegox 용 웹 드라이버 클래스 반환
	 * @return
	 */
	public static WebDriver getFireFoxDriver() {
		System.setProperty("webdriver.firefox.bin", "D:/Util/FirefoxPortable/App/Firefox/firefox.exe");
		
		DesiredCapabilities dc = new DesiredCapabilities();
		/*dc.setCapability("nativeEvents", false);    
		dc.setCapability("unexpectedAlertBehaviour", "accept");
		dc.setCapability("ignoreProtectedModeSettings", true);
		dc.setCapability("disable-popup-blocking", true);
		dc.setCapability("enablePersistentHover", true);
		*/
		return new FirefoxDriver(dc);
	}
	
	/**chrome용 웹 드라이버 클래스 반환
	 * @return
	 */
	public static WebDriver getChromeDriver() {
		StringBuffer sb = new StringBuffer();
		
		sb.append(System.getProperty("user.dir"));
		sb.append(File.separator);
		sb.append("src");
		sb.append(File.separator);
		sb.append("test");
		sb.append(File.separator);
		sb.append("resources");
		sb.append(File.separator);
		sb.append("chromedriver.exe");
		
		System.out.println(sb.toString());
		
		System.setProperty("webdriver.chrome.driver", sb.toString()); //driver위치 반환
		ChromeOptions co = new ChromeOptions();
		co.setBinary("C:/Program Files (x86)/Google/Chrome/Application/chrome.exe"); //cannot find chrome binary 예외 발생시에는 chrome실행파일을 수동으로 지정해 주어야 함
		return new ChromeDriver(co);
	}
	
	/**WebElement list의 값을 기반으로 queryString 작성
	 * @param list
	 * @return
	 */
	public static String getQueryString(List<WebElement> list) throws OperationNotSupportedException{
		StringBuffer sb = new StringBuffer();
		
		for(int i = 0,length = list.size() ; i<length ; i++) {
			WebElement el = list.get(i);
			
			if(el.getTagName().intern() != "input") {
				throw new OperationNotSupportedException("this element not input");
			}

			String prefix = (i == 0) ? "?" : "&";
			sb.append(prefix);
			sb.append(String.format("%1$s=%2$s", el.getAttribute("name"), el.getAttribute("value")));
		}
		
		return sb.toString();
	}
}
