package com.lve.risk.exception;


import java.util.List;

public class ResultData {

    private Integer code;

    private String Detailes;

    private String Message;

    private List<String> ValidationErrors;

    private ResultData Error;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDetailes() {
        return Detailes;
    }

    public void setDetailes(String detailes) {
        Detailes = detailes;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<String> getValidationErrors() {
        return ValidationErrors;
    }

    public void setValidationErrors(List<String> validationErrors) {
        ValidationErrors = validationErrors;
    }

    public ResultData getError() {
        return Error;
    }

    public void setError(ResultData error) {
        Error = error;
    }
}
