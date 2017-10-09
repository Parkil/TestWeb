package pageobject;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

/*
 * LoadableComponent를 이용한 pageFactory 구현
 * 
 * 기존 PageFactroy에서 테스트에 필요한 페이지가 정상적으로 load되었는지 확인하는 기능이 추가됨
 */
public class LoginPage_Loadable extends LoadableComponent<LoginPage_Loadable>{
	
	@FindBy(id = "id")
	private WebElement id;
	
	@FindBy(id = "pw")
	private WebElement pw;
	private WebElement submit;
	
	@FindBy(xpath = "//a[@onclick=\"javascript:alert('test12345')\"]")
	private WebElement test;
	
	private WebDriver driver;
	
	private String url = "http://localhost:8080";
	
	public LoginPage_Loadable(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	/*
	 * 호출하는 쪽에서는 new LoginPage_Loadable().get()으로 호출하면 해당 메소드가 호출된다.
	 */
	@Override
	protected void isLoaded() throws Error {
		driver.get(url);
	}
	
	/*
	 * 해당페이지가 정상적으로 로딩되었는지 확인
	 */
	@Override
	protected void load() {
		assertEquals("Insert Title Here", driver.getTitle());
	}
	
	public void Login(String web_id, String web_pw) {
		id.clear();
		pw.clear();
		
		id.sendKeys(web_id);
		pw.sendKeys(web_pw);
		test.click();
		submit.click();
	}
}
