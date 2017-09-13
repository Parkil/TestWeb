
package gen_template;

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
public class root_node_0_10
    extends LoadableComponent<root_node_0_10>
{

    private WebDriver driver;
    private String url = "http://localhost:8080/sample/addSampleView.do";
    @FindBy(xpath = "//a[@id=\"list_btn\"]")
    private WebElement btn1;
    @FindBy(xpath = "//a[@onclick=\"javascript:fn_egov_save();\"]")
    private WebElement btn2;
    @FindBy(xpath = "//a[@href=\"javascript:document.detailForm.reset();\"]")
    private WebElement btn3;

    public root_node_0_10(WebDriver driver) {
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
        btn1.click();
        
        WebElement list_btn = driver.findElement(By.xpath("//a[@href=\"javascript:fn_egov_addView();\"]"));
        list_btn.click();
        
        btn2.click();
    }

}
