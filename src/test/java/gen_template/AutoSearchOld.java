package gen_template;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.tools.ant.taskdefs.WaitFor.Unit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import gen_template.tree.Node;
import gen_template.tree.Tree;
import util.InjectJQuery;
import util.Util;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AutoSearchOld {
	private static WebDriver driver;
	private static JavascriptExecutor je;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = Util.getIEDriver();
		je = (JavascriptExecutor)driver;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//driver.close();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	/**
	 * 로그인
	 */
	@Test
	public void test() {
		InjectJQuery ij = new InjectJQuery();
		ij.injectJQueryIfNone(je);
		
		driver.get("http://localhost:8082");
		
		je.executeScript("$('form[name=loginfrm]').attr('onsubmit','javascript:getSubmitHiddenParam();');", "");
		WebElement id = driver.findElement(By.cssSelector("#id"));
		WebElement pw = driver.findElement(By.cssSelector("#pw"));
		WebElement submit = driver.findElement(By.cssSelector("input[type='submit']"));
		
		id.sendKeys("user");
		pw.sendKeys("user");
		submit.click();
		
		String ttt = (String)je.executeScript("return localStorage.test");
		System.out.println(ttt);
		
		/*
		 * <script type="text/javascript">
	function test() {
		$('form[name=loginfrm]').attr('onsubmit',"javascript:getSubmitHiddenParam();");
		var sss = $('form[name=loginfrm]').attr('onsubmit');
		console.log('sss : '+sss);
	}
	
	function getSubmitHiddenParam() {
		var param_arr = new Array();
		$('input[type=text]').each(function() {
			var value = $(this).attr('id')+'-'+$(this).val();
			param_arr.push(value);
		});
		
		localStorage.test = param_arr;
	}
</script>
		 */
		
		/*
		 * 묵시적 대기시간은 해당 wait구문이 호출된후의 페이지에서 대기로직이 실행된다.
		 * 지금 코드의 경우에는 로그인버튼을 클릭하고 이동하는 페이지에서 10초를 대기한다.
		 */
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); 
		
		WebDriverWait wdw = new WebDriverWait(driver,10);
		wdw.until(ExpectedConditions.titleContains("List"));
		
		//assertEquals(driver.getCurrentUrl(), "http://localhost:8080/sample/egovSampleList.do");
	}
	
	
	/**로그인후 메인페이지에서 loop를 돌려서 하위 페이지를 검색
	 * @throws Exception
	 */
	//@Test
	public void test2() throws Exception{
		//1.로딩이 다 되었는지 확인
		
		//2.해당 페이지에서 클릭가능한 요소를 추출(여기서는 a tag만 지정)
		By a_tag = By.tagName("a");
		List<WebElement> a_list = driver.findElements(a_tag);
		
		/*
		 * 3.리스트를 돌면서 요소를 클릭
		 * 		<공통 요소>
		 * 		페이지가 이동 되었을 경우 - staleElementException방지를 위해서 다시 2번을 반복한다
		 * 		클릭후 페이지가 refresh되었는지 WebDriverWait를 이용
		 * 		해당 페이지가 로그인되었을 경우 한번 로그인이 되면 다음 부터를 로그인하지 않도록 처리
		 * 
		 * 		<클릭별>
		 * 		클릭후
		 * 			case1 : 다른 페이지로 이동(상단 url이 변경)
		 * 			case2 : 다른 페이지로 이동(상단 url이 변경되지 않음 - iframe 또는 url은 고정이고 특정 페이지에서 include를 이용하여 페이지를 표시
		 * 			case3 : 페이지가 바뀌지 않고 팝업이 표시되는 경우
		 * 			case4 : 페이지가 바뀌지 않고 팝업도 표시되지 않음(외부 연동이나 기타 특정 기능 수행)
		 * 
		 *			case1,2의 경우 이전 url(1번이 수행되고 표시된 화면의 url)로 되돌아가는 방법 강구필요(javascript history.back or driver.get())
		 *			case3의 경우는 팝업이 존재하는지 확인하는 로직 필요
		 *			case4의 경우는 그냥 진행
		 */
		
		WebElement el = null;
		String parent_current_url = driver.getCurrentUrl(); //부모 페이지 url
		for(int i = 0,length = a_list.size() ; i < length ; i++) {
			try {
				el = a_list.get(i);
				
				if(el.getText().intern() == "로그아웃".intern()) {
					continue;
				}
				
			}catch(StaleElementReferenceException e) {
				a_list = driver.findElements(a_tag);
				el = a_list.get(i);
			}
			
			el.click();
			
			WebDriverWait wdw = new WebDriverWait(driver,10);
			wdw.until(ExpectedConditions.elementToBeClickable(By.tagName("body")));
			
			String child_current_url = driver.getCurrentUrl();
			
			if(parent_current_url.indexOf(child_current_url) != -1 || child_current_url.indexOf(parent_current_url) != -1) {
				System.out.println("부모 - 자식 url이 동일함 : "+parent_current_url);
			}else {
				System.out.println("부모 - 자식간 url이 동일하지 않음 : "+parent_current_url+"<==>"+child_current_url);
				
				By input_hidden = By.cssSelector("input[type=hidden]");
				driver.get(parent_current_url+Util.getQueryString(driver.findElements(input_hidden)));
				
				wdw.until(ExpectedConditions.elementToBeClickable(By.tagName("body")));
			}
		}
	}
	
	/**driver.get으로 url이동시 input hidden값을 querystring으로 변경해서 get방식으로 같이 넘기도록 처리
	 * @throws Exception
	 */
	//@Test
	public void test2_1() throws Exception{
		String url = driver.getCurrentUrl();
		By a_tag = By.tagName("a");
		List<WebElement> a_list = driver.findElements(a_tag);
		
		a_list.get(3).click();
		
		WebDriverWait wdw = new WebDriverWait(driver,10);
		wdw.until(ExpectedConditions.elementToBeClickable(By.tagName("body")));
		
		By input_hidden = By.cssSelector("input[type=hidden]");
		List<WebElement> hidden_list = driver.findElements(input_hidden);
		
		driver.get(url+Util.getQueryString(hidden_list));
	}
	
	/** 클릭가능한 요소 자동검색 
	 * @throws Exception
	 */
	//@Test
	public void test2_2() throws Exception{
		By a_tag = By.tagName("a");
		By input_hidden = By.cssSelector("input[type=hidden]"); //input hidden 검색 by;
		
		WebElement el = null;
		String parent_current_url = driver.getCurrentUrl(); //부모 페이지 url
		
		ElementData el_data = new ElementData();
		el_data.setXpath("root");
		el_data.setUrl(parent_current_url);
		Tree tree = new Tree();
		tree.addNode("root-node").setAttach(el_data);
		
		int level = 0;
		List<Node> node_list = null;
		WebDriverWait wdw = new WebDriverWait(driver,10);
		
		while( (node_list = tree.getNodeListByLevel(level)).size() != 0 ) {
			
			System.out.println("level : "+level);
			for(Node n : node_list) {
				String identifier = n.getIdentifier(); //노드의 key
				String parent_identifier = (level == 0) ? "root-node" : n.getParent_identifier();
				/*
				 * root node만 존재할때에는 node -> root까지의 경로롤 표시하지 않도록 처리
				 */
				if(level != 0) {
					//root부터 element를 클릭하여 WebElement를 검색할 해당 페이지로 이동하도록 처리
					List<Node> to_root_list = tree.getNodeListToRoot(identifier);
					
					/*
					 * first : root-node이기 때문에 url로 이동
					 * 2 ~ last : 클릭처리
					 */
					
					for(int i = 0,length = to_root_list.size() ; i<length ; i++) {
						if(i == 0 ) {
							String url = ( (ElementData)to_root_list.get(0).getAttach() ).getUrl();
							System.out.println("root-node url :"+url);
							driver.get(url);
						}else {
							String cssSelectorStr = ( (ElementData)to_root_list.get(i).getAttach() ).getXpath();
							System.out.println("cssSelectorStr : "+cssSelectorStr);
							By a_tag_by = By.xpath(cssSelectorStr);
							WebElement temp_el = driver.findElement(a_tag_by);
							temp_el.click();
						}
						
						wdw.until(ExpectedConditions.elementToBeClickable(By.tagName("body")));
					}
					
					parent_current_url = driver.getCurrentUrl();
				}
				
				List<WebElement> a_list = driver.findElements(a_tag);
				
				//테스트 코드
				if(level != 0) {
					System.out.println("최총 url : "+parent_current_url);
					for(WebElement ttt : a_list) {
						System.out.println(ttt.getTagName()+"==="+ttt.getText());
					}
					//return;
				}
				
				for(int i = 0,length = a_list.size() ; i < length ; i++) {
					try {
						el = a_list.get(i);
						
						if(el.getText().intern() == "로그아웃".intern()) {
							continue;
						}
						
					}catch(StaleElementReferenceException e) {
						a_list = driver.findElements(a_tag);
						System.out.println("renew a list size : "+a_list.size());
						el = a_list.get(i);
					}
					
					System.out.println("zzzzz : "+el.getAttribute("sdfasdfsdafsadfa"));
					String selector = "//a[@href=\""+el.getAttribute("href")+"\"]";
					el.click();
					
					wdw.until(ExpectedConditions.elementToBeClickable(By.tagName("body")));
					
					String child_current_url = driver.getCurrentUrl();
					
					System.out.println(child_current_url);
					
					//부모 - 자식간 url이 동일함
					if(parent_current_url.indexOf(child_current_url) != -1 || child_current_url.indexOf(parent_current_url) != -1) {
						System.out.println("부모 - 자식 url이 동일함 : "+parent_current_url);
						
						/*
						el_data = new ElementData();
						el_data.setCssSelector(selector);
						el_data.setUrl(child_current_url);
						
						tree.addNode(level+"-"+i, parent_identifier).setAttach(el_data);
						*/
					}else { //부모 - 자식간 url이 동일하지 않음
						System.out.println("부모 - 자식간 url이 동일하지 않음 : "+parent_current_url+"<==>"+child_current_url);
						
						el_data = new ElementData();
						el_data.setXpath(selector);
						
						tree.addNode(level+"-"+i, parent_identifier).setAttach(el_data);
						
						System.out.println("test url : "+parent_current_url+Util.getQueryString(driver.findElements(input_hidden)));
						driver.get(parent_current_url+Util.getQueryString(driver.findElements(input_hidden)));
						
						wdw.until(ExpectedConditions.elementToBeClickable(By.tagName("body")));
					}
				}
			}
			//테스트 코드
			if(level != 0) {
				//return;
			}
			
			level++;
			System.out.println("\n\n\n");
		}
		
		tree.display("root-node");
	}
	
	/*
	 * By 테스트
	 */
	//@Test
	public void test2_3() throws Exception{
		
		//a tag의 href를 검색하기 위해서 xpath를 이용하도록 처리 cssSelector에는 href를 이용하여 검색하는 기능이 없음
		By xpath = By.xpath("//a[@href=\"javascript:fn_egov_select('SAMPLE-00001')\"]");
		WebElement el = driver.findElement(xpath);
		System.out.println(el.getTagName());
		System.out.println(el.getText());
		
		//System.out.println(je.executeScript("return $(\"a[href='javascript:fn_egov_addView();']\").html()", "")); //jquery이용시 href를 "로 감싸면 (href=[\"~\"])JavaScript error발생
	}
}
