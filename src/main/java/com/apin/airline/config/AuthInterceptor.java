package com.apin.airline.config;

import com.apin.util.pojo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 宣炳刚
 * @date 2017/10/26
 * @remark 身份验证/鉴权拦截器
 */
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    RestTemplate restTemplate;

    /**
     * 处理前回调方法,对接口访问请求进行身份验证和鉴权
     *
     * @param request
     * @param response
     * @param handler
     * @return 是否通过验证/鉴权
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 从HttpServletRequest获取参数
        String key = request.getRequestURI();
        String token = request.getHeader("Authorization");

        String url = "/bu/authapi/v1.1/tokens/secret?function=" + key;
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", token);
        HttpEntity<Reply> requestEntity = new HttpEntity<>(null, requestHeaders);

        ResponseEntity<Reply> result = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Reply.class);
        return result.getBody().getSuccess();
    }

    /**
     * 处理后回调方法
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 响应完成回调方法
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
