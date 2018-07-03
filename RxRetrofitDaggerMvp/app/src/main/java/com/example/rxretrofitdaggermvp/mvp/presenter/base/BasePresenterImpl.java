package com.example.rxretrofitdaggermvp.mvp.presenter.base;

import com.example.rxretrofitdaggermvp.listener.OnErrorCallBack;
import com.example.rxretrofitdaggermvp.mvp.view.base.BaseView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.example.rxretrofitdaggermvp.utils.Constant.CONNET_EXCEPTION;
import static com.example.rxretrofitdaggermvp.utils.Constant.REQUEST_TIME_OUT;
import static com.example.rxretrofitdaggermvp.utils.Constant.TOKENTIMEOUT;

/**
 * Created by MrKong on 2017/4/1.
 */
//相同逻辑的统一处理
public abstract class BasePresenterImpl<T extends BaseView> implements BasePresenter<T>, OnErrorCallBack {

    protected T mView;

    /**
     * for cancle multi subscribers.
     */
    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    public void setView(T baseView) {
        mView = baseView;
    }

    /**
     * 默认初始化方法
     */
    @Override
    public abstract void initialize();

    @Override
    public void destroy() {
        mCompositeDisposable.dispose();
    }

    @Override
    public void onError(int errorCode, String msg) {
        mView.hideLoading();
        mView.showMessage(errorCode, msg);
        switch (errorCode) {
            case REQUEST_TIME_OUT:
            case CONNET_EXCEPTION:
                mView.showNetFaileUI(errorCode, msg);
                break;
            case TOKENTIMEOUT:
                mView.goToLogin();
                break;
            default:
                break;
        }
    }
}
