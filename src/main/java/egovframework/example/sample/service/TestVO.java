package egovframework.example.sample.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TestVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6635406490585768900L;
	
	private Map<String,String> map1;
	private List<String> list1;
	
	public Map<String, String> getMap1() {
		return map1;
	}
	public void setMap1(Map<String, String> map1) {
		this.map1 = map1;
	}
	public List<String> getList1() {
		return list1;
	}
	public void setList1(List<String> list1) {
		this.list1 = list1;
	}
}
