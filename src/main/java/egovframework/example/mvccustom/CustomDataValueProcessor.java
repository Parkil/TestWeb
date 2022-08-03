package egovframework.example.mvccustom;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.lang.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.RequestDataValueProcessor;

/*
 * 현재 까지 확인한 바로는 spring tag에서만 작동하는것으로 보이는데 좀더 확인이 필요
 */
public class CustomDataValueProcessor implements RequestDataValueProcessor {
    
    private final Logger log = LoggerFactory.getLogger(CustomDataValueProcessor.class);
    
    /*
     * 화면표시시 form - action을 return 값으로 변경
     */
    @Override
    public String processAction(@NonNull HttpServletRequest request, @NonNull String action, @NonNull String httpMethod) {
        log.info("CustomDataValueProcessor.processAction called");
        return action;
    }

    /*
     * 1.form tag내에 있는 값이어야 하고
     * 2.@RequestParam이나 @ModelAttribute에서 Binding된 변수여야 함
     * 
     * 1,2조건에 맞는 form의 값을 가져온다
     */
    @Override
    public String processFormFieldValue(@NonNull HttpServletRequest request, String name, String value, String type) {
        log.info(String.format("CustomDataValueProcessor.processAction called %1$s,%2$s,%3$s", name, value, type));
        return value;
    }

    /*
     * return 되는 map의 값을 화면 표시시 자동으로 form의 hidden값으로 추가한다. null일 경우에는 작동하지 않음
     */
    @Override
    public Map<String, String> getExtraHiddenFields(HttpServletRequest request) {
        log.info("CustomDataValueProcessor.getExtraHiddenFields called");
        Map<String,String> test = new HashMap<>();
        test.put("test1", "첫번째 값");
        test.put("test2", "두번째 값");
        return test;
    }

    @Override
    public String processUrl(HttpServletRequest request, String url) {
        log.info("CustomDataValueProcessor.getExtraHiddenFields {}", url);
        return null;
    }
}
