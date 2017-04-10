package com.example.rxretrofitdaggermvp.ui.activities;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.example.rxretrofitdaggermvp.R;
import com.example.rxretrofitdaggermvp.mvp.module.entity.TabItem;
import com.example.rxretrofitdaggermvp.mvp.presenter.impl.MainActivityPresenterImpl;
import com.example.rxretrofitdaggermvp.mvp.view.MainView;
import com.example.rxretrofitdaggermvp.ui.activities.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements MainView {

    @Bind(android.R.id.tabcontent)
    FrameLayout tabcontent;
    @Bind(android.R.id.tabs)
    TabWidget tabs;
    @Bind(android.R.id.tabhost)
    FragmentTabHost tabhost;
    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Inject
    MainActivityPresenterImpl mainActivityPresenterImpl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPresenter() {
        basePresenter = mainActivityPresenterImpl;
    }

    @Override
    protected void initInjector() {
        activityComponent.inject(this);
    }

    @Override
    public void initTabHost(final List<TabItem> tabItems) {
        tabhost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        tabhost.getTabWidget().setDividerDrawable(null);
        for (int i = 0; i < tabItems.size(); i++) {
            TabItem tabItem = tabItems.get(i);
            TabHost.TabSpec tabSpec = tabhost.newTabSpec(tabItem.getTitleString()).setIndicator(tabItem.getView(this));
            tabhost.addTab(tabSpec, tabItem.getFragmentClass(), null);
            tabhost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.main_bottom_bg));
        }
        tabItems.get(0).setChecked(true);
        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                for (int i = 0; i < tabItems.size(); i++) {
                    TabItem tabitem = tabItems.get(i);
                    if (s.equals(tabitem.getTitleString())) {
                        tabitem.setChecked(true);
                    } else {
                        tabitem.setChecked(false);
                    }
                }
            }
        });
    }

    @Override
    protected boolean isSetStatusBar() {
        return true;
    }
}
