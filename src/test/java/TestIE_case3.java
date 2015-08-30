import static org.junit.Assert.*;

import java.io.File;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;

/*
 * 데이터 주도 테스트 관련 소스
 * 
 * 적용방법
 * 1.테스트 케이스 상단에 @RunWith(value=Parameterized.class) 적용
 * 2.데이터를 반환하는 메소드에 @Parameters 어노테이션 적용
 * 3.생성자 정의
 * 	주의할점은 데이터의 순서대로 생성자의 인자를 정의해야 한다.
 *  다시 말해서 {"1","2"},{"3","4"} 데이터가 @Parameters 메소드에서 반환될경우
 *  
 *  public Creator(String a, String b)로 생성자를 정의하면 
 *  a에는 1,3 데이터가, b에는 2,4데이터가 정의된다.
 *  4.테스트케이스에서 생성자에서 정의된 데이터로 테스트를 수행
 */
@RunWith(value=Parameterized.class)
public class TestIE_case3 {
	private static WebDriver driver;
	//private static JavascriptExecutor je;
	private static GetDataCollection col = new GetDataCollection();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("setUpBeforeClass");
		
		StringBuffer sb = new StringBuffer();
		System.out.println("setUp");
		
		sb.append(System.getProperty("user.dir"));
		sb.append(File.separator);
		sb.append("src");
		sb.append(File.separator);
		sb.append("test");
		sb.append(File.separator);
		sb.append("resources");
		sb.append(File.separator);
		sb.append("IEDriverServer.exe");
		
		System.setProperty("webdriver.ie.driver", sb.toString());
		driver = new InternetExplorerDriver();
		//je = (JavascriptExecutor)driver;
		driver.get("http://localhost:10010");
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.close();
	}

	@Before
	public void setUp() throws Exception {
	}
	
	private String id;
	private String pw;
	
	@SuppressWarnings("rawtypes")
	@Parameters
	public static Collection test_login_data() {
		//return col.getPlainData();
		Collection co = null;
		try {
			co = col.getCSVData("d:/csv.txt");
			//co = col.getExcelData("d:/excel.xlsx");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return co;
	}
	
	public TestIE_case3(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}
	
	@Test
	public void test() {
		WebElement input_id = driver.findElement(By.cssSelector("#id"));
		WebElement input_pw = driver.findElement(By.cssSelector("#pw"));
		WebElement submit 	= driver.findElement(By.cssSelector("input[name='submit']"));
		//WebElement error	= driver.findElement(By.cssSelector("#loginfrm"));
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //묵시적대기적용(무조건 3초를 대기)
		
		input_id.clear();
		input_id.sendKeys(id);
		
		input_pw.clear();
		input_pw.sendKeys(pw);
		submit.click();
		
		//System.out.println(error.getText());
		
		assertEquals("user", id);
	}
}
