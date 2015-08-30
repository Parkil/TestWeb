import static org.junit.Assert.*;
import nest_pageobject.HomePage;
import nest_pageobject.SearchResults;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class TestIE_nest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		HomePage hp = new HomePage();
		hp.get();
		
		SearchResults srs = hp.Search().searchInStore("eye");
		
		System.out.println(srs.getProducts());
		assertEquals(3, srs.getProducts().size());
		assertTrue(srs.getProducts().contains("NOLITA CAMI"));
		
		srs.close();
	}

}
