package com.lue.risk.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截跨域处理
 *
 * @author qingjianfeng
 * @date   2021-01-28
 * */
public class CrossInterceptor implements HandlerInterceptor {
    //业务处理器处理请求之前被调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "3600");
        System.out.println("------------------------------拦截器--------------------------------");

        // response.setHeader("Access-Control-Allow-Headers", "Authorization"); //Token 校验
        //response.setHeader("Access-Control-Allow-Origin","Origin");
        //response.setHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, OPTIONS");
        return true;
    }

    //业务处理器处理请求执行完成后被调用
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
    //在DispatcherServlet完全处理完请求后被调用
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
