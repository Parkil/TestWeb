import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;


public class TestIE {
	private static WebDriver driver;
	private static JavascriptExecutor je;
	
	/*
	 * 해당클래스가 시작전 실행될 내용을 정의
	 * - 테스트케이스개수와 상관없이 테스트케이스 클래스별로 1번만 실행
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("setUpBeforeClass");
		
		StringBuffer sb = new StringBuffer();
		System.out.println("setUp");
		
		sb.append(System.getProperty("user.dir"));
		sb.append(File.separator);
		sb.append("src");
		sb.append(File.separator);
		sb.append("test");
		sb.append(File.separator);
		sb.append("resources");
		sb.append(File.separator);
		sb.append("IEDriverServer.exe");
		
		System.setProperty("webdriver.ie.driver", sb.toString());
		driver = new InternetExplorerDriver();
		je = (JavascriptExecutor)driver;
		driver.get("http://localhost:10010");
	}
	
	/*
	 * 해당클래스가 종료후 실행될 내용을 정의
	 * - 테스트케이스개수와 상관없이 테스트케이스 클래스별로 1번만 실행
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("tearDownAfterClass");
		
		driver.close();
	}
	
	/*
	 * 각 테스트 케이스가 시작하기전 실행될 내용을 정의
	 * - 테스트 케이스 개수만큼 실행된다 (테스트케이스가 2개면 2번)
	 */
	@Before
	public void setUp() throws Exception {
		System.out.println("setUp");
	}
	
	/*
	 * 테스트케이스가 종료후 실행될 내용을 정의
	 * - 테스트케이스 개수만큼 실행된다.
	 */
	@After
	public void tearDown() throws Exception {
		System.out.println("tearDown");
	}
	
	@Test
	public void login_test() {
		InjectJQuery ij = new InjectJQuery();
		ij.injectJQueryIfNone(je);
		
		je.executeScript("$('#id').val('user')");
		je.executeScript("$('#pw').val('user111');");
		
		WebElement submit = driver.findElement(By.cssSelector("input[type='submit']"));
		
		//actions를 이용한 화면 요소 클릭 여러개의 action을 선택한 후에 build().perform()을 호출하면 된다.
		Actions action = new Actions(driver);
		action.click(submit);
		action.build().perform();
		
		//fail("Not yet implemented");
	}
	
	/*
	 * 주의할 점
	 * submit이던 location.href던 url을 이동하는 동작후 url을 검사하려고 하면 동일한 테스트케이스에서는 전 url만 잡히기 때문에
	 * 검사가 제대로 되지 않는다 주의할 것
	 * 
	 * ex)
	 * a.do ->(submit) b.do 일경우
	 * 테스트케이스1에서 submit을 실행하고 driver.getCurrentUrl()을 실행하면 a.do로 표시가 된다.
	 * 
	 * 다른 테스트케이스에서 driver.getCurrentUrl()을 실행해야 정상적으로 표시가 된다.
	 */
	@Test
	public void submit_after_test() {
		assertEquals(-1, driver.getCurrentUrl().indexOf("fail=true"));
		/*
		System.out.println("teardown test : "+driver.getCurrentUrl());
		System.out.println("pageSource : "+driver.getPageSource());
		System.out.println("title"+driver.getTitle());
		System.out.println("window handle : "+driver.getWindowHandle());
		*/
	}
}
