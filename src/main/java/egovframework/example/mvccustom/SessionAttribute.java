package egovframework.example.mvccustom;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 사용자 정의 어노테이션 지정
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SessionAttribute {
    String value() default "";
    
    boolean required() default false;
    
    boolean exposeAsModelAttribute() default false;
}
