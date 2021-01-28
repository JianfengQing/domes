package com.lve.risk.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;

import java.util.Map;

/**
 * JWT 工具类
 *
 * @author qingjianfeng
 * @date   2021-01-28
 * */
public class JWTUtils {

    /** JWT解析Token */
   public static Map<String, Claim> parseToken(String token){
        return JWT.decode(token).getClaims();
    }
}
