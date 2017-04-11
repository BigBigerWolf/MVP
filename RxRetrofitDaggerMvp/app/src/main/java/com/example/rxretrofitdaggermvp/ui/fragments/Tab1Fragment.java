package com.example.rxretrofitdaggermvp.ui.fragments;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.rxretrofitdaggermvp.R;
import com.example.rxretrofitdaggermvp.ui.adapters.NewsListFragmentAdapter;
import com.example.rxretrofitdaggermvp.ui.fragments.base.BaseFragment;

import butterknife.Bind;

/**
 * Created by MrKong on 2017/4/1.
 */

public class Tab1Fragment extends BaseFragment {

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab1;
    }

    @Override
    protected void initInjector() {
        fragmentComponent.inject(this);
    }

    @Override
    protected void initView(View contentView) {
        NewsListFragmentAdapter adapter = new NewsListFragmentAdapter(getChildFragmentManager(),
                getResources().getStringArray(R.array.home_tab));
        viewPager.setAdapter(adapter);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
    }
}
