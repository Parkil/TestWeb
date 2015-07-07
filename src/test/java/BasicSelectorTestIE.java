import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/*
 * Selenium의 기본 선택자를 이용하여 페이지의 기본 요소를 선택하는 테스트
 */
public class BasicSelectorTestIE {

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

	@Test
	public void test() {
		File file = new File("d:/selenium/IEDriverServer_Win32_2.46.0/IEDriverServer.exe"); //ieDriver 설정 크롬의 경우에는 크롬드라이버위치를 파이어폭스의 경우에는 path에 파이어폭스가 잡혀있어야 한다.
		System.setProperty("webdriver.ie.driver", file.getAbsolutePath()); //ie Driver를 쓰기 위해서 해당 System property를 설정해 주어야 함
		
		WebDriver driver = new org.openqa.selenium.ie.InternetExplorerDriver();
		driver.get("http://localhost:10010");
		
		//html id속성으로  웹 요소를 가져옴
		WebElement id = driver.findElement(By.id("id"));
		WebElement pw = driver.findElement(By.id("pw"));
		
		//html name속성으로 웹 요소를 가져옴
		WebElement id_byname = driver.findElement(By.name("id"));
		WebElement pw_byname = driver.findElement(By.name("pw"));
		
		//html class="~" 속성으로 웹 요소를 가져옴
		WebElement id_byclass = driver.findElement(By.className("testid"));
		WebElement pw_byclass = driver.findElement(By.className("testpw"));
		
		//해당하는 전체 웹 요소를 리스트로 가져온다
		List<WebElement> list = driver.findElements(By.tagName("input"));
		
		WebElement submit = driver.findElement(By.name("submit"));
		
		/*
		 * cssSelector를 이용하여 웹 요소를 선택
		 * 
		 * form table tr - 부모 -> 자식 순으로 배열한다 form > table > tr 이런식으로 명시적으로 처리하는것도 가능
		 * + -> 동일한 레벨의 형제tag를 반환 form table tr+tr - table의 첫번째 tr tag의 형제 tag 즉 두번째 tr을 가리킨다.
		 */
		WebElement css_id = driver.findElement(By.cssSelector("form table tr td+td input"));
		WebElement css_pw = driver.findElement(By.cssSelector("form table tr+tr td+td input"));
		
		
		assertEquals(list.size() , 3);
		assertEquals(id.getText(), "");
		assertEquals(pw.getText(), "");
		assertEquals(id_byname.getText(), "");
		assertEquals(pw_byname.getText(), "");
		assertEquals(id_byclass.getText(), "");
		assertEquals(pw_byclass.getText(), "");
		
		assertEquals(css_id.getText(), "");
		assertEquals(css_pw.getText(), "");
		
		submit.click(); //a 나 button,submit요소의 경우 WebElement를 얻은뒤 click을 선언하여 해당 버튼이나 링크를 클릭하게 만들수 있다.
		//fail("Not yet implemented");
	}

}
