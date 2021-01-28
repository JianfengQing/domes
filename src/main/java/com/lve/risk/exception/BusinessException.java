package com.lve.risk.exception;

public class BusinessException extends RuntimeException{

    private String code;

    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public BusinessException(String code, String msg){
      this.code = code;
      this.msg = msg;
   }

    public BusinessException(String msg){
        this.msg = msg;
    }
}
