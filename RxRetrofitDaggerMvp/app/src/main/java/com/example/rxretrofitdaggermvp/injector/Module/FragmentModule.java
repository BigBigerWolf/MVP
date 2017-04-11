package com.example.rxretrofitdaggermvp.injector.Module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.example.rxretrofitdaggermvp.injector.scope.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MrKong on 2017/4/1.
 */

@Module
public class FragmentModule {
    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

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
