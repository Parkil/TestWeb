import static org.junit.Assert.*;

import java.io.File;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestIE_case2 {
	private static WebDriver driver;
	private static JavascriptExecutor je;

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

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//driver.close();
	}

	@Before
	public void setUp() throws Exception {
	}
	
	/*
	 * login
	 */
	@Test
	public void test_1() {
		InjectJQuery ij = new InjectJQuery();
		ij.injectJQueryIfNone(je);
		
		je.executeScript("$('#id').val('user')");
		je.executeScript("$('#pw').val('user');");
		
		WebElement submit = driver.findElement(By.cssSelector("input[type='submit']"));
		
		//actions를 이용한 화면 요소 클릭 여러개의 action을 선택한 후에 build().perform()을 호출하면 된다.
		Actions action = new Actions(driver);
		action.click(submit);
		action.build().perform();
	}
	
	/*
	 * alert관련 테스트
	 */
	@Test
	public void test_2() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#alert")));
		
		try {
			String parent = driver.getWindowHandle();
			WebElement ele = driver.findElement(By.cssSelector("#alert"));
			
			ele.click();
			
			Alert a = driver.switchTo().alert();
			
			assertEquals("123456789", a.getText());
			
			driver.switchTo().window(parent); //alert창으로 포커스를 옮겨서 테스트를 진행한 후에 다음테스트를 진행하기 위해서 포커스를 다시 부모창으로 이동
			
			
		}catch(NoAlertPresentException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * confirm 관련 테스트
	 */
	@Test
	public void test_3() {
		WebDriverWait wait = new WebDriverWait(driver,10);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#confirm")));
		
		WebElement el = driver.findElement(By.cssSelector("#confirm"));
		el.click();
		
		Alert a = driver.switchTo().alert();
		//a.accept(); //ok버튼 클릭
		a.dismiss(); //cancel버튼 클릭
		
		driver.get("http://localhost:10010");
	}
	
	/*
	 * prompt 관련 테스트
	 */
	@Test
	public void test_4() {
		WebDriverWait wait = new WebDriverWait(driver,10);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#prompt")));
		
		WebElement el = driver.findElement(By.cssSelector("#prompt"));
		el.click();
		
		Alert a = driver.switchTo().alert();
		a.sendKeys("음화화화");
		a.accept();
		
		WebElement body = driver.findElement(By.tagName("body"));
		assertEquals("음화화화", body.getText());
	}
	
	/*
	 * frameset 관련 테스트
	 * 
	 * iframe도 동일한 동작으로 작동함
	 */
	@Test
	public void test_5() {
		driver.get("http://localhost:10010/frametest/framec.html");
		
		driver.switchTo().frame("left");
		
		WebElement element = driver.findElement(By.tagName("body"));
		assertEquals("Frame A", element.getText());
		
		driver.switchTo().defaultContent(); //최상위 윈도우로 이동
		
		driver.switchTo().frame("right");
		
		WebElement element2 = driver.findElement(By.tagName("body"));
		assertEquals("Frame", element2.getText());
		
		driver.switchTo().defaultContent(); //최상위 윈도우로 이동
		
		driver.switchTo().frame(1); //왼쪽부터 0으로 시작하는 index로 frame을 선택하는 것도 가능
		WebElement element3 = driver.findElement(By.tagName("body"));
		assertEquals("Frame B", element3.getText());
	}
}
