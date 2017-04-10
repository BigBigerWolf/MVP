package com.example.rxretrofitdaggermvp.mvp.view;

import com.example.rxretrofitdaggermvp.mvp.module.entity.NewsInfo;
import com.example.rxretrofitdaggermvp.mvp.view.base.BaseView;

/**
 * Created by MrKong on 2017/4/2.
 */

public interface NewsListView extends BaseView {

    void initNews(NewsInfo newsInfo);
}
