package nest_pageobject;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public class SearchResults extends LoadableComponent<SearchResults> {
	private String query;
	
	public SearchResults(String query) {
		PageFactory.initElements(Browser.driver(), this);
	}
	
	@Override
	protected void isLoaded() throws Error {
		assertEquals("Search results for: "+this.query, Browser.driver().getTitle());
	}

	@Override
	protected void load() {
		//dummy
	}
	
	public List<String> getProducts() {
		List<String> products = new ArrayList<String>();
		List<WebElement> productList = Browser.driver().findElements(By.cssSelector("ul.products-grid > li"));
		
		for(WebElement item : productList) {
			products.add(item.findElement(By.cssSelector("h2 > a")).getText());
		}
		
		return products;
	}
	
	public void close() {
		Browser.close();
	}
	
	public Search Search() {
		Search search = new Search();
		return search;
	}
}
