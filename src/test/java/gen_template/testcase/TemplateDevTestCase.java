package gen_template.testcase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import gen_template.search.AutoSearch;
import gen_template.search.login.TestLoginProc;
import util.Util;

/*
 * Selnium Template Generator 개발 테스트 케이스
 */
public class TemplateDevTestCase {
	private static WebDriver driver;
	//private static JavascriptExecutor je;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = Util.getChromeDriver();
		//driver = Util.getIEDriver();
		//je = (JavascriptExecutor)driver;
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
	 */
	@Test
	public void autoSearchTest() throws Exception{
		By css_selector = By.cssSelector("a,input[name='submit'],input[type='button'],input[type='image']");
		AutoSearch.searchClickableElement(css_selector, null, driver, new TestLoginProc());
	}
}
