package egovframework.example.cmmn.interceptor;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

/*
 * header의 referer 속성을 이용하여 해당 URL을 호출한 전 URL로 redirect를 시키는 예제
 */
public class RefererInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest req,
			HttpServletResponse resp, Object arg2, Exception arg3)
			throws Exception {
		//미구현
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse resp,
			Object arg2, ModelAndView arg3) throws Exception {
		//미구현
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp,
			Object arg2) throws Exception {
		
		/*
		Map<String,String[]> map = (Map<String,String[]>)req.getParameterMap();
		map.put("selectedId", map.get("id"));

		String referer = req.getHeader("Referer");
		System.out.println("테스트 : "+referer);
		System.out.println("Object : "+arg2);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:"+referer);
		mav.addAllObjects(map);
		throw new ModelAndViewDefiningException(mav);
		*/
		return true;
	}

}
