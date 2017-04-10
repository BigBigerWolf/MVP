package com.example.rxretrofitdaggermvp.injector.component;

import android.content.Context;

import com.example.rxretrofitdaggermvp.injector.Module.AppModule;
import com.example.rxretrofitdaggermvp.injector.scope.PerApplication;
import com.example.rxretrofitdaggermvp.manager.AppManager;

import dagger.Component;

/**
 * Created by MrKong on 2017/4/1.
 */

@PerApplication
@Component(modules = AppModule.class)
public interface AppComponent {

    //下层提供全局context
    Context getContext();

    //下层提供全局对象
    AppManager getAppManager();
}
