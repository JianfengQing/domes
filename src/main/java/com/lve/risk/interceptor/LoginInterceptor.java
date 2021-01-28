package com.lve.risk.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.lve.risk.exception.BusinessException;
import com.lve.risk.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


public class LoginInterceptor implements HandlerInterceptor {

    @Value("${spring.application.name}")
    private String serviceName;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        Map<String, Claim> map = JWTUtils.parseToken(authorization);
        List auds = map.get("aud").asList(String.class);
        if (!auds.contains(serviceName)){
            throw new BusinessException("200", "无访问权限");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
