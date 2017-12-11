package gen_template.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import gen_template.tree.Node;
import gen_template.tree.Tree;

/*
 * AutoSearch 클래스에서 사용하는 유틸리티 클래스
 */
public class AutoSearchUtil {
	/** WebElement에서 xpath정보(By에서 사용)를 반환
	 * @param el WebElement
	 * @return xpath 선택 문자열
	 */
	public static String getXpathStr(WebElement el) {
		String tag_name = el.getTagName();
		String xpath_format = "//%1$s[@%2$s=\"%3$s\"]";
		
		/*
		 * a tag의 href에 #을 넣고 onclick 항목에 자바스크립트 코드를 넣는 경우 getAttribute("href")가 #으로 나오는 것이 아닌
		 * url주소/# 로 표시가 되기 때문에 onclick을 가장먼저 검색하도록 처리
		 * 속성 대소문자는 구분하지 않음
		 */
		String[] attr_arr = {"onclick", "id", "name", "href"};
		
		String attr = null;
		String sel_attr = null;
		
		for(String val : attr_arr) {
			attr = el.getAttribute(val);
			if(attr != null && attr.trim().intern() != "".intern()) {
				sel_attr = val;
				break;
			}
		}
		
		return String.format(xpath_format, tag_name, sel_attr, attr);
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
		}catch(NoAlertPresentException nape) { //alert이 존재하지 않을 경우
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
				
				//해당 xpath가 존재하지 않을 경우(해당 node에 해당하는 페이지가 삭제되었거나 수정되어 찾을수 없는 경우)false를 반환
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
	
	/**javascript 메소드의 유형 반환
	 * @param method_str javascript 호출 문자열
	 * @return
	 */
	public static String getMethodSignature(String method_str) {
		if(method_str == null || method_str.intern() == "".intern()) {
			return method_str;
		}
		
		Pattern p = Pattern.compile("\\((.*)\\)");
		Matcher m = p.matcher(method_str);
		
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		if(m.find()) {
			String param_str = m.group(1);
			
			if(param_str == null || param_str.intern() == "".intern()) {
				return method_str;
			}else {
				String arr[] = param_str.split(",");
				
				String val = null;
				for(int i = 0 ; i<arr.length ; i++) {
					val = arr[i];
					if(val.matches("'(.*)'")) { // '~'
						sb.append("''");
					}else if(val.matches("\"(.*)\"")) { //"~"
						sb.append("\"\"");
					}
					
					if(i != arr.length-1) {
						sb.append(",");
					}
				}
				sb.append(")");
			}
			
			return method_str.replaceAll(p.toString(), sb.toString());
		}else {
			return method_str;
		}
	}
	
	/**리스트에 담긴 웹 요소가 전부 클릭되었는지 확인
	 * @param temp_list
	 * @return
	 */
	public static boolean isAllClicked(List<ElementData> temp_list) {
		boolean isAllClicked = true;
		
		for(ElementData ed: temp_list) {
			if(ed.getUrl() == null) {
				isAllClicked = false;
				break;
			}
		}
		
		return isAllClicked;
	}
	
	/**JavaScript Method 구조 반환
	 * @param method_str
	 * @return
	 */
	public static String getJavaScriptMethodSignature(String method_str) {
		Pattern p = Pattern.compile("\\((.*)\\)");
		Matcher m = p.matcher(method_str);
		
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		if(m.find()) {
			String param_str = m.group(1);
			
			if(param_str == null || param_str.intern() == "".intern()) {
				return method_str;
			}else {
				String arr[] = param_str.split(",");
				
				String val = null;
				for(int i = 0 ; i<arr.length ; i++) {
					val = arr[i];
					if(val.matches("'(.*)'")) { // '~'
						sb.append("''");
					}else if(val.matches("\"(.*)\"")) { //"~"
						sb.append("\"\"");
					}
					
					if(i != arr.length-1) {
						sb.append(",");
					}
				}
				sb.append(")");
			}
			
			return method_str.replaceAll(p.toString(), sb.toString());
		}else {
			return method_str;
		}
	}
	
	/** AutoSearch에서 생성된 Tree데이터를 기준으로 템플릿 코드 생성
	 * @param identifier
	 * @param tree
	 * @throws Exception
	 */
	public void generateCodeByTree(String identifier, Tree tree) throws Exception{
		ArrayList<String> children = tree.getNode(identifier).getChildren();
		
		Map<String,String> gen_data_map = new HashMap<String,String>();
		if(children.size() != 0) {
			ElementData parent_el = (ElementData)tree.getNode(identifier).getAttach();
			gen_data_map.put("url", parent_el.getUrl());
			gen_data_map.put("class_nm", identifier.replace("-", "_")); //나중에 식별자 자체를 _로 변경
			
			Set<String> signature_set = new HashSet<String>();
			
			int idx = 0;
			for (String child : children) {
				ElementData child_el = (ElementData)tree.getNode(child).getAttach();
				
				String signature = AutoSearchUtil.getJavaScriptMethodSignature(child_el.getXpath());
				
				if(signature_set.contains(signature)) { //동일한 mehtod signature가 존재할경우 pass처리
					continue;
				}
				
				signature_set.add(signature);
				
				gen_data_map.put("xpath"+(++idx), child_el.getXpath());
			}
			new GenerateCodeUtil().genTemplateCode(gen_data_map);
		}

		for (String child : children) {
			//재귀호출
			this.generateCodeByTree(child, tree);
		}
	}
}
