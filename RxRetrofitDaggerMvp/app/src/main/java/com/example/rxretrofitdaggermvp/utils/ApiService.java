package com.example.rxretrofitdaggermvp.utils;

import com.example.rxretrofitdaggermvp.mvp.module.entity.BaseResponse;
import com.example.rxretrofitdaggermvp.mvp.module.entity.NewsInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by MrKong on 2017/4/2.
 */

public interface ApiService {

    @GET("index")
    Observable<BaseResponse<NewsInfo>> getNews(@Query("type") String newsType,
                                               @Query("key") String key);
}
