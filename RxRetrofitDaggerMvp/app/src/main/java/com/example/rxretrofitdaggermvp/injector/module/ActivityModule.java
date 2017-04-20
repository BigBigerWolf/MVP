package com.example.rxretrofitdaggermvp.injector.module;

import android.app.Activity;

import com.example.rxretrofitdaggermvp.injector.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MrKong on 2017/4/1.
 */
//同Fragment~
@Module
public class ActivityModule {
    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity ProvideActivity() {
        return activity;
    }
}
