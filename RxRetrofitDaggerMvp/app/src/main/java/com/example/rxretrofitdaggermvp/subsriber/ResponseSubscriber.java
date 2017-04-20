package com.example.rxretrofitdaggermvp.subsriber;

import com.example.rxretrofitdaggermvp.listener.OnErrorCallBack;
import com.example.rxretrofitdaggermvp.manager.ExceptionManger;

import rx.Subscriber;

/**
 * Created by MrKong on 2017/4/2.
 */
//处理成功失败的回调数据
public abstract class ResponseSubscriber<T> extends Subscriber<T> {

    private OnErrorCallBack onErrorCallBack;

    public ResponseSubscriber(OnErrorCallBack onErrorCallBack) {
        this.onErrorCallBack = onErrorCallBack;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

        ExceptionManger.ApiException apiException = ExceptionManger.handleException(e);
        onErrorCallBack.onError(apiException.code, apiException.message);
    }

    public abstract void onSuccess(T t);

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }
}
