package com.example.rxretrofitdaggermvp.mvp.presenter.impl;

import com.example.rxretrofitdaggermvp.mvp.contract.NewsListContract;
import com.example.rxretrofitdaggermvp.mvp.module.entity.NewsInfo;
import com.example.rxretrofitdaggermvp.mvp.module.interactor.NewsListInteractor;
import com.example.rxretrofitdaggermvp.mvp.module.interactor.imp.NewsListInteractorImpl;
import com.example.rxretrofitdaggermvp.mvp.presenter.base.BasePresenterImpl;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by MrKong on 2017/4/2.
 */

public class NewsListPresenterImpl extends BasePresenterImpl<NewsListContract.View>
        implements NewsListContract.Presenter, NewsListInteractor.NewsListFragmentRequestCallBack {

    @Inject
    NewsListInteractorImpl interactor;

    @Inject
    NewsListPresenterImpl() {
    }

    @Override
    public void loadNews(String newsType) {
        mView.onLoading();
        Disposable subscription = interactor.requestNewsList(newsType, this);
        mCompositeDisposable.add(subscription);
    }

    @Override
    public void initialize() {
        //TODO: 默认调用
    }

    @Override
    public void onSucess(NewsInfo newsInfo) {
        mView.hideLoading();
        mView.initNews(newsInfo);
    }
}
