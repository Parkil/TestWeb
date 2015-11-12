import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageobject.LoginPage;
import pageobject.LoginPage_Loadable;
import pageobject.LoginPage_Private;
import util.JQueryUITab;
import util.Util;
import util.WebTable;

/*
 * PageFactory관련 테스트 케이스
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestIE_case4 {
	private static WebDriver driver;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = Util.getIEDriver();
		driver.get("http://localhost:10010");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//driver.close();
	}

	@Before
	public void setUp() throws Exception {
	}

	//@Test
	public void test1() {
		LoginPage lp = new LoginPage(driver); //pagefactory를 이용하여 페이지와 WebElement를 Mapping한다.
		
		WebDriverWait wdw = new WebDriverWait(driver, 10);
		wdw.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='id']")));
		
		//Mapping한 pagefactory를 이용하여 테스트를 진행
		lp.id.clear();
		lp.pw.clear();
		lp.id.sendKeys("user1");
		lp.pw.sendKeys("user1");
		lp.submit.click();
		
		assertEquals(1, 1);
	}
	
	//@Test
	public void test2() {
		LoginPage_Private pri = new LoginPage_Private(driver);
		pri.goUrl();
		pri.Login("user2", "user2");
	}
	
	@Test
	public void test3() {
		LoginPage_Loadable load = new LoginPage_Loadable(driver);
		load.get();
		load.Login("user", "user");
	}
	
	@Test
	public void test4() {
		WebDriverWait wdw = new WebDriverWait(driver, 10);
		wdw.until(ExpectedConditions.titleContains("Basic Board List"));
		
		assertEquals("http://localhost:10010/sample/egovSampleList.do", driver.getCurrentUrl());
		
		WebElement table = driver.findElement(By.tagName("table"));
		WebTable w_table = new WebTable(table);
		
		System.out.println(w_table.getCellContents(2, 2));
		
		assertEquals(7, w_table.getRowCount());
		assertEquals(6, w_table.getColCount());
	}
	
	//@Test
	public void test5() {
		driver.get("http://dl.dropbox.com/u/55228056/jQueryUITabDemo.html");
		
		WebDriverWait wdw = new WebDriverWait(driver, 10);
		wdw.until(ExpectedConditions.titleContains("jQuery UI Tabs"));
		
		try {
			JQueryUITab tab = new JQueryUITab(driver.findElement(By.cssSelector("div[id=MyTab][class^=ui-tabs]")));
			
			assertEquals(3, tab.getTabCount());
			assertEquals("Home", tab.getSelectedTab());
			
			tab.selectTab("Options");
			assertEquals("Options", tab.getSelectedTab());
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
