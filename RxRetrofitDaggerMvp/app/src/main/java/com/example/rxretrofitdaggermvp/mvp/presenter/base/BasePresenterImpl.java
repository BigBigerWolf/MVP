package com.example.rxretrofitdaggermvp.mvp.presenter.base;

import com.example.rxretrofitdaggermvp.listener.OnErrorCallBack;
import com.example.rxretrofitdaggermvp.mvp.view.base.BaseView;
import com.example.rxretrofitdaggermvp.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

import static com.example.rxretrofitdaggermvp.utils.Constant.CONNET_EXCEPTION;
import static com.example.rxretrofitdaggermvp.utils.Constant.REQUEST_TIME_OUT;
import static com.example.rxretrofitdaggermvp.utils.Constant.TOKENTIMEOUT;

/**
 * Created by MrKong on 2017/4/1.
 */
//相同逻辑的统一处理
public abstract class BasePresenterImpl<T extends BaseView> implements BasePresenter<T>, OnErrorCallBack {

    protected T view;

    /**
     * for cancle multi subscribers.
     */
    protected List<Disposable> subForUnSubscribes = new ArrayList<>();

    @Override
    public void setView(T baseView) {
        view = baseView;
    }

    public abstract void initialize();

    @Override
    public void destroy() {
        for (Disposable subscription :
                subForUnSubscribes) {
            if (subscription != null && !subscription.isDisposed()) {
                subscription.dispose();
            }
        }
        subForUnSubscribes.clear();
    }

    @Override
    public void onError(int errorCode, String msg) {
        view.hideLoading();
        view.showMessage(errorCode, msg);
        switch (errorCode) {
            case REQUEST_TIME_OUT:
            case CONNET_EXCEPTION:
                view.showNetFaileUI(errorCode, msg);
                break;
            case TOKENTIMEOUT:
                view.goToLogin();
                break;
        }
    }
}
