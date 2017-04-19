package com.example.rxretrofitdaggermvp.mvp.presenter.impl;

import com.example.rxretrofitdaggermvp.mvp.contract.NewsListContract;
import com.example.rxretrofitdaggermvp.mvp.module.entity.NewsInfo;
import com.example.rxretrofitdaggermvp.mvp.module.interactor.NewsListInteractor;
import com.example.rxretrofitdaggermvp.mvp.module.interactor.imp.NewsListInteractorImpl;
import com.example.rxretrofitdaggermvp.mvp.presenter.base.BasePresenterImpl;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by MrKong on 2017/4/2.
 */

public class  NewsListPresenterImpl extends BasePresenterImpl<NewsListContract.View>
        implements NewsListContract.Presenter, NewsListInteractor.NewsListFragmentRequestCallBack {

    private final NewsListInteractorImpl interactor;

    @Inject
    public NewsListPresenterImpl(NewsListInteractorImpl interactor) {
        this.interactor = interactor;
    }

    @Override
    public void loadNews(String newsType) {
        view.onLoading();
        Subscription subscription = interactor.requestNewsList(newsType, this);
        subForUnSubscribes.add(subscription);
    }

    @Override
    public void initialize() {
        //TODO: 默认调用
    }

    @Override
    public void onSucess(NewsInfo newsInfo) {
        view.hideLoading();
        view.initNews(newsInfo);
    }
}
