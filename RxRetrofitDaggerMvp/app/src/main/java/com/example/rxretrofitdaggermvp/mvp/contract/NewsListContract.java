package com.example.rxretrofitdaggermvp.mvp.contract;

import com.example.rxretrofitdaggermvp.mvp.module.entity.NewsInfo;
import com.example.rxretrofitdaggermvp.mvp.presenter.base.BasePresenter;
import com.example.rxretrofitdaggermvp.mvp.view.base.BaseView;

/**
 * Created by "MrKong" on 2017/4/19.
 */

public interface NewsListContract {

    interface View extends BaseView {
        void initNews(NewsInfo newsInfo);
    }

    interface Presenter extends BasePresenter<View> {
        void loadNews(String newsType);
    }
}
