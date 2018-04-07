package egovframework.example.mvccustom;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestDataValueProcessor;

/*
 * 현재 까지 확인한 바로는 spring tag에서만 작동하는것으로 보이는데 좀더 확인이 필요
 */
public class CustomDataValueProcessor implements RequestDataValueProcessor {
	/*
	 * 화면표시시 form - action을 return 값으로 변경
	 */
	@Override
	public String processAction(HttpServletRequest request, String action) {
		
		System.out.println("==========>CustomDataValueProcessor processAction "+action);
		// TODO Auto-generated method stub
		return action;
	}

	/*
	 * form의 값을 가져오는데 단 조건이 있다. 
	 * 1.form tag내에 있는 값이어야 하고
	 * 2.@RequestParam이나 @ModelAttribute에서 Binding된 변수여야 함
	 * 
	 * 1,2조건에 맞는 값만 가져온다.
	 */
	@Override
	public String processFormFieldValue(HttpServletRequest request, String name, String value, String type) {
		System.out.println("==========>CustomDataValueProcessor processFormFieldValue "+name+","+value+","+type); //type이 text인 경우에만 return type에 따라서 값이 변경됨
		// TODO Auto-generated method stub
		return value; //여기서 return 하는 값이 화면에 표시됨
	}

	/*
	 * return 되는 map의 값을 화면 표시시 자동으로 form의 hidden값으로 추가한다. null일 경우에는 작동하지 않음
	 */
	@Override
	public Map<String, String> getExtraHiddenFields(HttpServletRequest request) {
		System.out.println("==========>CustomDataValueProcessor getExtraHiddenFields");
		Map<String,String> test = new HashMap<String,String>();
		test.put("test1", "첫번째 값");
		test.put("test2", "두번째 값");
		return test;
	}

	@Override
	public String processUrl(HttpServletRequest request, String url) {
		System.out.println("==========>CustomDataValueProcessor processUrl "+url);
		// TODO Auto-generated method stub
		return null;
	}

}
