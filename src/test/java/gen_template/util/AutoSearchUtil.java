package gen_template.util;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import gen_template.ElementData;
import gen_template.tree.Node;

/*
 * AutoSearch 클래스에서 사용하는 유틸리티 클래스
 */
public class AutoSearchUtil {
	/** WebElement에서 xpath정보(By에서 사용)를 반환
	 * @param el WebElement
	 * @return xpath 선택 문자열
	 */
	public static String getXpathStr(WebElement el) {
		String xpath_str = null;
		
		String tag_name = el.getTagName();
		
		String xpath_format = "//%1$s[@%2$s=\"%3$s\"]";
		if(tag_name.equalsIgnoreCase("a")) {
			
			String attr_arr[] = {"href", "onclick", "onClick"};
			String attr = null;
			String sel_attr = null;
			
			for(String val : attr_arr) {
				attr = el.getAttribute(val);
				if(attr != null) {
					sel_attr = val;
					break;
				}
			}
			
			xpath_str = String.format(xpath_format, tag_name, sel_attr, attr);
		}else if(tag_name.equals("input")) {
			//나중에 처리
		}
		
		
		return xpath_str;
	}
	
	/**alert이나 confirm이 발생할 경우 ok버튼을 눌러서 pass하도록 처리 다중 alert도 처리가능
	 * @param driver
	 */
	public static String passAlertConfirm(WebDriver driver) {
		StringBuffer sb = new StringBuffer();
		String parent = driver.getWindowHandle();
		try {
			/*
			 * 한 기능(버튼클릭)시 10개 정도의 팝업을 띄우는 케이스는 없다고 가정하고 처리
			 */
			for(int i = 0 ; i<10 ; i++) {
				Alert alert = driver.switchTo().alert();
				sb.append(alert.getText());
				sb.append("\n===========\n");
				alert.accept();
				driver.switchTo().window(parent); //alert을 닫은후 포커스를 다시 부모창으로 이동해야 다음 alert을 처리할수 있음.
			}
		}catch(NoAlertPresentException nape) {
			//System.out.println("No Alert Presented");
		}finally {
			driver.switchTo().window(parent);
		}
		
		return sb.toString();
	}
	
	/** 해당 node에 해당하는 url로 이동
	 * @param to_root_list
	 * @param driver
	 * @param wdw
	 * @return
	 */
	public static boolean navigateTargetUrl(List<Node> to_root_list, WebDriver driver, WebDriverWait wdw) {
		for(int i = 0,len = to_root_list.size(); i<len ; i++) {
			Node temp_node = to_root_list.get(i);
			
			if(temp_node.getLevel() == 0) {
				String root_url = ((ElementData)temp_node.getAttach()).getUrl();
				driver.get(root_url);
			}else {
				String temp_xpath = ((ElementData)temp_node.getAttach()).getXpath();
				List<WebElement> list = driver.findElements(By.xpath(temp_xpath));
				
				//해당 xpath가 존재하지 않을 경우(해당 node에 해당하는 페이지가 삭제되었거나 수정되어 찾을수 없는 경우)에는 해당  node자체를 continue처리
				if(list.size() == 0) {
					return false;
				}else {
					list.get(0).click();
				}
			}
			
			wdw.until(ExpectedConditions.elementToBeClickable(By.tagName("body")));
		}
		
		return true;
	}
}
