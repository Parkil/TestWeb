package gen_template.search.config;

import org.openqa.selenium.By;

public class AutoSearchConfig {
	private By clickableCss = null; //클릭가능한 웹 요소
	private By inputableCss = null; //입력가능한 웹 요소
	private final static AutoSearchConfig config = new AutoSearchConfig();
	
	private AutoSearchConfig() {}
	
	public static AutoSearchConfig getInstance() {
		return config;
	}
	
	public void setClickableCss(String clickCssStr) {
		clickableCss = By.cssSelector(clickCssStr);
	}
	
	public void setInputableCss(String inputCssStr) {
		inputableCss = By.cssSelector(inputCssStr);
	}
	
	public By getClickableCss() {
		return (clickableCss == null) ? clickableCss = By.cssSelector("a,input[name='submit'],input[type='button'],input[type='image']") : clickableCss;
	}
	
	public By getInputableCss() {
		return (inputableCss == null) ? inputableCss = By.cssSelector("input[type='text'], input[type='checkbox'], input[type='radio'], textarea, select") : inputableCss;
	}
}
