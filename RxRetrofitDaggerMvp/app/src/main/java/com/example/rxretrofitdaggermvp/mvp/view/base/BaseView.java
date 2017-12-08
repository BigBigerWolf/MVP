package com.example.rxretrofitdaggermvp.mvp.view.base;

/**
 * Created by MrKong on 2017/4/1.
 */

public interface BaseView {
    /**
     * token失效,跳转登陆页面
     */
    void goToLogin();

    /**
     * 请求过程中，加载
     */
    void onLoading();

    /**
     * 请求完毕，展示
     */
    void hideLoading();

    /**
     * 失败回调
     * @param errorCode 错误码
     * @param msg 错误信息
     */
    void showMessage(int errorCode, String msg);

    /**
     * 接口超时回调
     * @param errorCode
     * @param msg
     */
    void showNetFaileUI(int errorCode, String msg);
}
