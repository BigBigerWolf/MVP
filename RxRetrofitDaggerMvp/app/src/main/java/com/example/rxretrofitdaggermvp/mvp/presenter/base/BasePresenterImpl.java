package com.example.rxretrofitdaggermvp.mvp.presenter.base;

import com.example.rxretrofitdaggermvp.listener.OnErrorCallBack;
import com.example.rxretrofitdaggermvp.mvp.view.base.BaseView;
import com.example.rxretrofitdaggermvp.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by MrKong on 2017/4/1.
 */
//相同逻辑的统一处理
public abstract class BasePresenterImpl<T extends BaseView> implements BasePresenter<T>, OnErrorCallBack {

    protected T view;

    /**
     *  for cancle multi subscribers.
     */
    protected List<Subscription> subForUnSubscribes = new ArrayList<>();

    @Override
    public void setView(T baseView) {
        view = baseView;
    }

    public abstract void initialize();

    @Override
    public void destroy() {
        for (Subscription subscription :
                subForUnSubscribes) {
            if (subscription != null && !subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
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
