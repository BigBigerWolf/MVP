package com.example.rxretrofitdaggermvp.mvp.module.entity;

/**
 * Created by MrKong on 2017/4/2.
 */
//响应结果基类
public class BaseResponse<T> {
    private String reason;
    private int error_code;
    private T result;

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public int getError_code() {
        return error_code;
    }

    public T getResult() {
        return result;
    }
}
