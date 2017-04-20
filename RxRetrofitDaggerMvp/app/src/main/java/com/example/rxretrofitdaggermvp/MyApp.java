package com.example.rxretrofitdaggermvp;

import android.app.Application;
import android.content.Context;

import com.example.rxretrofitdaggermvp.injector.module.AppModule;
import com.example.rxretrofitdaggermvp.injector.component.AppComponent;
import com.example.rxretrofitdaggermvp.injector.component.DaggerAppComponent;

/**
 * Created by MrKong on 2017/4/1.
 */

public class MyApp extends Application {

    private static Context context;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initComponent();
    }

    /**
     * 全局初始化AppComponent
     */
    private void initComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public static Context getAppContext() {
        return context;
    }
}
