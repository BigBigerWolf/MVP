package com.example.rxretrofitdaggermvp.injector.component;

import com.example.rxretrofitdaggermvp.injector.Module.ActivityModule;
import com.example.rxretrofitdaggermvp.injector.scope.PerActivity;
import com.example.rxretrofitdaggermvp.ui.activities.MainActivity;
import com.example.rxretrofitdaggermvp.ui.activities.NewsDetailActivity;

import dagger.Component;

/**
 * Created by MrKong on 2017/4/1.
 */

@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity activity);

    void inject(NewsDetailActivity newsDetailActivity);
}

