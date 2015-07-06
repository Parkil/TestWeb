import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class TestCase1 {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		File file = new File("d:/selenium/IEDriverServer_Win32_2.46.0/IEDriverServer.exe");
		System.setProperty("webdriver.ie.driver", file.getAbsolutePath()); //ie Driver를 쓰기 위해서 해당 System property를 설정해 주어야 함
		
		WebDriver driver = new org.openqa.selenium.ie.InternetExplorerDriver();
		driver.get("http://localhost:10010");
		
		WebElement id = driver.findElement(By.id("id"));
		WebElement pw = driver.findElement(By.id("pw"));
		
		WebElement id_byname = driver.findElement(By.name("id"));
		WebElement pw_byname = driver.findElement(By.name("pw"));
		
		assertEquals(id.getText(), "");
		assertEquals(pw.getText(), "");
		assertEquals(id_byname.getText(), "");
		assertEquals(pw_byname.getText(), "");
		//fail("Not yet implemented");
	}

}
