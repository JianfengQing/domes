package com.lve.risk.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.lve.risk.constant.ExceptionCode;
import com.lve.risk.constant.RedisKey;
import com.lve.risk.exception.BusinessException;
import com.lve.risk.exception.ResponseData;
import com.lve.risk.exception.ResultData;
import com.lve.risk.utils.DateUtils;
import com.lve.risk.utils.HttpClientUtils;
import com.lve.risk.utils.JWTUtils;
import com.lve.risk.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginInterceptor implements HandlerInterceptor {

    @Value("${spring.application.name}")
    private String serviceName;

    @Value("${refresh.url}")
    private String refreshUrl;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private HttpClientUtils httpClientUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        Map<String, Claim> map = JWTUtils.parseToken(authorization);
        long expires = map.get("exp").asInt();
        long expiresTime = expires * 1000;
        long currentTime = System.currentTimeMillis();
        int timeDifference = (int) ((currentTime - expiresTime) / 1000 / 60) + 1;
        if (timeDifference >= 0) {
            responseWriterExpire(response);
            return false;
        }
        if (timeDifference < 0 && timeDifference >= -5) {
            String clientId = map.get("client_id").asString();
            String userId = map.get("sub").asString();
            Map<String, Object> params = new HashMap<String, Object>(5);
            params.put("clientId", clientId);
            String refreshTokenKey = String.format(RedisKey.Refresh_Token, userId);
            String authorizationToken = (String) redisUtils.get(refreshTokenKey);
            params.put("refreshToken", authorizationToken);
            String newToken = httpClientUtils.postSendMsg(refreshUrl, JSON.toJSONString(params));
            if (StringUtils.isEmpty(newToken)) {
                throw new BusinessException(ExceptionCode.NEW_TOKEN_NOT_IS_EXIST, "身份信息已过期,请您重新登陆");
            }
            Map<String, Object> newTokenMap = (Map<String, Object>) JSONObject.parse(newToken);
            String refreshToken = (String) newTokenMap.get("refresh_token");
            redisUtils.set(refreshTokenKey, refreshToken, 60 * 60 * 24 * 30);
            String accessToken = (String) newTokenMap.get("access_token");
            ckeckPermissios(JWTUtils.parseToken(accessToken));
            responseWriterRefreshToken(response, userId, ResponseData.success(200, "刷新Token成功", accessToken));
            return true;
        }
        ckeckPermissios(map);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private void ckeckPermissios(Map<String, Claim> map){
        //权限检查
        String roleId = map.get("role").asString();
        String permissioRole = String.format(RedisKey.Permission_Role, roleId);
        String roles = (String) redisUtils.get(permissioRole);
        if (StringUtils.isEmpty(roles)) {
            throw new BusinessException(ExceptionCode.ROLE_NOT_IS_EXIST, "无访问权限");
        }
        List<String> roleList = JSONArray.parseArray(roles, String.class);
        // roleList.contains()
        //服务权限
        List auds = map.get("aud").asList(String.class);
        if (!auds.contains(serviceName)) {
            throw new BusinessException("200", "无访问权限");
        }
    }

    private void responseWriterExpire(HttpServletResponse response) {
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
        response.setStatus(401);
        response.setHeader("ACT", "Expired");
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

    private void responseWriterRefreshToken(HttpServletResponse response, String userId, ResponseData responseData) {
        response.setStatus(401);
        response.setHeader("NewToken", "Member_Refresh_Token_" + userId);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.append(JSON.toJSONString(responseData));
    }
}
