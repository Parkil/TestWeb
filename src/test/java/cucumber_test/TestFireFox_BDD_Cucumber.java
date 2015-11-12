package cucumber_test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageobject.LoginPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import util.Util;

/*
 * Cucumber-jvm을 이용한 BDD 테스트
 */
public class TestFireFox_BDD_Cucumber {
	private static WebDriver driver;
	private static LoginPage login_page = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver	= Util.getFireFoxDriver();
		login_page = new LoginPage(driver);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.close();
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("before");
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Given("^로그인 화면으로 이동한다\\.$")
	public void 로그인_화면으로_이동한다() throws Throwable {
		driver	= Util.getFireFoxDriver();
		login_page = new LoginPage(driver);
		
		driver.get("http://localhost:10010");
		
		WebDriverWait wdw = new WebDriverWait(driver, 10);
		wdw.until(ExpectedConditions.titleContains("Insert title here"));
	}

	@When("^아이디 \"([^\"]*)\"을 입력한다\\.$")
	public void 아이디_을_입력한다(String id) throws Throwable {
		login_page.id.sendKeys(id);
	}

	@When("^패스워드 \"([^\"]*)\"을 입력한다\\.$")
	public void 패스워드_을_입력한다(String pw) throws Throwable {
		login_page.pw.sendKeys(pw);
	}

	@When("^로그인버튼을 클릭한다\\.$")
	public void 로그인버튼을_클릭한다() throws Throwable {
		login_page.submit.click();
	}

	@Then("^로그인 실패 페이지를 확인한다\\.$")
	public void 로그인_실패_페이지를_확인한다() throws Throwable {
		System.out.println(driver.getCurrentUrl());
		assertEquals(24, driver.getCurrentUrl().indexOf("login.do"));
	}


	/*
	@Given("로그인 페이지로 이동한다.")
	public void The_user_is_on_fund_transfer_page() {
		
		
	}
	
	@When("ID \"([^\"*])\"를 입력한다.")
	public void input_id(String id) {
		
	}
	
	@And("PW \"([^\"*])\"를 입력한다.")
	public void input_pw(String pw) {
		
	}
	
	@And("로그인 버튼을 클릭한다.")
	public void click_submit(String pw) {
		
	}
	
	@Then("로그인 실패 메시지가 표시되는것을 확인한다.")
	public void login_fail() {
		assertEquals(23, driver.getCurrentUrl().indexOf("login.do"));
	}
	
	@Then("리스트 페이지로 이동하는것을 확인한다.")
	public void login_success() {
		assertEquals(-1, driver.getCurrentUrl().indexOf("login.do"));
	}*/
}
