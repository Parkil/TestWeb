package util;
import org.openqa.selenium.JavascriptExecutor;

public class InjectJQuery {
	public void injectJQueryIfNone(JavascriptExecutor je) {
		if(isJQueryLoaded(je) == false) {
			injectJQuery(je);
		}
	}
	
	private boolean isJQueryLoaded(JavascriptExecutor je) {
		Boolean result;
		try {
			result = (Boolean)je.executeScript("return jQuery() != null");
		}catch(Exception e) {
			return false;
		}
		
		return result.booleanValue();
	}
	
	private void injectJQuery(JavascriptExecutor je) {
		StringBuffer scriptcon = new StringBuffer();
		scriptcon.append("var script = document.createElement('script');");
		scriptcon.append("script.type = 'text/javascript';");
		scriptcon.append("script.src = 'http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js';");
		scriptcon.append("document.getElementsByTagName('head')[0].appendChild(script);");
		je.executeScript(scriptcon.toString());
	}
}
