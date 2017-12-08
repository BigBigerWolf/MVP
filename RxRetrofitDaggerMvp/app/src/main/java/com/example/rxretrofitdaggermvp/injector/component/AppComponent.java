package com.example.rxretrofitdaggermvp.injector.component;

import android.content.Context;

import com.example.rxretrofitdaggermvp.injector.module.AppModule;
import com.example.rxretrofitdaggermvp.injector.scope.PerApplication;
import com.example.rxretrofitdaggermvp.manager.AppManager;
import com.example.rxretrofitdaggermvp.manager.RequestManager;

import dagger.Component;

/**
 * Created by MrKong on 2017/4/1.
 */

@PerApplication
@Component(modules = AppModule.class)
public interface AppComponent {

    //↓↓↓它的特点是，需要向下层（也就是需要的层）提供AppModule中提供好的对象
    Context getContext();

    AppManager getAppManager();

    RequestManager getRequestManager();
}
