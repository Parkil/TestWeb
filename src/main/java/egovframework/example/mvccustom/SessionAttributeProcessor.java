package egovframework.example.mvccustom;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/*
 * 해댱 어노테이션의 상세동작을 지정
 */
public class SessionAttributeProcessor implements HandlerMethodArgumentResolver, HandlerMethodReturnValueHandler {

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return returnType.getMethodAnnotation(SessionAttribute.class) != null;
	}

	@Override
	public void handleReturnValue(
				Object returnValue,
				MethodParameter returnType,
				ModelAndViewContainer mavContainer,
				NativeWebRequest webRequest) throws Exception {
		SessionAttribute annotation;
		annotation = returnType.getMethodAnnotation(SessionAttribute.class);
		webRequest.setAttribute(annotation.value(), returnValue, NativeWebRequest.SCOPE_SESSION);
		exposeModelAttribute(annotation, returnValue, mavContainer);
	}
	
	private void exposeModelAttribute(SessionAttribute annotation, Object value, ModelAndViewContainer mavContainer) {
		if(annotation.exposeAsModelAttribute()) {
			mavContainer.addAttribute(annotation.value(), value);
		}
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(SessionAttribute.class);
	}

	@Override
	public Object resolveArgument(
				MethodParameter parameter,
				ModelAndViewContainer mavContainer,
				NativeWebRequest webRequest,
				WebDataBinderFactory binderFactory) throws Exception {
		SessionAttribute annotation;
		annotation = parameter.getParameterAnnotation(SessionAttribute.class);
		
		Object value = webRequest.getAttribute(annotation.value(), NativeWebRequest.SCOPE_SESSION);
		
		if(value == null && annotation.required()) {
			throw new MissingServletRequestParameterException(annotation.value(), parameter.getParameterType().getName());
		}
		
		exposeModelAttribute(annotation, value, mavContainer);
		return value;
	}

}
