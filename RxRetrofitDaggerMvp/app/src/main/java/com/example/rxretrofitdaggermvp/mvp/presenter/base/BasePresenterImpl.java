package com.example.rxretrofitdaggermvp.mvp.presenter.base;

import android.support.annotation.NonNull;

import com.example.rxretrofitdaggermvp.listener.OnErrorCallBack;
import com.example.rxretrofitdaggermvp.mvp.view.base.BaseView;
import com.example.rxretrofitdaggermvp.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by MrKong on 2017/4/1.
 */

public abstract class BasePresenterImpl<T extends BaseView> implements BasePresenter, OnErrorCallBack {

    protected T view;

    /**
     *  for cancle multi subscribers.
     */
    protected CompositeSubscription subForUnSubscribes;

    @Override
    public void setView(@NonNull BaseView baseView) {
        view = (T) baseView;
    }

    public abstract void initialize();

    @Override
    public void destroy() {
        if (subForUnSubscribes != null && subForUnSubscribes.isUnsubscribed()) {
            subForUnSubscribes.unsubscribe();
        }
        subForUnSubscribes.clear();
    }

    @Override
    public void onError(int errorCode, String msg) {
        view.hideLoading();
        if (errorCode == Constant.TOKENTIMEOUT) {
            view.goToLogin();
        }
        view.showMessage(errorCode, msg);
    }
}
