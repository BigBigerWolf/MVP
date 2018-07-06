package com.example.rxretrofitdaggermvp.injector.component;

import android.content.Context;
import android.widget.Toast;

import com.example.rxretrofitdaggermvp.MyApp;
import com.example.rxretrofitdaggermvp.injector.module.AppModule;
import com.example.rxretrofitdaggermvp.injector.module.RequestApiModule;
import com.example.rxretrofitdaggermvp.injector.scope.PerApplication;
import com.example.rxretrofitdaggermvp.manager.AppManager;
import com.example.rxretrofitdaggermvp.manager.RequestManager;

import dagger.Component;

/**
 * 顶层Component，可向它的子依赖提供{@link AppModule}和{@link RequestApiModule}提供的对象。
 *
 * @author MrKong
 * @date 2017/4/1
 */
@PerApplication
@Component(modules = {AppModule.class, RequestApiModule.class})
public interface AppComponent {

    Context getContext();

    MyApp getMyApp();

    Toast getToast();

    AppManager getAppManager();

    RequestManager getRequestManager();

    ActivityComponent getActivityComponent();

    CommonComponent getCommonComponent();
}
