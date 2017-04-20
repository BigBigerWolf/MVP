package com.example.rxretrofitdaggermvp.injector.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.example.rxretrofitdaggermvp.injector.scope.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MrKong on 2017/4/1.
 */
//提供Fragment生命周期内有效的对象
@Module
public class FragmentModule {
    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    //↓↓↓这里所Provide的对象只能在Fragment和Fragment中Inject过的对象中使用（比如NewsListFrgment和NewsListAdapter中）
    //但是，这里的单例只能限制在Fragment生命周期内

    @Provides
    @PerFragment
    Fragment provideFragment() {
        return fragment;
    }

    @Provides
    @PerFragment
    Activity provideActivity() {
        return fragment.getActivity();
    }
}
