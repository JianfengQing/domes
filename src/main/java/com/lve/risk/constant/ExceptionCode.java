package com.lve.risk.constant;

public class ExceptionCode {
    /** 更新token失败 编码*/
    public static final String NEW_TOKEN_NOT_IS_EXIST_CODE = "NEW_TOKEN_NOT_IS_EXIST";
    /** 更新token失败 信息*/
    public static final String NEW_TOKEN_NOT_IS_EXIST_MSG = "身份信息已过期,请您重新登陆";

    /** 无服务器访问权限 编码*/
    public static final String NO_SERVICE_ACCESS_CODE = "NO_ACCESS_CODE";
    /** 无服务器访问权限 信息*/
    public static final String NO_SERVICE_ACCESS_MSG = "无服务器访问权限";

    /** 角色不存在 编码*/
    public static final String NO_ACCESS_CODE = "ROLE_NOT_IS_EXIST";
    /** 角色不存在 信息*/
    public static final String NO_ACCESS_MSG = "无访问权限";
}
