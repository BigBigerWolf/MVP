package com.example.rxretrofitdaggermvp.utils;

import com.example.rxretrofitdaggermvp.mvp.module.entity.BaseResponse;
import com.example.rxretrofitdaggermvp.mvp.module.entity.NewsInfo;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by MrKong on 2017/4/2.
 */

public interface ApiService {

    @GET("index")
    Observable<BaseResponse<NewsInfo>> getNews(@Header("Cache-Control") String cacheControl,
                                                     @Query("type") String newsType,
                                                     @Query("key") String key);
}
