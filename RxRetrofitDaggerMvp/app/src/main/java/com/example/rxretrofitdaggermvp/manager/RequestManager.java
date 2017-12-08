package com.example.rxretrofitdaggermvp.manager;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.rxretrofitdaggermvp.BuildConfig;
import com.example.rxretrofitdaggermvp.mvp.module.entity.BaseResponse;
import com.example.rxretrofitdaggermvp.subsriber.ResponseSubscriber;
import com.example.rxretrofitdaggermvp.utils.Constant;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

import static com.example.rxretrofitdaggermvp.utils.Constant.SUCCESS;

/**
 * Created by MrKong on 2017/9/25.
 * 请求数据实现类
 */

public class RequestManager {
    private Retrofit retrofit;
    private int mConnetTimeout;
    private int mReadTimeout;
    private int mWriteTimeout;

    public RequestManager() {
        LoggingInterceptor loggingInterceptor = new LoggingInterceptor.Builder()
                .loggable(Constant.isDebug)
                .setLevel(Level.BASIC)
                .log(Log.INFO)
                .request("Request")
                .response("Response")
                .addHeader("version", BuildConfig.VERSION_NAME)
                .build();

        retrofit = new RetrofitManager()
                .baseUrl(Constant.BASE_URL)
                .writeTimeout(mWriteTimeout, TimeUnit.SECONDS)
                .readTimeout(mReadTimeout, TimeUnit.SECONDS)
                .connectTimeout(mConnetTimeout, TimeUnit.SECONDS)
                .addLoggingIntercepter(loggingInterceptor)
                .addRequestIntercepter(new RequestIntercepter())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .init();
    }

    /**
     * 设置连接时间
     * @param connectTimeout
     * @return
     */
    public RequestManager setConnectTimeout(int connectTimeout) {
        this.mConnetTimeout = connectTimeout;
        return this;
    }

    /**
     * 设置读取时间
     * @param readTimeout
     * @return
     */
    public RequestManager setReadTimeout(int readTimeout) {
        this.mReadTimeout = readTimeout;
        return this;
    }

    /**
     * 设置写入时间
     * @param writeTimeout
     * @return
     */
    public RequestManager setWriteTimeout(int writeTimeout) {
        this.mWriteTimeout = writeTimeout;
        return this;
    }

    /**
     * 定义apiservice
     *
     * @param apiService
     * @param <T>
     * @return
     */
    public <T> T createApiService(Class<T> apiService) {
        return retrofit.create(apiService);
    }

    /**
     * 二次处理observable
     *
     * @param observable
     * @param responseObserver
     * @param <T>
     * @return
     */
    public <T> Disposable getDisposable(Observable<BaseResponse<T>> observable, final ResponseSubscriber<BaseResponse<T>> responseObserver) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<BaseResponse<T>, Observable<BaseResponse<T>>>() {
                    @Override
                    public Observable<BaseResponse<T>> apply(@NonNull BaseResponse<T> tBaseResponse) throws Exception {
                        int result = tBaseResponse.getError_code();
                        if (result == SUCCESS) {
                            return Observable.just(tBaseResponse);
                        } else {
                            throw new ExceptionManger.ServerException(tBaseResponse.getError_code(), tBaseResponse.getReason());
                        }
                    }
                }).onErrorResumeNext(new Function<Throwable, ObservableSource<BaseResponse<T>>>() {
                    @Override
                    public ObservableSource<BaseResponse<T>> apply(@NonNull Throwable throwable) throws Exception {
                        return Observable.error(throwable);
                    }
                }).subscribe(new Consumer<BaseResponse<T>>() {
                    @Override
                    public void accept(BaseResponse<T> t) throws Exception {
                        if (responseObserver != null) responseObserver.onNext(t);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (responseObserver != null) responseObserver.onError(throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        if (responseObserver != null) responseObserver.onComplete();
                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (responseObserver != null) responseObserver.onSubscribe(disposable);
                    }
                });
    }

}
