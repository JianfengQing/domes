package com.lve.risk.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.lve.risk.exception.BusinessException;
import com.lve.risk.exception.ResultData;
import com.lve.risk.utils.DateUtils;
import com.lve.risk.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class LoginInterceptor implements HandlerInterceptor {

    @Value("${spring.application.name}")
    private String serviceName;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        Map<String, Claim> map = JWTUtils.parseToken(authorization);
        long expires = map.get("exp").asInt();
        long expiresTime = expires * 1000;
        long currentTime = System.currentTimeMillis();
        String date = DateUtils.secondToDate(expires);
        int timeDifference = (int) ((currentTime-expiresTime) / 1000 / 60) + 1;
        if (timeDifference >= 0){   //过期
            ResultData resultDataError = new ResultData();
            resultDataError.setCode(401);
            resultDataError.setDetailes("");
            resultDataError.setMessage("");
            resultDataError.setValidationErrors(null);
            resultDataError.setError(new ResultData());
            ResultData resultData = new ResultData();
            resultData.setCode(401);
            resultData.setDetailes("");
            resultData.setMessage("");
            resultData.setValidationErrors(null);
            resultData.setError(resultDataError);
            responseWriterData(response, resultData);
        }
        if (timeDifference < 0 && timeDifference >= -5){  //需要跟新token


        }
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

    private void responseWriterData(HttpServletResponse response, ResultData resultData){
        response.setStatus(401);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        String res = JSON.toJSONString(resultData);
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.append(res.toString());
    }
}
