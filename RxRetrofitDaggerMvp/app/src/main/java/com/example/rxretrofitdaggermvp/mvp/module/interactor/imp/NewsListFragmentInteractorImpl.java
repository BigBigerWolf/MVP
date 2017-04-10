package com.example.rxretrofitdaggermvp.mvp.module.interactor.imp;

import com.example.rxretrofitdaggermvp.manager.HttpManager;
import com.example.rxretrofitdaggermvp.mvp.module.entity.BaseResponse;
import com.example.rxretrofitdaggermvp.mvp.module.entity.NewsInfo;
import com.example.rxretrofitdaggermvp.mvp.module.interactor.NewsListFragmentInteractor;
import com.example.rxretrofitdaggermvp.subsriber.ResponseSubscriber;
import com.example.rxretrofitdaggermvp.utils.Constant;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;

import static com.example.rxretrofitdaggermvp.manager.HttpManager.getCacheControl;

/**
 * Created by MrKong on 2017/4/2.
 */

public class NewsListFragmentInteractorImpl implements NewsListFragmentInteractor {

    private final HttpManager httpManager;

    @Inject
    public NewsListFragmentInteractorImpl() {
        httpManager = HttpManager.getInstance();
    }

    @Override
    public Subscription requestNewsList(String newsType, final NewsListFragmentRequestCallBack callBack) {
        Observable<BaseResponse<NewsInfo>> observable = httpManager.getApiService().getNews(getCacheControl(), newsType, Constant.KEY);
        return httpManager.getSubscribtion(observable, new ResponseSubscriber<NewsInfo>(callBack) {
            @Override
            public void onSuccess(NewsInfo newsInfo) {
                callBack.onSucess(newsInfo);
            }
        });
    }
}
