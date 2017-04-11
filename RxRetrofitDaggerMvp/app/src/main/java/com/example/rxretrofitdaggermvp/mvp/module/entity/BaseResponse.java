package com.example.rxretrofitdaggermvp.mvp.module.entity;

/**
 * Created by MrKong on 2017/4/2.
 */

public class BaseResponse<T> {
    private String reason;
    private int error_code;
    private T result;

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
