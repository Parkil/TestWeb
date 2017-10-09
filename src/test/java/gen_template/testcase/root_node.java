
package gen_template.testcase;

import org.junit.Assert;
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
public class root_node
    extends LoadableComponent<root_node>
{

    private WebDriver driver;
    private String url = "http://localhost:8080/sample/egovSampleList.do";
    @FindBy(xpath = "//a[@href=\"javascript:aaa()\"]")
    private WebElement btn1;
    @FindBy(xpath = "//a[@href=\"javascript:bbb()\"]")
    private WebElement btn2;
    @FindBy(xpath = "//a[@href=\"javascript:fn_egov_selectList();\"]")
    private WebElement btn3;
    @FindBy(xpath = "//a[@href=\"javascript:fn_egov_select('SAMPLE-00001')\"]")
    private WebElement btn4;
    @FindBy(xpath = "//a[@href=\"javascript:fn_egov_addView();\"]")
    private WebElement btn5;

    public root_node(WebDriver driver) {
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
        //insert detail test code here
    	btn1.click();
    	btn2.click();
    	btn3.click();
    	btn4.click();
    	
    	root_node_0_4 second = new root_node_0_4(driver); //수정화면
    	second.exec(params);
    	
    	btn5.click();
    	root_node_0_10 third = new root_node_0_10(driver); //등록화면
    	third.exec(params);
    	
    	//WebElement list_btn = driver.findElement(By.xpath("//a[@id=\"list_btn\"]"));
    	//list_btn.click();
    	
    	//btn5.click();
    }

}
