package egovframework.example.cmmn.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/*
 * Interceptor 샘플
 */
public class SampleInterceptor implements HandlerInterceptor {

    protected Log log = LogFactory.getLog(this.getClass());

    @Override
    public void afterCompletion(HttpServletRequest req,
            HttpServletResponse resp, Object arg2, Exception arg3)
            throws Exception {
        log.info("SampleInterceptor.afterCompletion called");
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse resp,
            Object arg2, ModelAndView arg3) throws Exception {
        log.info("SampleInterceptor.postHandle called");
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp,
            Object arg2) throws Exception {
        log.info("SampleInterceptor.preHandle called");
        return true;
    }
}
