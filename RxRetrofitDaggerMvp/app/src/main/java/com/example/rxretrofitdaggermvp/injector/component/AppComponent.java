package com.example.rxretrofitdaggermvp.injector.component;

import android.content.Context;

import com.example.rxretrofitdaggermvp.injector.Module.AppModule;
import com.example.rxretrofitdaggermvp.injector.scope.PerApplication;
import com.example.rxretrofitdaggermvp.manager.AppManager;
import com.example.rxretrofitdaggermvp.manager.HttpManager;

import dagger.Component;

/**
 * Created by MrKong on 2017/4/1.
 */

@PerApplication
@Component(modules = AppModule.class)
public interface AppComponent {
    Context getContext();

    AppManager getAppManager();

    HttpManager getHttpManger();
}
