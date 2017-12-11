package gen_template.search.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import gen_template.search.ElementData;
import gen_template.tree.Tree;

public class TestLoginProc implements ExecLoginProc {

	@Override
	public void exec_login(WebDriver driver, Object... params) throws Exception {
		driver.get("http://localhost:8081");
		WebElement id = driver.findElement(By.cssSelector("#id"));
		WebElement pw = driver.findElement(By.cssSelector("#pw"));
		WebElement submit = driver.findElement(By.cssSelector("input[type='submit']"));
		
		id.sendKeys("user");
		pw.sendKeys("user");
		submit.click();
		
		WebDriverWait wdw = new WebDriverWait(driver,10);
		wdw.until(ExpectedConditions.titleContains("List"));
		
		//root-node를 수동으로 입력
		ElementData el_data = new ElementData();
		el_data.setUrl(driver.getCurrentUrl());
		Tree tree = (Tree)params[0];
		tree.addNode("root-node").setAttach(el_data);
	}

	@Override
	public boolean is_logout_btn(WebDriver driver, Object... params) throws Exception {
		WebElement el = (WebElement)params[0];
		return (el.getText().intern() == "로그아웃".intern() || el.getText().intern() == "history.back()".intern());
	}
}
