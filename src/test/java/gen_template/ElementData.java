package gen_template;

public class ElementData {
	private String cssSelector; //By cssSelector 문자열
	private String url; //url정보
	private boolean is_login_page = false; //해당 페이지가 로그인 페이지인지 확인
	
	public boolean isIs_login_page() {
		return is_login_page;
	}

	public void setIs_login_page(boolean is_login_page) {
		this.is_login_page = is_login_page;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCssSelector() {
		return cssSelector;
	}

	public void setCssSelector(String cssSelector) {
		this.cssSelector = cssSelector;
	}
}
