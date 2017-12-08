package com.example.rxretrofitdaggermvp.subsriber;

import com.example.rxretrofitdaggermvp.listener.OnErrorCallBack;
import com.example.rxretrofitdaggermvp.manager.ExceptionManger;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by MrKong on 2017/4/2.
 */
//处理成功失败的回调数据
public abstract class ResponseSubscriber<T> implements Observer<T> {

    private OnErrorCallBack onErrorCallBack;

    public ResponseSubscriber(OnErrorCallBack onErrorCallBack) {
        this.onErrorCallBack = onErrorCallBack;
    }

    @Override
    public void onComplete() {
        // TODO: 2017/12/8
    }

    @Override
    public void onError(Throwable e) {
        ExceptionManger.ApiException apiException = ExceptionManger.handleException(e);
        if (onErrorCallBack == null) return;
        onErrorCallBack.onError(apiException.code, apiException.message);
    }

    @Override
    public void onSubscribe(Disposable d) {
        // TODO: 2017/12/8
    }

    public abstract void onSuccess(T t);

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }
}
