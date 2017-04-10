package com.example.rxretrofitdaggermvp.manager;

import android.support.annotation.NonNull;
import android.util.Log;

import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;
import com.example.rxretrofitdaggermvp.BuildConfig;
import com.example.rxretrofitdaggermvp.MyApp;
import com.example.rxretrofitdaggermvp.mvp.module.entity.BaseResponse;
import com.example.rxretrofitdaggermvp.utils.ApiService;
import com.example.rxretrofitdaggermvp.utils.Constant;
import com.example.rxretrofitdaggermvp.utils.LogUtil;
import com.example.rxretrofitdaggermvp.utils.Netutil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by MrKong on 2017/4/2.
 */

public class HttpManager {
    private OkHttpClient mOkHttpClient;
    private ApiService apiService;

    /**
     * 设缓存有效期为两天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;

    /**
     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     * max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。
     */
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;

    /**
     * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
     */
    private static final String CACHE_CONTROL_AGE = "max-age=0";


    private HttpManager() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(MyGsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }

    private static HttpManager httpManager;

    public static HttpManager getInstance() {
        if (httpManager == null) {
            synchronized (HttpManager.class) {
                if (httpManager == null) {
                    httpManager = new HttpManager();
                }
            }
        }
        return httpManager;
    }

    private OkHttpClient getOkHttpClient() {

        if (mOkHttpClient == null) {
            synchronized (HttpManager.class) {
                Cache okHttpCache = new Cache(new File(MyApp.getAppContext().getCacheDir(), "OkHttpCache"), 1024 * 1024 * 100);
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient.Builder().cache(okHttpCache)
                            .connectTimeout(6, TimeUnit.SECONDS)
                            .readTimeout(6, TimeUnit.SECONDS)
                            .writeTimeout(6, TimeUnit.SECONDS)
                            .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(loggingInterceptor)
                            .build();
                }
            }
        }
        return mOkHttpClient;
    }

    /**
     * 配置离线缓存
     */
    private final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            if (!Netutil.isNetworkAvailable()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                LogUtil.d("netStatus", "no network");
            }
            Response originalResponse = chain.proceed(request);
            if (Netutil.isNetworkAvailable()) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    /**
     * 设置拦截打印器
     */
    private LoggingInterceptor loggingInterceptor = new LoggingInterceptor.Builder()
            .loggable(BuildConfig.DEBUG)
            .setLevel(Level.BASIC)
            .log(Log.INFO)
            .request("Request")
            .response("Response")
            .addHeader("version", BuildConfig.VERSION_NAME)
            .build();


    /**
     * 根据网络状况获取缓存的策略
     */
    @NonNull
    public static String getCacheControl() {
        return Netutil.isNetworkAvailable() ? CACHE_CONTROL_AGE : CACHE_CONTROL_CACHE;
    }

    /**
     * 对Observable的二次封装
     * @param observable
     * @param subscriber
     * @param <T>
     * @return
     */
    public <T> Subscription getSubscribtion(Observable<BaseResponse<T>> observable, Subscriber<T> subscriber) {
        return observable
                .compose(this.<BaseResponse<T>>defaultSchedulers())
                .flatMap(new FunBaseResponse<T>())
                .retryWhen(new RetryMechanism())
                .subscribe(subscriber);
    }

    /**
     * 请求成功或失败的处理。
     *
     * @param <T>
     */
    private static class FunBaseResponse<T> implements Func1<BaseResponse<T>, Observable<T>> {
        @Override
        public Observable<T> call(BaseResponse<T> baseResResponse) {
            return Observable.just(baseResResponse.getResult());
        }
    }

    /**
     * 默认订阅线程调度
     *
     * @param <T>
     * @return
     */
    private <T> Observable.Transformer<T, T> defaultSchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 实现Rx重试机制
     */
    private static class RetryMechanism implements Func1<Observable<? extends Throwable>, Observable<?>> {
        @Override
        public Observable<?> call(Observable<? extends Throwable> observable) {
            return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                @Override
                public Observable<?> call(Throwable throwable) {
                    if (throwable instanceof ExceptionManger.ServerException) {
                        ExceptionManger.ServerException serverException = (ExceptionManger.ServerException) throwable;
                        LogUtil.e(serverException.code + serverException.message);
                        if (serverException.code == Constant.TOKENTIMEOUT) {
                            // TODO: 2017/3/31  在这里可以启用token过期，静默登陆机制；
                        }
                    }
                    return Observable.error(throwable);
                }
            });
        }
    }
}
