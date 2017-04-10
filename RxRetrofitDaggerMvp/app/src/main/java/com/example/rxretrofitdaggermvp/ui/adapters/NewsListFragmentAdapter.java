package com.example.rxretrofitdaggermvp.ui.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.rxretrofitdaggermvp.ui.fragments.NewsListFragment;
import com.example.rxretrofitdaggermvp.utils.Constant;

/**
 * Created by MrKong on 2017/4/2.
 */
public class NewsListFragmentAdapter extends FragmentPagerAdapter {
    private String[] array;

    public NewsListFragmentAdapter(FragmentManager fragmentManager, String[] stringArray) {
        super(fragmentManager);
        this.array = stringArray;
    }

    @Override
    public Fragment getItem(int position) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.NEWS_TYPE, array[position].split("@")[1]);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return array[position].split("@")[0];
    }

    @Override
    public int getCount() {
        return array.length;
    }
}
