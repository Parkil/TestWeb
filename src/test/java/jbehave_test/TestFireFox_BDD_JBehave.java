package jbehave_test;

import static org.junit.Assert.*;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageobject.LoginPage;
import util.Util;

/*
 * jbehave 테스트 케이스
 * jbehave 설정 클래스를 상속받아야 하며 story(cucumberjvm의 feature파일)파일의 기본경로는 src/test/resources/[해당 클래스가 있는 패키지명]/[클래스명].story가 된다.
 * 대문자는 [소문자_]로 변환한다는 것에 주의
 */
public class TestFireFox_BDD_JBehave extends JBehaveTest{
	private static WebDriver driver;
	private static LoginPage login_page = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Given("로그인 화면으로 이동한다.")
	public void given로그인화면으로이동한다() {
		driver	= Util.getFireFoxDriver();
		login_page = new LoginPage(driver);
		
		driver.get("http://localhost:10010");
		
		WebDriverWait wdw = new WebDriverWait(driver, 10);
		wdw.until(ExpectedConditions.titleContains("Insert title here"));
	}

	@When("아이디 '$id'을 입력한다.")
	public void when아이디User111을입력한다(String id) {
		login_page.id.sendKeys(id);
	}

	@When("패스워드 '$pw'을 입력한다.")
	public void when패스워드User111을입력한다(String pw) {
		login_page.pw.sendKeys(pw);
	}

	@When("로그인버튼을 클릭한다.")
	public void when로그인버튼을클릭한다() {
		login_page.submit.click();
	}

	@Then("로그인 실패 페이지를 확인한다.")
	public void then로그인실패페이지를확인한다() {
		System.out.println(driver.getCurrentUrl());
		assertEquals(24, driver.getCurrentUrl().indexOf("login.do"));
	}
}
