package egovframework.example.sample.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/*
 * GET,POST외의 method를 가진 Controller에서 jsp view를 반환하게 되면 해당 Controller에서는 처음에 호출된 method를
 * 계속 가지고 가기때문에 405에러가 발생한다. 이를 방지하기 위해서 method를 변경하는 필터를 추가한다 
 */
public class GetMethodConvertingFilter implements Filter {

    private final Logger log = LoggerFactory.getLogger(GetMethodConvertingFilter.class);

    @Override
    public void init(FilterConfig config) throws ServletException {
        // do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(wrapRequest((HttpServletRequest) request), response);
    }

    @Override
    public void destroy() {
        // do nothing
    }

    private static HttpServletRequestWrapper wrapRequest(HttpServletRequest request) {
        return new HttpServletRequestWrapper(request) {
            @Override
            public String getMethod() {
                return "GET";
            }
        };
    }
}
