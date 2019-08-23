import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
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
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.os.WindowsUtils;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import util.CompareUtil;
import util.ObjectMap;
import util.WebElementExpand;

/*
 * 테스트케이스 실행시 원래는 순서기 Junit 임의대로 실행된다. 이를 특정순서대로 표시하고 싶으면 
 * @FixMethodOrder를 정의하면 된다.
 * @FixMethodOrder(MethodSorters.NAME_ASCENDING) - 메소드명 이름을 오름차순으로 정렬
 * 
 * 다른것도 있는거 같은데 아직 확인은 못함
 * 
 * 2015-09-15 추가확인
 * 각 테스트케이스를 실행할때마다 생성자가 호출되는것이 확인되었고 non-static 변수의 경우 테스트케이스가 끝날때마다 
 * 초기화가 되는것을 확인함
 * 
 * 테스트케이스 전체에서 공통적으로 사용하고자 하는 변수는 static으로 선언할것
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestIE_case1 {
	private static WebDriver driver;
	private static JavascriptExecutor je;
	private static ObjectMap map;
	
	private static long startTime = 0L;
	private static long endTime = 0L;

	/*
	 * 해당클래스가 시작전 실행될 내용을 정의
	 * - 테스트케이스개수와 상관없이 테스트케이스 클래스별로 1번만 실행
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("setUpBeforeClass");
		
		map = new ObjectMap("C:/Dev/eclipse/workspace/TestWeb/src/test/java/util/objectmap.properties");
		
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
		
		//driver.close();
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
	public void css_width_test() {
		WebElement target = driver.findElement(By.cssSelector("form table tr"));
		assertEquals("150px", target.getCssValue("width"));
	}
	
	
	
	
	@Test
	public void login_test() throws Exception{
		InjectJQuery ij = new InjectJQuery();
		ij.injectJQueryIfNone(je);
		
		//javasciptExecutor를 이용하여 대상요소에 값을 입력
		/*
		je.executeScript("$('#id').val('user')");
		je.executeScript("$('#pw').val('user');");
		*/
		
		//WebElement 확장 클래스를 이용하여 대상요소에 값을 입력
		/*
		WebElement id = driver.findElement(By.cssSelector("#id"));
		WebElement pw = driver.findElement(By.cssSelector("#pw"));
		
		WebElementExpand.setAttribute(id, "value", "user1");
		WebElementExpand.setAttribute(pw, "value", "user1");
		
		WebElementExpand.highlightElement(id);
		*/
		
		/*
		 * ObjectMap을 이용하여 대상요소를 지정하고 값을 입력
		 * 테스트케이스가 여러개 작성되면 By.~ 로시작되는 위치정보가 중복되기 마련이고 
		 * 이 위치정보가 웹어플에서 수정되면 모든 코드의 데이터를 다 수정해야 하는 문제가 발생한다.
		 * 이를 막기 위해 ObjectMap에서 모든 위치정보를 관리하도록 처리한다.
		 */
		
		
		WebElement id = driver.findElement(map.getLocator("id"));
		WebElement pw = driver.findElement(map.getLocator("pw"));
		
		id.clear();
		pw.clear();
		
		id.sendKeys("user");
		pw.sendKeys("user");
		
		WebElement submit = driver.findElement(By.cssSelector("input[type='submit']"));
		
		FileUtils.copyFile(WebElementExpand.captureElement(submit), new File("d:/aaa.png"));
		
		//actions를 이용한 화면 요소 클릭 여러개의 action을 선택한 후에 build().perform()을 호출하면 된다.
		Actions action = new Actions(driver);
		action.click(submit);
		action.build().perform();
		
		startTime = System.currentTimeMillis();
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
	}
	
	//Drag&Drop test
	@Test
	public void submit_after_test2() {
		/*
		 * 웹이 url을 이동하는 과정에서 해당 테스트가 실행되는 시점에서 화면 load가 안되어 테스트가 실패할 경우가 있다.
		 * 이를 방지하기 위하여 화면 load가 될때까지 기다리기 위해 wait를 설정할 수 있다.
		 * wait에는 묵시적,명시적이 있는데 묵시적적용은 dom의 웹 요소를 찾을때마다 지정한 시간만큼 대기하기 때문에
		 * 많이 쓰게 되면 테스트실행시간이 늘어나는 문제가있다. 되도록이면 사용하지 말것
		 */
		//driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS); //묵시적 wait
		
		/*
		 * 명시적 wait 기본시간을 설정하고 until에서 지정한 조건이 충족되면 바로 다음 코드를 실행한다.
		 * 지정된 시간이 지나도 조건이 충족되지 않을때에는 예외가 발생한다.
		 */
		WebDriverWait wdw = new WebDriverWait(driver, 10); //기본시간 설정(단위 초)
		wdw.until(ExpectedConditions.titleContains("Basic Board List")); //title명이 Basic Board List가 나타날때까지 대기
		
		endTime = System.currentTimeMillis();
		
		System.out.println("endTime : "+endTime);
		System.out.println("startTime : "+startTime);
		System.out.println("로그인후 화면까지 표시되는 시간 : "+(endTime - startTime)+" milliseconds");
		
		//until조건을 사용자가 정의하여 사용하는 방법 - 해당 웹 요소가 반환될때 까지 대기
		WebElement start = (new WebDriverWait(driver, 10).until(
			new ExpectedCondition<WebElement>() {
				@Override
				public WebElement apply(WebDriver d) {
					return d.findElement(By.cssSelector("body tr th"));
				}
			}
		));
		
		//until조건을 사용자가 정의하여 사용하는 방법 - 해당 웹 요소가 화면에 표시될때까지 대기
		wdw.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver d) {
				return d.findElement(By.cssSelector("body tr th+th+th+th+th+th")).isDisplayed();
			}
		});
		
		WebElement end	 = driver.findElement(By.cssSelector("body tr th+th+th+th+th+th"));
		
		Actions action = new Actions(driver);
		action.dragAndDrop(start, end);
		action.perform();
	}
	
	//Screenshot test
	@Test
	public void submit_after_test3() {
		File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(file, new File("d:/screen1.png"));
			FileUtils.copyFile(file, new File("d:/screen2.png"));
			
			assertEquals(CompareUtil.Result.PixcelMismatch, CompareUtil.CompareImage("d:/screen1.png", "d:/screen2.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//window size maximize
	@Test
	public void submit_after_test4() {
		driver.manage().window().maximize();
	}
	
	/*
	 * IeTestDriver가 여러개 떠있을 경우 JavaScript function을 찾지 못하는 문제가 발생하는 경우가 있다 주의할것
	 */
	//select-list test
	@Test
	public void submit_after_test5() {
		Select sel = new Select(driver.findElement(By.cssSelector("#searchCondition"))); //select list 선택
		
		assertFalse(sel.isMultiple()); //select list가 다중선택이 가능한지 확인
		
		sel.selectByIndex(1); //select list의 요소를 index기준으로 선택(동적으로 구성되는 select list의 경우 index마다 값이 다를수 있으니 주의)
		sel.selectByValue("1"); //select list의 요소를 option value값을 기준으로 선택
		sel.selectByVisibleText("Name"); //select list의 요소를 화면에 표시되는 text 기준으로 선택
		
		WebElement search_btn = driver.findElement(By.cssSelector(".btn_blue_l a"));
		JavascriptExecutor je = (JavascriptExecutor)driver;
		je.executeScript("$('#searchKeyword').val('00006')");
		
		search_btn.click();
	}
	
	//multi select test
	//@Test
	public void submit_after_test6() {
		Select sel = new Select(driver.findElement(By.cssSelector("#multisel")));
		
		assertTrue(sel.isMultiple());
		
		//선택하는 부분은 일반 select와 동일함
		sel.selectByVisibleText("Volvo");
		sel.selectByVisibleText("Saab");
		sel.selectByVisibleText("Opel");
		
		//해당되는 option을 비선택 처리
		sel.deselectByVisibleText("Saab");
	}
	
	//select option check test
	@Test
	public void submit_after_test7() {
		Select sel = new Select(driver.findElement(By.cssSelector("#searchCondition")));
		
		//select option의 값 배열에 저장
		List<String> temp = new ArrayList<String>();
		for(WebElement val : sel.getOptions()) {
			temp.add(val.getText());
		}
		
		//예상되는 select option의 값
		String[] chk_sel_option = {"Name","ID111"};
		
		assertArrayEquals(temp.toArray(), chk_sel_option);
	}
	
	//현재 선택된 select option 체크
	@Test
	public void submit_after_test8() {
		Select sel = new Select(driver.findElement(By.cssSelector("#searchCondition")));
		assertEquals("Name", sel.getFirstSelectedOption().getText());
	}
	
	//radio button test(여기서는 구현하지 않았으나 체크박스도 동일한 로직으로 작동한다 체크박스 테스트 구현시에는 아래 코드에서 webelement만 체크박스요소로 가져올것)
	//@Test
	public void sumbit_after_test91() {
		//radio button 요소 추출
		WebElement target = driver.findElement(By.cssSelector("input[name='rdr'][value='1']"));
		
		/*
		if(target instanceof HtmlUnitWebElement) {
			System.out.println("HtmlUnitWebElement");
		}else if(target instanceof RemoteWebElement) {
			System.out.println("RemoteWebElement");
		}*/
		
		//해당 라디오 버튼이 선택되지 않았으면 선택처리
		if(!target.isSelected()) {
			target.click();
		}
		
		//해당 radio button이 선택되었는지 확인
		assertTrue(target.isSelected());
		assertTrue(target.isEnabled());
		
		//전체 라디오버튼 요소를 가져와서 해당 라디오버튼이 클릭 되었는지 확인
		for(WebElement we : driver.findElements(By.cssSelector("input[name='rdr']"))) {
			assertEquals("1", we.getAttribute("value"));
		}
	}
	
	/*
	 * 팝업관련 테스트
	 */
	//@Test
	public void submit_after_test92() {
		WebElement element = driver.findElement(By.cssSelector("#popup"));
		element.click(); //팝업표시
		
		String parentWindowid = driver.getWindowHandle();
		System.out.println("parentWindowid : "+parentWindowid);
		
		driver.switchTo().window("google"); //window.open(url,'[name]'~ 에서 지정한 name속성에 해당하는 팝업으로 이동
		assertEquals("Google", driver.getTitle());
		
		driver.switchTo().window("daum");
		assertEquals("Daum – 모으다 잇다 흔들다", driver.getTitle());
		
		Set<String> allWindows = driver.getWindowHandles();
		
		//전체 창 중에서 타이틀을 기준으로 팝업창을 겁색 
		for(String wid : allWindows) {
			if(driver.switchTo().window(wid).getTitle().equals("Google")) {
				System.out.println("구글 윈도우");
			}
			
			if(driver.switchTo().window(wid).getPageSource().indexOf("google") != -1) {
				System.out.println("구글윈도우(페이지 소스로 검색)");
			}
		}
		
		driver.switchTo().window(parentWindowid); //부모창으로 다시 이동함;
	}
	
	//window process kill test(여러개의 동일한 프로세스가 떠 있는 경우 떠 있는 모든 프로세스를 kill한다.)
	@Test
	public void submit_after_test993() {
		WindowsUtils.killByName("IEDriverServer.exe");
	}
}
