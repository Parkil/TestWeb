import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.LegacyProxyServer;
import net.lightbody.bmp.proxy.ProxyServer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import util.Util;

/*
 * 실행시간 체크관련 테스트
 * 
 * 2015-10-06 알아낸사항
 * WebDriver객체를 생성하면 테스트 케이스 실행시 1개의 브라우저가 실행된다.
 * WebDriver객체를 여러번 생성하면 새창으로 브라우저가 계속 발생하여 테스트가 진행되지 않는 문제가 있으니 유의할것
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestFireFox_TimeCheck {
	private static WebDriver driver;
	private static JavascriptExecutor je;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver	= Util.getFireFoxDriver();
		je		= (JavascriptExecutor)driver;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//driver.close();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/*
	 * javascript navigaion timing을 이용한 실행시간 측정 
	 * 해당 API는 IE9이상 버전,크롬,파이어폭스에서 사용이 가능하다.
	 */
	//@Test
	public void time_check1() {
		//driver.get("http://dl.dropbox.com/u/55228056/bmicaculator.html");
		driver.get("http://localhost:10010");
		
		long loadEventEnd	 = (Long) je.executeScript("return window.performance.timing.loadEventEnd");
		long navigationStart = (Long) je.executeScript("return window.performance.timing.navigationStart");
		
		System.out.println("Page Load Time is "+(loadEventEnd - navigationStart)/1000 +"seconds");
	}
	
	/*
	 * browsermob을 이용한 실행시간 측정
	 * 현재는 har파일은 생성되는데 내용이 측정되지 않음
	 * 
	 * 2015-10-12확인사항
	 * LegacyProxyServer를 사용하면 내용이 측정되지 않으며 반드시 driver를 new로 테스트케이스내부에서 생성해야 정상적으로 측정된다.
	 */
	@Test
	public void time_check2() throws Exception{
		//browsermob 서버 실행
		//LegacyProxyServer proxyServer = new BrowserMobProxyServer(9090);
		ProxyServer proxyServer = new ProxyServer(9090);
		proxyServer.start();
		
		String proxy_url = "localhost:9090";
		Proxy proxy = proxyServer.seleniumProxy();
		proxy.setHttpProxy(proxy_url).setFtpProxy(proxy_url).setSslProxy(proxy_url);
		
		/*
		 * driver를 static으로 설정하고 getCapabilities를 하면 측정이 되지 않는다.
		
		FirefoxDriver temp = (FirefoxDriver)driver;
		DesiredCapabilities dc = (DesiredCapabilities)temp.getCapabilities();
		dc.setCapability(CapabilityType.PROXY, proxy);
		*/
		
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.PROXY, proxy);

		WebDriver driver = new FirefoxDriver(capabilities);
		
		//har파일 생성
		proxyServer.newHar("time_check2");
		
		driver.get("https://www.google.co.kr");
		
		Har har = proxyServer.getHar();
		FileOutputStream fos = new FileOutputStream("d:/test.har");
		har.writeTo(fos);
		fos.close();
		
		proxyServer.stop();
		driver.quit();
	}
}
