import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageobject.LoginPage;
import pageobject.LoginPage_Loadable;
import pageobject.LoginPage_Private;
import util.Util;

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
		driver.close();
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
	public void test4() {
		LoginPage_Loadable load = new LoginPage_Loadable(driver);
		load.get();
		load.Login("user2", "user2");
	}
	
	@Test
	public void test5() {
		WebDriverWait wdw = new WebDriverWait(driver, 10);
		wdw.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='id']")));
		
		assertEquals("http://localhost:10010", driver.getCurrentUrl());
	}
}
