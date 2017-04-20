package com.example.rxretrofitdaggermvp.mvp.contract;

import com.example.rxretrofitdaggermvp.mvp.module.entity.TabItem;
import com.example.rxretrofitdaggermvp.mvp.presenter.base.BasePresenter;
import com.example.rxretrofitdaggermvp.mvp.view.base.BaseView;

import java.util.List;

/**
 * Created by "MrKong" on 2017/4/19.
 */

//管理View和Presenter的契约接口
public interface MainContract {

    interface View extends BaseView {
        void initTabHost(List<TabItem> items);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
