package gen_template.testcase;
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

import util.Util;

/*
	데이터 는 Data Collection에서 가져오고 실행은 PageObject에서 실행하는 예제
 */
@RunWith(value=Parameterized.class)
public class DataCollectionPageObject {
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
		//driver = new InternetExplorerDriver();
		//driver = Util.getIEDriver();
		driver = Util.getChromeDriver();
		//je = (JavascriptExecutor)driver;
		driver.get("http://localhost:8080");
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
			System.out.println("Data Collection 추출");
			co = col.getCSVData("d:/csv.txt");
			System.out.println("co : "+co);
			//co = col.getExcelData("d:/excel.xlsx");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return co;
	}
	
	public DataCollectionPageObject(String id, String pw) {
		System.out.println("Data Collection 데이터 입력"+id+"===="+pw);
		this.id = id;
		this.pw = pw;
	}
	
	@Test
	public void test() {
		gen_template.testcase.LoginPageObject test = new gen_template.testcase.LoginPageObject(driver, id, pw);
		Object[] t = new Object[1];
		test.get();
		test.exec(t);
	}
}
