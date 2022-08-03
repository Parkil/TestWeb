package egovframework.example.cmmn.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.core.convert.ConversionService;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;

public class EgovBindingInitializer extends org.springframework.web.bind.support.ConfigurableWebBindingInitializer{
    
    private final ConversionService conversionService;
    private final Validator validator;

    public EgovBindingInitializer(ConversionService conversionService, Validator validator) {
        this.conversionService = conversionService;
        this.validator = validator;
    }

    protected Log log = LogFactory.getLog(this.getClass());

    @Override
    public void initBinder(WebDataBinder binder) {
        log.info("전역 Binder 실행");
        
        /*
         * binder와 converter/formatter에 동일한 DataType을 변환하는 기능이 정의되어 있으면 property-editor가 우선권을 갖는다.
         */
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.registerCustomEditor(String.class,new StringTrimmerEditor(false));
        
        //converter 서비스를 지정
        binder.setConversionService(conversionService);
        binder.setValidator(validator);
    }
}