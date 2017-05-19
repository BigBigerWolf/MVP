package com.example.rxretrofitdaggermvp.ui.fragments;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.rxretrofitdaggermvp.R;
import com.example.rxretrofitdaggermvp.ui.adapters.NewsListFragmentAdapter;
import com.example.rxretrofitdaggermvp.ui.fragments.base.BaseFragment;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import butterknife.Bind;

/**
 * Created by MrKong on 2017/4/1.
 */

public class Tab1Fragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.slidingTabLayout)
    SlidingTabLayout slidingTabLayout;

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
        viewPager.setOnPageChangeListener(this);
        slidingTabLayout.setViewPager(viewPager);
        slidingTabLayout.showDot(2);
        slidingTabLayout.showMsg(5, 100);
        slidingTabLayout.setMsgMargin(5, 0, 10);
        slidingTabLayout.showMsg(3, 5);
        slidingTabLayout.setMsgMargin(3, 0, 10);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        slidingTabLayout.hideMsg(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
