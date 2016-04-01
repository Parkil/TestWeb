package gen_template;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import util.Util;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AutoSearch {
	private static WebDriver driver;
	private static JavascriptExecutor je;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = Util.getIEDriver();
		je = (JavascriptExecutor)driver;
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
	
	
	//로그인
	@Test
	public void test() {
		driver.get("http://localhost:8080");
		
		WebElement id = driver.findElement(By.cssSelector("#id"));
		WebElement pw = driver.findElement(By.cssSelector("#pw"));
		WebElement submit = driver.findElement(By.cssSelector("input[type='submit']"));
		
		id.sendKeys("user");
		pw.sendKeys("user");
		submit.click();
		
		WebDriverWait wdw = new WebDriverWait(driver,10);
		wdw.until(ExpectedConditions.titleContains("List"));
		
		assertEquals(driver.getCurrentUrl(), "http://localhost:8080/sample/egovSampleList.do");
	}
	
	//@Test
	public void test2() {
		//1.로딩이 다 되었는지 확인
		
		//2.해당 페이지에서 클릭가능한 요소를 추출(여기서는 a tag만 지정)
		By a_tag = By.tagName("a");
		List<WebElement> a_list = driver.findElements(a_tag);
		
		/*
		 * 3.리스트를 돌면서 요소를 클릭
		 * 		<공통 요소>
		 * 		페이지가 이동 되었을 경우 - staleElementException방지를 위해서 다시 2번을 반복한다
		 * 		클릭후 페이지가 refresh되었는지 WebDriverWait를 이용
		 * 		해당 페이지가 로그인되었을 경우 한번 로그인이 되면 다음 부터를 로그인하지 않도록 처리
		 * 
		 * 		<클릭별>
		 * 		클릭후
		 * 			case1 : 다른 페이지로 이동(상단 url이 변경)
		 * 			case2 : 다른 페이지로 이동(상단 url이 변경되지 않음 - iframe 또는 url은 고정이고 특정 페이지에서 include를 이용하여 페이지를 표시
		 * 			case3 : 페이지가 바뀌지 않고 팝업이 표시되는 경우
		 * 			case4 : 페이지가 바뀌지 않고 팝업도 표시되지 않음(외부 연동이나 기타 특정 기능 수행)
		 * 
		 *			case1,2의 경우 이전 url(1번이 수행되고 표시된 화면의 url)로 되돌아가는 방법 강구필요(javascript history.back or driver.get())
		 *			case3의 경우는 팝업이 존재하는지 확인하는 로직 필요
		 *			case4의 경우는 그냥 진행
		 */
		
		WebElement el = null;
		String parent_current_url = driver.getCurrentUrl(); //부모 페이지 url
		for(int i = 0,length = a_list.size() ; i < length ; i++) {
			try {
				el = a_list.get(i);
				
				if(el.getText().intern() == "로그아웃".intern()) {
					continue;
				}
				
				el.click();
			}catch(StaleElementReferenceException e) {
				a_list = driver.findElements(a_tag);
				el = a_list.get(i);
			}
			
			WebDriverWait wdw = new WebDriverWait(driver,10);
			wdw.until(ExpectedConditions.elementToBeClickable(By.tagName("body")));
			
			String child_current_url = driver.getCurrentUrl();
			
			if(parent_current_url.intern() == child_current_url.intern()) {
				System.out.println("부모 - 자식 url이 동일함 : "+parent_current_url);
			}else {
				System.out.println("부모 - 자식간 url이 동일하지 않음");
				System.out.println(je.executeScript("return document.referrer", ""));
				//driver.get(parent_current_url);
				driver.navigate().back();
			}
		}
	}
	
	@Test
	public void test2_1() {
		String url = driver.getCurrentUrl();
		By a_tag = By.tagName("a");
		List<WebElement> a_list = driver.findElements(a_tag);
		
		a_list.get(3).click();
		
		WebDriverWait wdw = new WebDriverWait(driver,10);
		wdw.until(ExpectedConditions.elementToBeClickable(By.tagName("body")));
		
		By input_hidden = By.cssSelector("input[type=hidden]");
		List<WebElement> hidden_list = driver.findElements(input_hidden);
		
		driver.get(url+Util.getQueryString(hidden_list));
	}
}
