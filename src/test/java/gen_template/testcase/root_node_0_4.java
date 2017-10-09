
package gen_template.testcase;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Generated Class.
 * 
 */
public class root_node_0_4
    extends LoadableComponent<root_node_0_4>
{

    private WebDriver driver;
    private String url = "http://localhost:8080/sample/updateSampleView.do";
    @FindBy(xpath = "//a[@id=\"list_btn\"]")
    private WebElement btn1;
    @FindBy(xpath = "//a[@onclick=\"javascript:fn_egov_save();\"]")
    private WebElement btn2;
    @FindBy(xpath = "//input[@onclick=\"javascript:fn_egov_delete();\"]")
    private WebElement btn3;
    @FindBy(xpath = "//a[@href=\"javascript:document.detailForm.reset();\"]")
    private WebElement btn4;

    public root_node_0_4(WebDriver driver) {
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
    protected void load() { //load조건을 자동으로 처리하도록 수정필요
        Assert.assertEquals("Sample 수정", driver.getTitle());
    }

    public void exec(Object... params) {
       btn1.click();
        
       WebElement list_btn = driver.findElement(By.xpath("//a[@href=\"javascript:fn_egov_select('SAMPLE-00001')\"]"));
       list_btn.click();
       
       WebDriverWait wdw = new WebDriverWait(driver,10);
       wdw.until(ExpectedConditions.titleContains("Sample 수정"));
       btn2.click();
       
       list_btn = driver.findElement(By.xpath("//a[@href=\"javascript:fn_egov_select('SAMPLE-00001')\"]"));
       list_btn.click();
       
       wdw.until(ExpectedConditions.titleContains("Sample 수정"));
       btn3.click(); //삭제로직이기 때문에 더이상 진행이 안되어 주석처리함
       //btn4.click();
    }

}
