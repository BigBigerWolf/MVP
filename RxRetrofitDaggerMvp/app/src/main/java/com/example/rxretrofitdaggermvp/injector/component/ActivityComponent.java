package com.example.rxretrofitdaggermvp.injector.component;

import com.example.rxretrofitdaggermvp.injector.module.ActivityModule;
import com.example.rxretrofitdaggermvp.injector.module.AppModule;
import com.example.rxretrofitdaggermvp.injector.scope.PerActivity;
import com.example.rxretrofitdaggermvp.ui.activities.MainActivity;
import com.example.rxretrofitdaggermvp.ui.activities.NewsDetailActivity;

import dagger.Subcomponent;

/**
 * {@link AppComponent}的子Component, 同时可以提供{@link AppModule}和{@link ActivityModule}中提供的对象。
 *
 * 这种方式与使用{@link FragmentComponent}的结果是一样的，区别是它不用依赖{@link AppComponent}，但是需要在{@link AppComponent}中提供 ActivityComponent getActivityComponent();
 *
 * @author MrKong
 * @date 2017/4/1
 */

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(NewsDetailActivity newsDetailActivity);
}

