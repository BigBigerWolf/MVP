package com.example.rxretrofitdaggermvp.mvp.view;

import com.example.rxretrofitdaggermvp.mvp.module.entity.TabItem;
import com.example.rxretrofitdaggermvp.mvp.view.base.BaseView;

import java.util.List;

/**
 * Created by MrKong on 2017/4/1.
 */

public interface MainView extends BaseView {

    void initTabHost(List<TabItem> items);

}
