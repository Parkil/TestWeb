package gen_template;

public class ElementData {
	private String xpath; //By xpath문자열
	private String url; //url정보
	private boolean is_update_or_delete = false; //해당 요소가 수정 또는 삭제 버튼인지 확인
	
	public boolean isIs_update_or_delete() {
		return is_update_or_delete;
	}

	public void setIs_update_or_delete(boolean is_update_or_delete) {
		this.is_update_or_delete = is_update_or_delete;
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
