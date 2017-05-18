package com.example.rxretrofitdaggermvp.mvp.module.interactor.imp;

import android.support.v4.app.Fragment;

import com.example.rxretrofitdaggermvp.R;
import com.example.rxretrofitdaggermvp.mvp.module.entity.TabItem;
import com.example.rxretrofitdaggermvp.mvp.module.interactor.MainInteractor;
import com.example.rxretrofitdaggermvp.ui.fragments.Tab1Fragment;
import com.example.rxretrofitdaggermvp.ui.fragments.Tab2Fragment;
import com.example.rxretrofitdaggermvp.ui.fragments.Tab3Fragment;
import com.example.rxretrofitdaggermvp.ui.fragments.Tab4Fragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by MrKong on 2017/4/1.
 */

public class MainInteractorImpl implements MainInteractor {

    @Inject
    public MainInteractorImpl() {
    }

    @Override
    public List<TabItem> getTabData() {
        List<TabItem> tabItems = new ArrayList<>();
        tabItems.add(new TabItem(R.mipmap.main_tab_1_up, R.mipmap.main_tab_1_down, R.string.main_tab_1, Tab1Fragment.class));
        tabItems.add(new TabItem(R.mipmap.main_tab_2_up, R.mipmap.main_tab_2_down, R.string.main_tab_2, Tab2Fragment.class));
        tabItems.add(new TabItem(R.mipmap.ic_launcher, R.mipmap.ic_launcher, 0, Fragment.class));
        tabItems.add(new TabItem(R.mipmap.main_tab_3_up, R.mipmap.main_tab_3_down, R.string.main_tab_3, Tab3Fragment.class));
        tabItems.add(new TabItem(R.mipmap.main_tab_4_up, R.mipmap.main_tab_4_down, R.string.main_tab_4, Tab4Fragment.class));
        return tabItems;
    }
}
