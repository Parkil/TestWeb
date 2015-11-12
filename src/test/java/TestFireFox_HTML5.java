import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.aspectj.util.FileUtil;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import util.Util;
import util.WebElementExpand;

/*
 * HTML5 관련 테스트케이스
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestFireFox_HTML5 {
	private static WebDriver driver;
	private static JavascriptExecutor je;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = Util.getFireFoxDriver();
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
	
	/*
	 * html5 video tag를 자동 테스트
	 */
	//@Test
	public void video_test() throws Exception{
		File scrFile = null;
		
		driver.get("http://html5demos.com/assets/dizzy.mp4"); //html5 테스트 페이지 이동
		
		WebElement videoPlayer = driver.findElement(By.tagName("video")); //video tag 추출
		
		String source = (String)je.executeScript("return arguments[0].currentSrc;", videoPlayer); //현재 플레이중인 동영상 url
		
		long play_time = (Long)je.executeScript("return arguments[0].duration", videoPlayer); //동영상 길이
		
		assertEquals("http://html5demos.com/assets/dizzy.mp4", source);
		assertEquals(25, play_time);
		
		je.executeScript("return arguments[0].pause()", videoPlayer); //동영상 정지
		Thread.sleep(5000);
		
		je.executeScript("arguments[0].play()", videoPlayer); //동영상 재생
		
		scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtil.copyFile(scrFile, new File("d:/test2.png"));
	}
	
	/*
	 * canvas 테스트
	 * 2015-10-14 주의할점 
	 * iframe안에 검색하고자하는 요소가 있을경우 먼저 driver.switchTo.frame을 이용하여 먼저 frame을 이동후에 검색하여야 함.
	 */
	//@Test
	public void canvas_test() throws Exception{
		//driver.get("https://developer.mozilla.org/en-US/demos/detail/zen-photon-garden/launch");
		
		driver.get("https://developer.mozilla.org/ko/demos/detail/html5-drawings/launch");
		
		WebDriverWait wdw = new WebDriverWait(driver,10);
		wdw.until(ExpectedConditions.titleContains("HTML5"));
		
		driver.switchTo().frame("demoframe");
		
		WebElement canvas = driver.findElement(By.tagName("canvas"));
		
		Actions builder = new Actions(driver);
		builder.clickAndHold(canvas).moveByOffset(10, 50).
		moveByOffset(50,10).
		moveByOffset(-10,-50).
		moveByOffset(-50,-10).release().perform();
		
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtil.copyFile(scrFile, new File("d:/orignal.png"));
	}
	
	/*
	 * html5 로컬 스토리지 관련 테스트
	 * 기존에 클라이언트에 정보를 저장하려면 쿠키외에는 방법이 없었으나 html5에서는 로컬스토리지를 이용하여 데이터를 저장할 수 있다.
	 * 기존 쿠키와는 다르게 대용량의 데이터도 저장할 수 있으며 파일로 저장이 되지 않아 안전하게 사용할 수 있다.
	 * 
	 * 로컬스토리지 내용을 검색하려면 
	 * 크롬 - 개발자 도구에서 resource - local storage를 확인
	 * 파이어폭스 - 개발자 도구에서 storage 탭 - local storage를 확인 탭이 안보일 경우에는 개발자도구 상단 toolbox-opion에서 storage를 표시하도록 수정
	 * 
	 * 동일한 url(www.google.co.kr)을 크롭과 파이어폭스에서 실행시 로컬스토리지 내용이 다르게 표시가된다. 이게 맞는지는 확인이 필요
	 * 
	 * 한번 로컬스토리지에 저장된 데이터는 무한대로 저장된다. 단 아래와 같이 기존 서버에 스크립트를 삽입하는 방식으로는 브라우저를 끄면 로컬스토리지 내용이 서버에서 지정한 
	 * 내용으로 초기화된다.
	 */
	//@Test
	public void local_storage_test() {
		driver.get("http://localhost:10010");
		
		je.executeScript("localStorage.test = '123'"); //로컬 스토리지에 값을 입력

		assertEquals("123", je.executeScript("return localStorage.test")); //로컬 스토리지의 값 추출
	}
	
	/*
	 * 세션 스토리지 테스트
	 * 세션에 일정한 값을 저장하는것으로 브라우저를 닫으면 사라진다.
	 */
	//@Test
	public void session_storage_test() {
		driver.get("http://localhost:10010");
		String click_count = null;
		
		WebElement click = driver.findElement(By.id("click"));
		
		click_count = (String)je.executeScript("return sessionStorage.clickCount");
		
		assertEquals(null, click_count);
		
		click.click();
		
		click_count = (String)je.executeScript("return sessionStorage.clickCount");
		assertEquals("1", click_count);
	}
	
	/*
	 * 기존에 저장된 로컬스토리지 및 세션 스토리지를 초기화하는 테스트
	 */
	@Test
	public void clear_local_session_storage() {
		driver.get("http://localhost:10010");
		
		/*
		 * 특정한 값을 제거
		 */
		//je.executeScript("localStorage.removeItem('test')");
		//je.executeScript("sessionStorage.removeItem('clickCount')");
		
		/*
		 * 스토리지 전체를 초기화
		 */
		je.executeScript("localStorage.clear()");
		je.executeScript("sessionStorage.clear()");
	}
}


