package egovframework.example.cmmn.viewresolver;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.lang.NonNull;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

/*
 * Custom View Resolver
 * 
 * [view이름, View객체]를 가진 map을 저장한다.
 * map에 저장된 view객체는 new ModelAndView("view이름")으로 가져다 쓸수 있다.
 */
public class SimpleConfigurableViewResolver implements ViewResolver {
    private Map<String, ? extends View> views = new HashMap<>();
    
    @Override
    public View resolveViewName(@NonNull String viewName, @NonNull Locale locale) {
        return this.views.get(viewName);
    }
    
    public void setViews(Map<String, ? extends View> views) {
        this.views = views;
    }
}
