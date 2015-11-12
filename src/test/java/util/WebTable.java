package util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/*
 * selenium에서 테이블을 접근하기 위한 유틸리티 클래스
 */
public class WebTable {
	private WebElement _webTable;
	
	public WebTable(WebElement table) {
		setWebTable(table);
	}
	
	public WebElement getWebTable() {
		return _webTable;
	}
	
	public void setWebTable(WebElement table) {
		_webTable = table;
	}
	
	public int getRowCount() {
		return _webTable.findElements(By.tagName("tr")).size();
	}
	
	/*
	 * 요즘은 제목줄에 td대신 th를 사용하기 때문에 먼저 td로 검색하고 td의 개수가 0일 경우 th를 검색한다
	 */
	public int getColCount() {
		WebElement first_tr = _webTable.findElements(By.tagName("tr")).get(0);
		
		int chk = first_tr.findElements(By.tagName("td")).size();
		
		if(chk == 0) {
			chk = first_tr.findElements(By.tagName("th")).size();
		}
		
		return chk;
	}
	
	public String getCellContents(int row, int col) {
		if(row > getRowCount()) {
			row = getRowCount();
		}
		
		if(col > getColCount()) {
			col = getColCount();
		}
		
		WebElement target_tr = _webTable.findElements(By.tagName("tr")).get(row - 1);
		WebElement target_td = target_tr.findElements(By.tagName("td")).get(col - 1);
		
		return target_td.getText();
	}
	
	public WebElement getCell(int row, int col) {
		if(row > getRowCount()) {
			row = getRowCount();
		}
		
		if(col > getColCount()) {
			col = getColCount();
		}
		
		WebElement target_tr = _webTable.findElements(By.tagName("tr")).get(row - 1);
		WebElement target_td = target_tr.findElements(By.tagName("td")).get(col - 1);
		
		return target_td;
	}
}
