package nest_pageobject;

import org.openqa.selenium.WebDriver;

import util.Util;

/*
 * 중첩 pagefactory에서 사용할 공통 클래스
 */
public class Browser {
	private static WebDriver driver = Util.getIEDriver();
	
	public static WebDriver driver() {
		System.out.println("open : "+driver.hashCode());
		return driver;
	}
	
	public static void open(String url) {
		driver.get(url);
	}
	
	public static void close() {
		System.out.println("close : "+driver.hashCode());
		driver.close();
	}
}
