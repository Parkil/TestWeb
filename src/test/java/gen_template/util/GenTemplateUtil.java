package gen_template.util;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import gen_template.ElementData;
import gen_template.tree.Node;
import gen_template.tree.Tree;
import util.Util;

public class GenTemplateUtil {
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

	
	/** 인자로 주어진 요소를 클릭하고 정보를 Tree에 저장
	 * @param search_tag 클릭하고자하는 요소를 지정한 By클래스
	 * @param tree 정보를 저장할 Tree구조 클래스
	 * @param driver selenium WebDriver 클래스
	 * @return @param tree와 동일한 Tree구조
	 */
	public static Tree searchClickableElement(By search_tag, Tree tree, WebDriver driver){
		WebDriverWait wdw = new WebDriverWait(driver,10); 
		By input_hidden = By.cssSelector("input[type=hidden]"); //input hidden 검색 by;
		
		WebElement el = null;
		String parent_current_url = driver.getCurrentUrl(); //부모 페이지 url
		
		List<Node> node_list = null;
		int level = 0;
		
		while( (node_list = tree.getNodeListByLevel(level)).size() != 0 ) {
			for(Node node : node_list) {
				String parent_identifier = node.getIdentifier();
				
				List<Node> to_root_list = null;
				
				//level이 0(최초 화면)이 아닐경우 해당 화면으로 이동(이동하는 방식은 node마다 저장된 By selector를 이용하여 클릭가능한 요소를 클릭하는 방식으로 처리)
				if(level != 0) {
					to_root_list = tree.getNodeListToRoot(node.getIdentifier());
					
					for(Node temp_node : to_root_list) {
						if(temp_node.getLevel() == 0) {
							String root_url = ((ElementData)temp_node.getAttach()).getUrl();
							driver.get(root_url);
						}else {
							String temp_xpath = ((ElementData)temp_node.getAttach()).getXpath();
							By temp = By.xpath(temp_xpath);
							//테스트 코드
							try {
								driver.findElement(temp).click();
							}catch(Exception e) {
								System.out.println("level : "+level);
								continue;
							}
						}
						
						wdw.until(ExpectedConditions.elementToBeClickable(By.tagName("body")));
					}
				}
				
				parent_current_url = driver.getCurrentUrl();
				
				List<WebElement> a_list = driver.findElements(search_tag);
				for(int i = 0,length = a_list.size() ; i < length ; i++) {
					
					try {
						el = a_list.get(i);
						
						if(el.getText().intern() == "로그아웃".intern()) {
							continue;
						}
						
					}catch(StaleElementReferenceException e) {
						a_list = driver.findElements(search_tag);
						
						//테스트용 코드
						if(i > a_list.size()) {
							i = a_list.size();
							for(WebElement val : a_list) {
								System.out.println(val.getAttribute("href"));
							}
						}
						
						el = a_list.get(i);
					}catch(NoSuchElementException ne) {
						System.out.println("해당 요소가 존재하지 않음");
					}
					
					System.out.println("href : "+el.getAttribute("href"));
					
					ElementData el_data = new ElementData();
					el_data.setXpath(getXpathStr(el));
					el.click();
					passAlertConfirm(driver);
					
					wdw.until(ExpectedConditions.elementToBeClickable(By.tagName("body")));
					
					String child_current_url = driver.getCurrentUrl();
					el_data.setUrl(child_current_url);
					
					//부모 - 자식간 url이 동일함(검색 이나 외부 연동 또는 팝업처럼 페이지를 이동하지 않는 로직)
					if(parent_current_url.indexOf(child_current_url) != -1 || child_current_url.indexOf(parent_current_url) != -1) {
						
						//System.out.println("동일 : "+el_data.getXpath());
					}else { //부모 - 자식간 url이 동일하지 않음
						
						ElementData parent_el_data = (ElementData)tree.getNode(parent_identifier).getAttach();
						//System.out.println("부모 노드 url : "+parent_el_data.getUrl());
						
						/*
						 * input hidden으로 queryString을 만들어서 url을 이동하는 방식은 Article이 메인이 되고 List가 sub가 되어서
						 * Article->List->Article로 돌아올때 문제가 생기는 경우가 있다. 이를 방지하기 위해서 to_root_list가 있을경우에는 
						 * to_root_list의 마지막 요소(현재 클릭가능한 요소를 검색 하는 페이지)의 xpath를 검색해서 클릭하도록 처리한다.
						 */
						if(to_root_list != null) {
							int len = to_root_list.size();
							
							Node this_node = to_root_list.get(len-1);
							
							String temp_xpath = ((ElementData)this_node.getAttach()).getXpath();
							By temp = By.xpath(temp_xpath);
							try {
								driver.findElement(temp).click();
							}catch(NoSuchElementException e) {
								el_data.setIs_update_or_delete(true);
								System.out.println("해당 요소가 존재하지 않음. 수정되거나 삭제되었을 가능성이 있음 다음요소로 이동");
							}
						}else {
							try {
								driver.get(parent_current_url+Util.getQueryString(driver.findElements(input_hidden)));
							} catch (OperationNotSupportedException e) {
								e.printStackTrace();
							}
						}
						
						wdw.until(ExpectedConditions.elementToBeClickable(By.tagName("body")));
						
						//현재 url과 부모 node의 url이 동일한 경우에는 tree에 입력을 하지 않도록 처리한다.
						if(driver.getCurrentUrl().intern() == parent_el_data.getUrl().intern()) {
							continue;
						}
						
						tree.addNode(level+"-"+i, parent_identifier).setAttach(el_data);
						
						//해당 요소가 존재하지 않을 경우에는 더이상의 검색을 중지하고 다음 검색요소로 이동처리
						if(el_data.isIs_update_or_delete()) {
							break;
						}
						
					}
				}
			}
			
			tree.display("root-node");
			System.out.println("\n\n");
			level++;
		}
		
		return tree;
	}
	
	/**alert이나 confirm이 발생할 경우 ok버튼을 눌러서 pass하도록 처리 다중 alert도 처리가능
	 * @param driver
	 */
	public static void passAlertConfirm(WebDriver driver) {
		String parent = driver.getWindowHandle();
		try {
			/*
			 * 한 기능(버튼클릭)시 10개 정도의 팝업을 띄우는 케이스는 없다고 가정하고 처리
			 */
			for(int i = 0 ; i<10 ; i++) {
				Alert alert = driver.switchTo().alert();
				alert.accept();
				driver.switchTo().window(parent); //alert을 닫은후 포커스를 다시 부모창으로 이동해야 다음 alert을 처리할수 있음.
			}
		}catch(NoAlertPresentException nape) {
			System.out.println("No Alert Presented");
		}finally {
			driver.switchTo().window(parent);
		}
	}
}
