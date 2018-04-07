package gen_template.testcase;



import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import gen_template.search.AutoSearch;
import gen_template.search.login.TestLoginProc;
import util.Util;

/*
 * Selnium Template Generator 개발 테스트 케이스
 */
public class TemplateDevTestCase {
	private static WebDriver driver;
	private static JavascriptExecutor je;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = Util.getChromeDriver();
		//driver = Util.getIEDriver();
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
	
	public static String getXpathStr(WebElement el) {
		String tag_name = el.getTagName();
		String xpath_format = "//%1$s[@%2$s=\"%3$s\"]";
		
		/*
		 * a tag의 href에 #을 넣고 onclick 항목에 자바스크립트 코드를 넣는 경우 getAttribute("href")가 #으로 나오는 것이 아닌
		 * url주소/# 로 표시가 되기 때문에 onclick을 가장먼저 검색하도록 처리
		 */
		String[] attr_arr = {"onclick", "id", "name", "href"};
		
		String attr = null;
		String sel_attr = null;
		
		for(String val : attr_arr) {
			attr = el.getAttribute(val);
			if(attr != null && attr.trim().intern() != "".intern()) {
				sel_attr = val;
				break;
			}
		}
		
		return String.format(xpath_format, tag_name, sel_attr, attr);
	}
	
	/**
	 * 로그인이 존재하는 유형
	 * case1.로그인을 하지 않으면 전체기능에 접근이 불가능한 경우
	 * - index.jsp로 접근하면 로그인이 안된경우 자동으로 로그인 페이지로 이동하니 로그인하고 메인페이지로 이동하면 됨
	 * 
	 * case2.로그인을 하지 않으면 일부기능에 접근이 불가능한 경우
	 * - 로그인페이지 이동 및 로그인(특히 로그인 페이지 이동)을 특정하기가 불가능하기 때문에 홈페이지 별로 로그인페이지 이동로직을 정의해야 할 필요가 있음.
	 * 
	 * case3.로그인이 존재하지 않는 경우(단순 홈페이지성)
	 * - 2번과 비슷하게 로그인기능이 존재하지 않는다는것을 확인하기가 거의 불가능하기 때문에 검색시작시 존재하지 않는다는것을 정의하고 시작할 필요가 있음.
	 * 
	 * +
	 * 
	 * 검색도중 로그아웃을 수행하면 안되기 때문에 로그아웃버튼을 클릭하지 못하도록하는 기능이 필요
	 * 
	 * ==========================================================================================================
	 * 
	 * case1 - index.jsp 호출 시 나오는 페이지를 로그인 페이지인지 selenium상에서 판단이 가능한가?
	 * 	--> input text개수,input text name으로 판단하기에는 무리가 따름
	 * 	--> case2의 경우도 로그인 페이지 이동 및 로그인을 selenium상에서 자동으로 하게 하는건 무리가 있음
	 * 	--> case3도 로그인페이지가 없다는것을 자동으로 인지하지 못함
	 * 	결론 : 소프트웨어 상에서 자동으로 검색 하는건 불가능하며 각 case별로 사용자가 지정을 해야 함.
	 * 	Interface + imlements에서 사용자가 직접 로그인을 처리하도록 지정
	 * 
	 * case1~3 모두 로그인 수행후 이동하는 페이지를 root-node로 지정
	 * 
	 * 
	 * 
	 */
	@Test
	public void test() {
		
		driver.get("http://localhost:8080");
		
		/**/
		WebElement id = driver.findElement(By.cssSelector("#id"));
		WebElement pw = driver.findElement(By.cssSelector("#pw"));
		WebElement submit = driver.findElement(By.cssSelector("input[type='submit']"));
		
		id.sendKeys("user");
		pw.sendKeys("user");
		submit.click();
		
		WebDriverWait wdw = new WebDriverWait(driver,10);
		wdw.until(ExpectedConditions.titleContains("List"));
		
		root_node test = new root_node(driver);
		test.get();
		test.load();
		
		/*
		List<WebElement> list = driver.findElements(By.cssSelector("a,input[name='submit'],input[type='button'],input[type='image']"));
		for(WebElement el : list) {
			//getAttribute는 대소문자 구분을 가리지 않음.(해당 tag의 name 속성이 Name 이나 nAme으로 되어도 정상적으로 검출함
			System.out.println(el.getTagName()+"=="+el.getAttribute("id")+"=="+el.getAttribute("name")+"=="+el.getAttribute("type")+"=="+el.getAttribute("href")+"=="+el.getAttribute("onclick"));
			System.out.println(getXpathStr(el));
		}
		
		//WebElement test = driver.findElement(By.xpath("//input[@onclick=\"javascript:alert('sdafdsafasd')\"]"));
		//System.out.println(test.getTagName()+"=="+test.getAttribute("id")+"=="+test.getAttribute("name")+"=="+test.getAttribute("type"));
		*/
	}

	//@Test
	public void test1() throws Exception{
		By css_selector = By.cssSelector("a,input[name='submit'],input[type='button'],input[type='image']");
		
		/*
		//root-node를 수동으로 입력
		ElementData el_data = new ElementData();
		el_data.setUrl(driver.getCurrentUrl());
		Tree tree = new Tree();
		tree.addNode("root-node").setAttach(el_data);
		*/
		AutoSearch.searchClickableElement(css_selector, null, driver, new TestLoginProc());
	}
	
	//@Test
	//자동 진행에서 생성된 pageobject소스 검증 테스트
	public void test4() throws Exception{
		root_node test = new root_node(driver);
		
		Object[] t = new Object[1];
		test.exec(t);
	}
	
	//@Test
	//다중 alert 처리 테스트
	public void test2() {
		String parent = driver.getWindowHandle();
		System.out.println("Handle1 : "+parent);
		
		WebElement test = driver.findElement(By.cssSelector("a[id=aaa1s]"));
		test.click();
		
		try {
			/*
			 * 한 기능(버튼클릭)시 10개 정도의 팝업을 띄우는 케이스는 없다고 가정하고 처리
			 */
			for(int i = 0 ; i<10 ; i++) {
				Alert alert = driver.switchTo().alert();
				System.out.println("For Handle : "+driver.getWindowHandle());
				alert.accept();
				driver.switchTo().window(parent); //alert을 닫은후 포커스를 다시 부모창으로 이동해야 다음 alert을 처리할수 있음.
			}
		}catch(NoAlertPresentException nape) {
			System.out.println("No Alert Presented");
		}finally {
			driver.switchTo().window(parent);
		}
		
		driver.switchTo().window(parent); //alert창으로 포커스를 옮겨서 테스트를 진행한 후에 다음테스트를 진행하기 위해서 포커스를 다시 부모창으로 이동
		
		WebDriverWait wdw = new WebDriverWait(driver, 10);
		wdw.until(ExpectedConditions.elementToBeClickable(By.tagName("body")));
		
		WebElement test2 = driver.findElement(By.xpath("//a[@href=\"/logout.do\"]"));
		test2.click();
	}
	
	//@Test
	//Confirm관련 테스트
	public void test3() {
		try {
			String parent = driver.getWindowHandle();
			System.out.println("prev : "+parent);
			
			WebElement test = driver.findElement(By.cssSelector("input[id=test2]"));
			test.click();
			
			Alert a = driver.switchTo().alert();
			
			a.accept(); //OK버튼 클릭
			//a.dismiss(); //cancel버튼 클릭 
			
			System.out.println(driver.getWindowHandle());
			
			driver.switchTo().window(parent); //alert창으로 포커스를 옮겨서 테스트를 진행한 후에 다음테스트를 진행하기 위해서 포커스를 다시 부모창으로 이동
			
			WebElement test2 = driver.findElement(By.xpath("//a[@href=\"/logout.do\"]"));
			test2.click();
		}catch(NoAlertPresentException e) {
			e.printStackTrace();
		}
	}
}
