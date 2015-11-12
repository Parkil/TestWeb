package util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;

/*
 * Jquery UI Tab 테스트를 위한 유틸리티 클래스
 */
public class JQueryUITab {
	private WebElement _jqueryUITab;
	
	public JQueryUITab(WebElement jQueryUITab) {
		setJQuqeryUITab(jQueryUITab);
	}
	
	public WebElement getJQueryUITab() {
		return _jqueryUITab;
	}
	
	public void setJQuqeryUITab(WebElement jQueryUITab) {
		_jqueryUITab = jQueryUITab;
	}
	
	public int getTabCount() {
		List<WebElement> tabs = _jqueryUITab.findElements(By.cssSelector(".ui-tabs-nav > li"));
		return tabs.size();
	}
	
	public String getSelectedTab() {
		WebElement tab = _jqueryUITab.findElement(By.cssSelector("uitabs-nav > li[class*='ui-tabs-selected']"));
		return tab.getText();
	}
	
	public void selectTab(String tabName) throws Exception {
		int idx = 0;
		boolean found = false;
		
		List<WebElement> tabs = _jqueryUITab.findElements(By.cssSelector(".ui-tabs-nav > li"));
		
		for(WebElement tab : tabs) {
			if(tabName.equals(tab.getText().toString())) {
				WrapsDriver wrap = (WrapsDriver) _jqueryUITab;
				
				JavascriptExecutor driver = (JavascriptExecutor)wrap.getWrappedDriver();
				driver.executeScript("jQuery(arguments[0]).tabs().tabs('select',arguments[1]);", _jqueryUITab, idx);
				found = true;
				break;
			}
			idx++;
		}
		
		if(found == false) {
			throw new Exception("Could not find tab '"+tabName+"'");
		}
	}
}
 