package gen_template.util;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
		
		WebElement el = null;
		List<Node> node_list = null;
		int level = 0;
		
		
		while( (node_list = tree.getNodeListByLevel(level)).size() != 0 ) {
			
			System.out.println("=======노드 리스트 내용 Level"+level+"========");
			for(Node node : node_list) {
				ElementData zzz = (ElementData)node.getAttach();
				System.out.print(zzz.getXpath()+"=");
			}
			System.out.println();
			System.out.println("=======노드 리스트 내용 끝========");
			
			node_list_for :
			for(Node node : node_list) {
				List<Node> to_root_list = null;
				
				//해당 node에 해당하는 화면으로 이동(level 0이면 url이동 그외는 node에 저장된 xpath를 가져와서 요소를 클릭하는 방식으로 처리)
				if(level != 0) {
					to_root_list = tree.getNodeListToRoot(node.getIdentifier());
					
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
								continue node_list_for;
							}else {
								list.get(0).click();
							}
						}
						
						wdw.until(ExpectedConditions.elementToBeClickable(By.tagName("body")));
					}
				}
				
				String parent_current_url = driver.getCurrentUrl(); //검색대상 url
				
				//Tree에 동일한 URL이 들어있으면 cotinue처리
				if(isUrlContain(tree, parent_current_url, level)) {
					System.out.println("제외 url : "+parent_current_url);
					continue;
				}
				
				//해당페이지의 a link를 검색,클릭하여 이동하는 URL을 Tree 하위 Level에 저장
				List<WebElement> a_list = driver.findElements(search_tag);
				
				for(int i = 0,length = a_list.size() ; i < length ; i++) {
					try {
						el = a_list.get(i);
						
						//삭제대상 코드
						if(el.getText().intern() == "로그아웃".intern() || el.getText().intern() == "history.back()".intern()) {
							continue;
						}
					}catch(StaleElementReferenceException e) {
						System.out.println("a list refresh url : "+driver.getCurrentUrl());
						
						//여기서 가져온 a_list와 원래 a_list의 내용이 다를경우의 처리로직을 생각해 봐야 함
						a_list = driver.findElements(search_tag);
						el = a_list.get(i);
					}catch(NoSuchElementException ne) {
						System.out.println("해당 요소가 존재하지 않음");
					}
					
					ElementData el_data = new ElementData();
					el_data.setXpath(getXpathStr(el));
					el.click();
					passAlertConfirm(driver);
					
					wdw.until(ExpectedConditions.elementToBeClickable(By.tagName("body")));
					
					String child_current_url = driver.getCurrentUrl();
					el_data.setUrl(child_current_url);
					
					//부모 - 자식간 url이 동일함(검색 이나 외부 연동 또는 팝업처럼 페이지를 이동하지 않는 로직)
					if(parent_current_url.indexOf(child_current_url) != -1 || child_current_url.indexOf(parent_current_url) != -1) {
						//나중에 처리로직 삽입
					}else { //부모 - 자식간 url이 동일하지 않음 - ElementData에 값을 입력하고 전  URL로 이동처리
						String parent_identifier = node.getIdentifier();
						ElementData parent_el_data = (ElementData)tree.getNode(parent_identifier).getAttach();
						
						tree.addNode(level+"-"+i, parent_identifier).setAttach(el_data);
						/*
						 * to_root_list가 null이 아닌 경우에는 80라인과 동일한 로직으로 대상 페이지로 이동하고
						 * null인 경우(level 0)에는 부모 url로 바로 이동한다. 
						 */
						if(to_root_list != null) {
							for(int z = 0,len = to_root_list.size(); z<len ; z++) {
								Node temp_node = to_root_list.get(z);
								
								if(temp_node.getLevel() == 0) {
									String root_url = ((ElementData)temp_node.getAttach()).getUrl();
									driver.get(root_url);
								}else {
									String temp_xpath = ((ElementData)temp_node.getAttach()).getXpath();
									List<WebElement> list = driver.findElements(By.xpath(temp_xpath));
									
									if(list.size() == 0) {
										continue;
									}else {
										list.get(0).click();
									}
								}
								
								wdw.until(ExpectedConditions.elementToBeClickable(By.tagName("body")));
							}
						}else { //부모가  tree level 0인 경우
							driver.get(parent_current_url);
						}
						
						wdw.until(ExpectedConditions.elementToBeClickable(By.tagName("body")));
			
						//현재 url과 부모 node의 url이 동일한 경우에는 tree에 입력을 하지 않도록 처리한다.(이 로직 검토 필요)
						if(driver.getCurrentUrl().intern() != parent_el_data.getUrl().intern()) { //현재 여기로직에서 전부 continue가 걸리고 있음
						
						}
					}
				}
			}
			
			tree.display("root-node");
			System.out.println("\n\n");
			level++;
			
			//테스트용 코드
			if(level == 2) {
				break;
			}
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
			//System.out.println("No Alert Presented");
		}finally {
			driver.switchTo().window(parent);
		}
	}
	
	/**Tree에 인자로 들어간 url이 존재하는지 확인. 인자로 주어인 level의 Node는 검색조건에서 제외됨.
	 * @param tree url을 확인할 tree구조
	 * @param url 확인하고자 하는 url
	 * @param level 검색에서 제외하고자하는 Tree Level
	 * @return true or false
	 */
	public static boolean isUrlContain(Tree tree, String url, int level) {
		Iterator<Node> iter = tree.iterator("root-node");
		
		//String replace_url = url.replaceAll("\\?.*$", ""); //?~뒤의 파라메터 부분 제거
		
		while(iter.hasNext()) {
			Node node = iter.next();
			
			if(node.getLevel() == level) {
				continue;
			}
			
			/*
			if(node.getIdentifier().intern() == "root-node".intern()) { //최상위 노드는 제외하고 검색
				continue;
			}*/
			
			String node_url = ((ElementData)node.getAttach()).getUrl();
			
			if(url.equals(node_url)) {
				return true;
			}
			
			/*
			if(url.indexOf(node_url) != -1 || node_url.indexOf(url) != -1) {
				return true;
			}*/
		}
		
		return false;
	}
}
