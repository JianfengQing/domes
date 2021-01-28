package com.lve.risk.exception;

import java.io.Serializable;

/**
 * 自定义数据包
 *
 * @author qingjianfeng
 * @date   2021-01-28
 * */
public class ResponseData<T> implements Serializable {

    /**
     * 状态码：200-成功，500-失败
     * */
    private int code;

    /**
     * 错误消息，如果成功可为空或SUCCESS
     * */
    private String msg;

    /**
     * 返回结果数据
     * */
    private T data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static ResponseData success() {
        return success(200, "SUCCESS", null);
    }

    public static ResponseData success(int code, String msg, Object data) {
        ResponseData result = new ResponseData();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static ResponseData fail(String msg) {
        return fail(500, msg,null);
    }

    public static ResponseData fail(int code, String msg, Object data) {
        ResponseData result = new ResponseData();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

}
