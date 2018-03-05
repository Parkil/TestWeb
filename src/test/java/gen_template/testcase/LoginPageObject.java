
package gen_template.testcase;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;


/**
 * Generated Class.
 * 
 */
public class LoginPageObject
    extends LoadableComponent<LoginPageObject>
{

    private WebDriver driver;
    private String url = "http://localhost:8080/login.do";
    private static GetDataCollection col = new GetDataCollection();
    
    @FindBy(css = "input[type='submit']")
    private WebElement submit;
    
    @FindBy(css = "#id")
    private WebElement input_id;
    
    @FindBy(css = "#pw")
    private WebElement input_pw;
    
    private String id;
	private String pw;
	
	public LoginPageObject(WebDriver driver, String id, String pw) {
    	this.id = id;
    	this.pw = pw;
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Override
    protected void isLoaded()
        throws Error
    {
        driver.get(url);
    }

    @Override
    protected void load() {
        Assert.assertEquals("Insert Title Here", driver.getTitle());
    }

    public void exec(Object... params) {
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //묵시적대기적용(무조건 3초를 대기)
		
		input_id.clear();
		input_id.sendKeys(id);
		
		input_pw.clear();
		input_pw.sendKeys(pw);
		submit.click();
		
		//System.out.println(error.getText());
		
		assertEquals("user", id);
    }
}
