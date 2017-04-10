package com.example.rxretrofitdaggermvp.mvp.presenter.base;

import android.support.annotation.NonNull;

import com.example.rxretrofitdaggermvp.mvp.view.base.BaseView;

/**
 * Created by MrKong on 2017/4/1.
 */

public interface BasePresenter {

    void setView(@NonNull BaseView baseView);

    void initialize();

    void destroy();
}
