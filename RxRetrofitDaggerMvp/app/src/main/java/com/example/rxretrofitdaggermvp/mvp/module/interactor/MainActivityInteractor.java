package com.example.rxretrofitdaggermvp.mvp.module.interactor;

import com.example.rxretrofitdaggermvp.listener.OnErrorCallBack;
import com.example.rxretrofitdaggermvp.mvp.module.entity.TabItem;

import java.util.List;

/**
 * Created by MrKong on 2017/4/1.
 */

public interface MainActivityInteractor {
    List<TabItem> getTabData();

    interface MainActivityRequestCallBack extends OnErrorCallBack {
    }
}
