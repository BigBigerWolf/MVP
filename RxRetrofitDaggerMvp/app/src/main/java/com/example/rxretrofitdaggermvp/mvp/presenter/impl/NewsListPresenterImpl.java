package com.example.rxretrofitdaggermvp.mvp.presenter.impl;

import com.example.rxretrofitdaggermvp.mvp.module.entity.NewsInfo;
import com.example.rxretrofitdaggermvp.mvp.module.interactor.NewsListFragmentInteractor;
import com.example.rxretrofitdaggermvp.mvp.module.interactor.imp.NewsListFragmentInteractorImpl;
import com.example.rxretrofitdaggermvp.mvp.presenter.base.BasePresenterImpl;
import com.example.rxretrofitdaggermvp.mvp.presenter.NewsListPresenter;
import com.example.rxretrofitdaggermvp.mvp.view.NewsListView;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by MrKong on 2017/4/2.
 */

public class NewsListPresenterImpl extends BasePresenterImpl<NewsListView>
        implements NewsListPresenter, NewsListFragmentInteractor.NewsListFragmentRequestCallBack {

    @Inject
    NewsListFragmentInteractorImpl interactor;

    @Inject
    public NewsListPresenterImpl(){}

    @Override
    public void loadNews(String newsType) {
        view.onLoading();
        Subscription subscription = interactor.requestNewsList(newsType, this);
        if (!subForUnSubscribes.contains(subscription)) {
            subForUnSubscribes.add(subscription);
        }
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
