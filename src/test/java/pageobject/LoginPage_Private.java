package pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/*
 * LoginPage와 달리 pageFactory에서 테스트에 필요한 기능을 수행
 * 검색기준
 * tag name or id=[변수명]
 */
public class LoginPage_Private {
	
	private WebDriver driver;
	private WebElement id;
	private WebElement pw;
	private WebElement submit;
	
	public LoginPage_Private(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void goUrl() {
		driver.get("http://localhost:8080/");
	}
	
	public void Login(String web_id, String web_pw) {
		
		id.clear();
		pw.clear();
		
		id.sendKeys(web_id);
		pw.sendKeys(web_pw);
		
		submit.click();
	}
}
