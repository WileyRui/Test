package com.apin.airline.config;

import com.apinji.utils.log.MDCInsertingServletFilter;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by zhaowei on 2017/10/26.
 */
@Component
@WebFilter(urlPatterns = {"/*"})
public class MDCLogFilter implements Filter {
    MDCInsertingServletFilter filter = new MDCInsertingServletFilter();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
     filter.doFilter(servletRequest,servletResponse,filterChain);
    }

    @Override
    public void destroy() {

    }
}
