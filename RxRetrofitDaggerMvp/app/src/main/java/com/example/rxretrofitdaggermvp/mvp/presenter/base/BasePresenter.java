package com.example.rxretrofitdaggermvp.mvp.presenter.base;

/**
 * Created by MrKong on 2017/4/1.
 */

public interface BasePresenter<T> {

    void setView(T baseView);

    void initialize();

    void destroy();
}
