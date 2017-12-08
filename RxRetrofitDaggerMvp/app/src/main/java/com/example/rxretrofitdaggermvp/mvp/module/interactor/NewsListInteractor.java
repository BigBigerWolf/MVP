package com.example.rxretrofitdaggermvp.mvp.module.interactor;

import com.example.rxretrofitdaggermvp.listener.OnErrorCallBack;
import com.example.rxretrofitdaggermvp.mvp.module.entity.NewsInfo;

import io.reactivex.disposables.Disposable;
/**
 * Created by MrKong on 2017/4/2.
 */

public interface NewsListInteractor {

    Disposable requestNewsList(String newsType, NewsListFragmentRequestCallBack callBack);

    interface NewsListFragmentRequestCallBack extends OnErrorCallBack {
        void onSucess(NewsInfo newsInfo);
    }
}
