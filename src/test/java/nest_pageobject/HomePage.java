package nest_pageobject;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public class HomePage extends LoadableComponent<HomePage> {
	
	String url = "http://demo.magentocommerce.com/";

	private static String title = "Madison Island";
	
	public HomePage() {
		PageFactory.initElements(Browser.driver(), this);
	}
	
	@Override
	protected void isLoaded() throws Error {
		assertEquals(title, Browser.driver().getTitle());
	}

	@Override
	protected void load() {
		Browser.open(url);
	}
	
	public Search Search() {
		Search search = new Search();
		return search;
	}
}
