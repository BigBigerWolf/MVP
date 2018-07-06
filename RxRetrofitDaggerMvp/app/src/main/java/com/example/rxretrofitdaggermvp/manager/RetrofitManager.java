package com.example.rxretrofitdaggermvp.manager;

import android.text.TextUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by MrKong on 2017/9/25.
 * retrofit配置类
 */

public class RetrofitManager {

    private static final int DEFAULT_TIMEOUT = 15;
    private OkHttpClient.Builder okHttpClientBuilder;
    private Retrofit.Builder retrofitBuilder;
    private String baseUrl;
    private Converter.Factory converterFactory;
    private CallAdapter.Factory callAdapterFactory;
    private Interceptor loggingIntercepter;
    private Interceptor requestIntercepter;

    public RetrofitManager() {
        okHttpClientBuilder = new OkHttpClient.Builder();
        retrofitBuilder = new Retrofit.Builder();
    }

    /**
     * 配置baseUrl
     *
     * @param baseUrl
     * @return
     */
    public RetrofitManager baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    /**
     * 设置超时时间
     *
     * @param timeout
     * @param timeUnit
     * @return
     */
    public RetrofitManager connectTimeout(int timeout, TimeUnit timeUnit) {
        if (timeout > 0) {
            okHttpClientBuilder.connectTimeout(timeout, timeUnit);
        } else {
            okHttpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }
        return this;
    }

    /**
     * 设置写入超时时间
     *
     * @param timeout
     * @param unit
     * @return
     */
    public RetrofitManager writeTimeout(int timeout, TimeUnit unit) {
        if (timeout > 0) {
            okHttpClientBuilder.writeTimeout(timeout, unit);
        } else {
            okHttpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }
        return this;
    }

    /**
     * 设置读取超时时间
     *
     * @param timeout
     * @param unit
     * @return
     */
    public RetrofitManager readTimeout(int timeout, TimeUnit unit) {
        if (timeout > 0) {
            okHttpClientBuilder.readTimeout(timeout, unit);
        } else {
            okHttpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }
        return this;
    }

    /**
     * 添加转换器工厂
     *
     * @param factory
     * @return
     */
    public RetrofitManager addConverterFactory(Converter.Factory factory) {
        this.converterFactory = factory;
        return this;
    }

    /**
     * 添加calladapter 如：RxCallAdapterFactory
     *
     * @param factory
     * @return
     */
    public RetrofitManager addCallAdapterFactory(CallAdapter.Factory factory) {
        this.callAdapterFactory = factory;
        return this;
    }

    /**
     * 添加打印拦截器
     *
     * @param interceptor
     * @return
     */
    public RetrofitManager addLoggingIntercepter(Interceptor interceptor) {
        this.loggingIntercepter = interceptor;
        return this;
    }

    /**
     * 添加请求拦截器
     *
     * @param interceptor
     * @return
     */
    public RetrofitManager addRequestIntercepter(Interceptor interceptor) {
        this.requestIntercepter = interceptor;
        return this;
    }

    public Retrofit init() {
        if (TextUtils.isEmpty(baseUrl)) {
            throw new NullPointerException("baseUrl should be set!");
        }

        if (converterFactory == null) {
            throw new NullPointerException("converterFactory should be set!");
        }

        if (callAdapterFactory == null) {
            throw new NullPointerException("callAdapterFactory should be set!");
        }

        if (loggingIntercepter != null) {
            okHttpClientBuilder.addInterceptor(loggingIntercepter);
        }

        if (requestIntercepter != null) {
            okHttpClientBuilder.addInterceptor(requestIntercepter);
        }

        return retrofitBuilder
                .baseUrl(baseUrl)
                .client(okHttpClientBuilder.build())
                .addCallAdapterFactory(callAdapterFactory)
                .addConverterFactory(converterFactory)
                .build();
    }

}
