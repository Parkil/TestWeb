package pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/*
 * page factory 클래스 
 */
public class LoginPage {
	
	/*
	 * WebElement의 변수명은 해당 요소의 name이나 id명으로 설정한다.
	 * 한 요소에 id,pw가 달라도 1개만 변수명과 일치하면 해당 요소가 mapping된다.
	 * ex)
	 * <input type='text' name='id' id='id'/> -> WebElement id;
	 * <input type='text' name='pw' id='pw'/> -> WebElement pw;
	 * 
	 */
	public WebElement id;
	
	/*
	 * 변수명으로 mapping을 하지 않고 FindBy 어노테이션을 이용하여 직접  요소와 WebElement를 mapping한다.
	 */
	@FindBy(id = "pw")
	public WebElement pw;
	
	/*
	 * FindBy 어노테이션은 WebElement객체에서 메소드를 호출시마다 해당 요소를 검색하게 된다.
	 * Ajax나 다른 방식으로 동적으로 구성되는 요소가 아니면 구태여 호출시마다 요소를 검색할 필요가 
	 * 없으므로 CacheLookup어노테이션을 이용하여 한번 검색한 요소를 caching처리한다.
	 */
	@FindBy(name = "submit")
	@CacheLookup
	public WebElement submit;
	
	
	
	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this); //여기에서 해당 WebElement를 Mapping한다
	}
	
	
}
