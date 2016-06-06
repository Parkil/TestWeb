package gen_template;


import static org.junit.Assert.assertEquals;

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

import gen_template.tree.Tree;
import gen_template.util.GenTemplateUtil;
import util.InjectJQuery;
import util.Util;

public class Test1 {
	private static WebDriver driver;
	private static JavascriptExecutor je;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = Util.getChromeDriver();
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
	
	
	/**
	 * 로그인
	 */
	@Test
	public void test() {
		InjectJQuery ij = new InjectJQuery();
		ij.injectJQueryIfNone(je);
		
		driver.get("http://localhost:8082");
		
		WebElement id = driver.findElement(By.cssSelector("#id"));
		WebElement pw = driver.findElement(By.cssSelector("#pw"));
		WebElement submit = driver.findElement(By.cssSelector("input[type='submit']"));
		
		id.sendKeys("user");
		pw.sendKeys("user");
		submit.click();
		
		WebDriverWait wdw = new WebDriverWait(driver,10);
		wdw.until(ExpectedConditions.titleContains("List"));
	}

	@Test
	public void test1() throws Exception{
		By a_tag = By.tagName("a");
		
		//root-node를 수동으로 입력
		ElementData el_data = new ElementData();
		el_data.setXpath("root");
		el_data.setUrl(driver.getCurrentUrl());
		Tree tree = new Tree();
		tree.addNode("root-node").setAttach(el_data);
		
		GenTemplateUtil.searchClickableElement(a_tag, tree, driver);
		
		tree.display("root-node");
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
