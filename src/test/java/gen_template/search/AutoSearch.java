package gen_template.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import gen_template.search.login.ExecLoginProc;
import gen_template.tree.Node;
import gen_template.tree.Tree;

/*
 * selenium을 이용하여 자동으로 페이지의 click가능한 요소를 검색하는 클래스
 * 
 * [구조]
 * 1.페이지로 이동하여 클릭가능한 요소를 검색한다.(현재는 a tag만 처리)
 * 2.클릭가능한 요소가 alreadyClickElementSet에 저장된 요소일 경우에는 continue처리 (무한루프 방지)
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
	//Multi-thread safe한 Collection사용
	private static Set<String> alreadyClickElementSet = new ConcurrentSkipListSet<String>(); //클릭하여 Tree를 구성한 xpath를 저장(한번 클릭한 요소를 다시 클릭하는 케이스를 방지)
	private static Map<String,List<ElementData>> cutOffMap = new ConcurrentHashMap<String,List<ElementData>>(); //클릭후 전 url로 돌아가지 못하는 웹 요소를 저장 (클릭한 요소가 삭제 or 수정)
	private static Map<String,Integer> signatureCntMap = new ConcurrentHashMap<String,Integer>(); //검색시 method signature별 확인개수를 저장
	private static String tree_identifier_format_str = "[%1$s]-lv%2$s-%3$s";
	
	/** 인자로 주어진 요소를 클릭하고 정보를 Tree에 저장
	 * @param search_tag 클릭하고자하는 요소를 지정한 By클래스
	 * @param tree 정보를 저장할 Tree구조 클래스
	 * @param driver selenium WebDriver 클래스
	 * @param proc TODO
	 * @return @param tree와 동일한 Tree구조
	 */
	public static Tree searchClickableElement(By search_tag, Tree tree, WebDriver driver, ExecLoginProc proc){
		WebDriverWait wdw = new WebDriverWait(driver,10); 
		
		WebElement el = null;
		List<Node> node_list = null;
		int level = 0;
		
		tree = new Tree();
		
		try{
			proc.exec_login(driver, tree);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
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
				
				/*
				 * cufOffMap(검색 진행도중 삭제나 기타 이유로 진행을 할수 없는 url을 저장한 Map)을 검색하여 검색결과가 있으면 신규 검색을 하는것이 아니라 
				 * cutOffMap에 있는 내용을 진행한다.(신규로 진행을 하면 alreadyClickElementSet에 진행하지 못한 요소가 신규 Node 밑에 붙기 때문에 이를 방지)
				 */
				String parent_current_url = driver.getCurrentUrl(); //검색대상 url
				String parent_xpath = ((ElementData)node.getAttach()).getXpath(); //부모의 xpath
				
				String del_chk_key = parent_current_url+","+AutoSearchUtil.getMethodSignature(parent_xpath);
				
				List<ElementData> temp_list = null;
				if((temp_list = cutOffMap.get(del_chk_key))!= null) {
					
					//리스트에 들어있는 요소를 모두 클릭했으면리스트에서 삭제처리
					if(AutoSearchUtil.isAllClicked(temp_list)) {
						cutOffMap.remove(del_chk_key);
					}
				}else {
					/*
					 * findElements로 얻어온 데이터를 바로 클릭하지 않고
					 * (바로 클릭시 클릭하는 기능이 페이지를 이동 또는 refresh가 될경우 데이터를 새로 findElements로 갱신하지 않으면 staleElementException이 발생한다)
					 * alreadyClickElementSet(현재 클릭된 요소가 저장된 Set)에 없는 요소만 리스트에 저장(중복클릭 방지)
					 */
					List<WebElement> a_list = driver.findElements(search_tag); //해당페이지의 a link를 검색
					temp_list = new ArrayList<ElementData>();
					int idx = 0;
					
					for(int i = 0,length = a_list.size() ; i < length ; i++) {
						
						el = a_list.get(i);
						
						try {
							//로그아웃 버튼을 실행하지 않도록 처리(나중에는 로그아웃뿐만 아니라 나머지 버튼도 설정으로 실행하지 않도록 처리할 필요가 있음)
							if(proc.is_logout_btn(driver, el)) {
								continue;
							}
						}catch(Exception e) {
							e.printStackTrace();
						}
						
						ElementData temp_el_data = new ElementData();
						temp_el_data.setXpath(AutoSearchUtil.getXpathStr(el));
						
						//이미 클릭된 요소일 경우 리스트에 저장하지 않음
						if(alreadyClickElementSet.contains(temp_el_data.getXpath()+","+parent_current_url)) {
							continue;
						}
						
						/*
						 * 이부분에서 list판단을 해야 할것으로 보임
						 * list로 판단을하려면 동일한 method signature가 2개이상인걸 판단해야 함
						 * HashMap containsKey로 체크
						 * - 각 method signature별 count는 나오는데 이걸 어떻게 tree구조와 match를 시키느냐가 관건
						 * 		->count자체는 tree구조가 완료된후에 나오기 때문에 tree구조 작성중에 match를 시키는건 의미가 없음 
						 * 		->소스코드를 생성하는 과정에서 해당값이 들어가야 할것으로 보임
						 * 		->해당 method signature체크는 동일 level에 있는것만 체크(다른 level은 다른페이지 이므로 의미가 없음)
						 * 		->cnt를 저장하면서 level이 연관되어 저장되어야 함(어떻게 저장할지는 확인 필요)
						 */
						String method_signature = AutoSearchUtil.getMethodSignature(temp_el_data.getXpath());
						if(!signatureCntMap.containsKey(method_signature)) {
							signatureCntMap.put(method_signature, 1);
						}else {
							int prev_cnt = signatureCntMap.get(method_signature);
							signatureCntMap.put(method_signature, prev_cnt+1);
						}
						
						String parent_identifier = node.getIdentifier();
						String identifier = String.format(tree_identifier_format_str, parent_identifier, level, (++idx));
						
						tree.addNode(identifier, parent_identifier).setAttach(temp_el_data);
						
						temp_list.add(temp_el_data);
					}
				}
				
				
				for(ElementData e : temp_list) {
					if(e.getUrl() != null) { //url이 있다는것은 이미 클릭을 한 웹 요소이므로 continue처리함(cutOffMap에서 가져온 경우에만 해당됨)
						continue;
					}
					
					el = driver.findElement(By.xpath(e.getXpath()));
					
					el.click();
					e.setAlertText(AutoSearchUtil.passAlertConfirm(driver)); //alert창 처리
					
					wdw.until(ExpectedConditions.elementToBeClickable(By.tagName("body")));
					
					String child_current_url = driver.getCurrentUrl();
					e.setUrl(child_current_url);
					
					//부모 - 자식간 url이 동일함(검색 이나 외부 연동 또는 팝업처럼 페이지를 이동하지 않는 로직)
					if(parent_current_url.indexOf(child_current_url) != -1 || child_current_url.indexOf(parent_current_url) != -1) {
						e.setSameUrl(true);
					}else { //부모 - 자식간 url이 동일하지 않음 - ElementData에 값을 입력하고 전  URL로 이동처리
						if(level != 0) {
							boolean result = AutoSearchUtil.navigateTargetUrl(to_root_list, driver, wdw);
							
							//전 url로 이동할수 없는 경우 - 해당 데이터가 삭제되거나 수정되어 찾아갈수 없는 경우
							if(!result) {
								alreadyClickElementSet.add(e.getXpath()+","+parent_current_url);
								cutOffMap.put(del_chk_key, temp_list);
								continue node_list_for;
							}
						}else { //부모가  tree level 0인 경우
							driver.get(parent_current_url);
							wdw.until(ExpectedConditions.elementToBeClickable(By.tagName("body")));
						}
					}
					
					alreadyClickElementSet.add(e.getXpath()+","+parent_current_url);
				}
				
				temp_list = null; //초기화
			}
			
			tree.display("root-node");
			
			level++;
		}
		
		
		/* 검색된 Tree구조 데이터를 이용하여 pageobject소스생성
		try {
			new AutoSearchUtil().generateCodeByTree("root-node", tree);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		//템플릿 소스코드 생성을 위해서 직렬화-파일저장을 시키는 로직
		//SerializationTest serial = new SerializationTest();
		//serial.save(tree);
		System.out.println("signatureCntMap : "+signatureCntMap);
		
		return tree;
	}
}
