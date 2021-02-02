package com.lve.risk.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.lve.risk.constant.ExceptionCode;
import com.lve.risk.constant.RedisKey;
import com.lve.risk.constant.ResponseMessage;
import com.lve.risk.exception.BusinessException;
import com.lve.risk.exception.ResponseData;
import com.lve.risk.exception.ResponseResultData;
import com.lve.risk.utils.HttpClientUtils;
import com.lve.risk.utils.JWTUtils;
import com.lve.risk.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginInterceptor implements HandlerInterceptor {

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
            String refreshTokenKey = String.format(RedisKey.REFRESH_TOKEN, userId);
            String authorizationToken = (String) redisUtils.get(refreshTokenKey);
            params.put("refreshToken", authorizationToken);
            String newToken = httpClientUtils.postSendMsg(refreshUrl, JSON.toJSONString(params));
            if (StringUtils.isEmpty(newToken)) {
                throw new BusinessException(ExceptionCode.NEW_TOKEN_NOT_IS_EXIST_CODE, ExceptionCode.NEW_TOKEN_NOT_IS_EXIST_MSG);
            }
            Map<String, Object> newTokenMap = (Map<String, Object>) JSONObject.parse(newToken);
            String refreshToken = (String) newTokenMap.get("refresh_token");
            redisUtils.set(refreshTokenKey, refreshToken, 60 * 60 * 24 * 30);
            String accessToken = (String) newTokenMap.get("access_token");
            responseWriterRefreshToken(response, userId, ResponseData.success(200, ResponseMessage.TOKEN_REFRESH_SUCCESSFULLY, accessToken));
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    //token 过期
    private void responseWriterExpire(HttpServletResponse response) {
        ResponseResultData resultDataError = new ResponseResultData();
        resultDataError.setCode(401);
        resultDataError.setDetailes("");
        resultDataError.setMessage("");
        resultDataError.setValidationErrors(null);
        resultDataError.setError(new ResponseResultData());
        ResponseResultData resultData = new ResponseResultData();
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

    //刷新token
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
