package gen_template;

import java.io.Serializable;

public class ElementData implements Serializable{
	
	private static final long serialVersionUID = -1263810364771199995L;
	
	private String xpath; //By xpath문자열
	private String url; //url정보
	private String alertText; //해당 요소를 클릭했을때 발생하는 alert문구(없을수도 있음)
	private boolean isSameUrl = false; //해당 요소를 클릭전,후 url이 동일한지 여부
	
	public boolean isSameUrl() {
		return isSameUrl;
	}

	public void setSameUrl(boolean isSameUrl) {
		this.isSameUrl = isSameUrl;
	}

	public String getAlertText() {
		return alertText;
	}

	public void setAlertText(String alertText) {
		this.alertText = alertText;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}
}
