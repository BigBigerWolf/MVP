package com.example.rxretrofitdaggermvp.mvp.module.interactor.imp;

import com.example.rxretrofitdaggermvp.manager.RequestManager;
import com.example.rxretrofitdaggermvp.mvp.module.entity.BaseResponse;
import com.example.rxretrofitdaggermvp.mvp.module.entity.NewsInfo;
import com.example.rxretrofitdaggermvp.mvp.module.interactor.NewsListInteractor;
import com.example.rxretrofitdaggermvp.subsriber.ResponseSubscriber;
import com.example.rxretrofitdaggermvp.utils.ApiService;
import com.example.rxretrofitdaggermvp.utils.Constant;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by MrKong on 2017/4/2.
 */

public class NewsListInteractorImpl implements NewsListInteractor {

    private RequestManager requestManager;

    @Inject
    public NewsListInteractorImpl(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    @Override
    public Disposable requestNewsList(String newsType, final NewsListFragmentRequestCallBack callBack) {
        ApiService apiService = requestManager.createApiService(ApiService.class);
        Observable<BaseResponse<NewsInfo>> observable = apiService.getNews(newsType, Constant.KEY);
        return requestManager
                .setConnectTimeout(10)
                .setReadTimeout(10)
                .setWriteTimeout(10)
                .getDisposable(observable, new ResponseSubscriber<BaseResponse<NewsInfo>>(callBack) {
                    @Override
                    public void onSuccess(BaseResponse<NewsInfo> newsInfoBaseResponse) {
                        callBack.onSucess(newsInfoBaseResponse.getResult());
                    }
                });
    }
}
