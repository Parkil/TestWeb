package gen_template.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import gen_template.ElementData;
import gen_template.tree.Node;
import gen_template.tree.Tree;

/*
 * selenium을 이용하여 자동으로 페이지의 click가능한 요소를 검색하는 클래스
 * 
 * [구조]
 * 1.페이지로 이동하여 클릭가능한 요소를 검색한다.(현재는 a tag만 처리)
 * 2.클릭가능한 요소가 xpath_list에 저장된 요소일 경우에는 continue처리 (무한루프 방지)
 * 3.1번에서 검색된 요소를 순차적으로 클릭한다.
 * 	3-1.클릭전 url과 클릭후 url이 동일한 경우 - tree에 동일 url flag를 달아서 저장
 *  3-2.클릭전 url과 클릭후 url이 다른경우 - 1번에서 지정된 페이지로 다시 이동 하고 tree에 해당 데이터를 저장한 다음 3번으로 이동
 *  
 * 위 과정을 tree의 단말노드가 존재하지 않을때 까지 처리한다. 
 * 
 * [페이지 이동 로직]
 * 원래는 history.back을 이용하려고 하였으나 forward 된 상태에서 history.back을 실행하면 [만료된 페이지 입니다] 페이지가 표시되기 때문에 
 * node의 최초 페이지로 이동후(최초 페이지는 링크가 없고 url로 이동) tree 하위 노드에 저장된 xpath를 클릭하는 방식으로 대상 페이지를 이동하도록 처리
 */
public class AutoSearch {
	private static Set<String> xpath_list = new HashSet<String>(); //xpath를 저장하는 리스트(중복제거를 위해 Set을사용)

	
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
			
			node_list_for :
			for(Node node : node_list) {
				
				//해당 Node의 ElementData가 동일 Url flag를 가지고 있을 경우 continue 처리
				ElementData temp = (ElementData)node.getAttach();
				if(temp.isSameUrl()) {
					continue node_list_for;
				}
				
				List<Node> to_root_list = null;
				
				//해당 node에 해당하는 화면으로 이동(level 0이면 url이동 그외는 node에 저장된 xpath를 가져와서 요소를 클릭하는 방식으로 처리)
				if(level != 0) {
					to_root_list = tree.getNodeListToRoot(node.getIdentifier());
					boolean result = AutoSearchUtil.navigateTargetUrl(to_root_list, driver, wdw);
					
					if(!result) {
						continue node_list_for;
					}
				}
				
				String parent_current_url = driver.getCurrentUrl(); //검색대상 url
				
				//해당페이지의 a link를 검색,클릭하여 이동하는 URL을 Tree 하위 Level에 저장
				List<WebElement> a_list = driver.findElements(search_tag);
				
				
				//===============================================================여기 부터 수정영역
				
				List<ElementData> temp_list = new ArrayList<ElementData>();
				int idx = 0;
				
				for(int i = 0,length = a_list.size() ; i < length ; i++) {
					el = a_list.get(i);
					
					//일부버튼을 실행하지 않도록 처리(나중에는 설정으로 따로 빼도록 처리)
					if(el.getText().intern() == "로그아웃".intern() || el.getText().intern() == "history.back()".intern()) {
						continue;
					}
					
					ElementData temp_el_data = new ElementData();
					temp_el_data.setXpath(AutoSearchUtil.getXpathStr(el));
					
					//해당 xpath와 url이 동일한 경우 버튼을 클릭하지 않고 continue처리
					if(xpath_list.contains(temp_el_data.getXpath()+"======"+parent_current_url)) {
						System.out.println("해당 xpath는 xpath_list에 존재함 : "+temp_el_data.getXpath()+"======"+parent_current_url);
						continue;
					}
					
					String parent_identifier = node.getIdentifier();
					tree.addNode(parent_identifier+"-"+level+"-"+(++idx), parent_identifier).setAttach(temp_el_data);
					
					temp_list.add(temp_el_data);
				}
				
				for(ElementData e : temp_list) {
					el = driver.findElement(By.xpath(e.getXpath()));
					
					el.click();
					e.setAlertText(AutoSearchUtil.passAlertConfirm(driver));
					
					wdw.until(ExpectedConditions.elementToBeClickable(By.tagName("body")));
					
					String child_current_url = driver.getCurrentUrl();
					e.setUrl(child_current_url);
					
					//부모 - 자식간 url이 동일함(검색 이나 외부 연동 또는 팝업처럼 페이지를 이동하지 않는 로직)
					if(parent_current_url.indexOf(child_current_url) != -1 || child_current_url.indexOf(parent_current_url) != -1) {
						e.setSameUrl(true);
					}else { //부모 - 자식간 url이 동일하지 않음 - ElementData에 값을 입력하고 전  URL로 이동처리
						if(level != 0) {
							boolean result = AutoSearchUtil.navigateTargetUrl(to_root_list, driver, wdw);
							
							if(!result) {
								xpath_list.add(e.getXpath()+"======"+parent_current_url);
								continue node_list_for;
							}
						}else { //부모가  tree level 0인 경우
							driver.get(parent_current_url);
							wdw.until(ExpectedConditions.elementToBeClickable(By.tagName("body")));
						}
					}
					
					xpath_list.add(e.getXpath()+"======"+parent_current_url);
				}
			}
			
			tree.display("root-node");
			System.out.println("\n\n");
	
			level++;
		}
		
		return tree;
	}
}
