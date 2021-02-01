package com.lve.risk.aop;

import com.alibaba.fastjson.JSONArray;
import com.auth0.jwt.interfaces.Claim;
import com.lve.risk.annotation.Authority;
import com.lve.risk.constant.ExceptionCode;
import com.lve.risk.constant.RedisKey;
import com.lve.risk.exception.BusinessException;
import com.lve.risk.utils.JWTUtils;
import com.lve.risk.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class AuthorityAop {

    @Resource
    private RedisUtils redisUtils;

    @Value("${spring.application.name}")
    private String serviceName;

    @Pointcut("@annotation(com.lve.risk.annotation.Authority)")
    private void authorityCut() {

    }

    @Around("authorityCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targerMethod = methodSignature.getMethod();
        if (targerMethod.isAnnotationPresent(Authority.class)) {
            Authority authority = targerMethod.getAnnotation(Authority.class);
            String roleAuthority = authority.authority();
            if (StringUtils.isNotEmpty(roleAuthority)) {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                String authorization = request.getHeader("Authorization");
                Map<String, Claim> map = JWTUtils.parseToken(authorization);
                List auds = map.get("aud").asList(String.class);
                if (!auds.contains(serviceName)) {
                    throw new BusinessException(ExceptionCode.NO_SERVICE_ACCESS_CODE, ExceptionCode.NO_SERVICE_ACCESS_MSG);
                }
                String roleId = map.get("role").asString();
                String permissioRole = String.format(RedisKey.PERMISSION_ROLE, roleId);
                String roles = (String) redisUtils.get(permissioRole);
                if (StringUtils.isEmpty(roles)) {
                    throw new BusinessException(ExceptionCode.NO_ACCESS_CODE, ExceptionCode.NO_ACCESS_MSG);
                }
                List<String> roleList = JSONArray.parseArray(roles, String.class);
                boolean authorityFlag = false;
                for (String item : roleList) {
                    if (item.equals(roleAuthority)) {
                        authorityFlag = true;
                        break;
                    }
                }
                if (authorityFlag) {
                    return pjp.proceed();
                } else {
                    throw new BusinessException(ExceptionCode.NO_ACCESS_CODE, ExceptionCode.NO_ACCESS_MSG);
                }
            }
        }
        return pjp.proceed();
    }
}
