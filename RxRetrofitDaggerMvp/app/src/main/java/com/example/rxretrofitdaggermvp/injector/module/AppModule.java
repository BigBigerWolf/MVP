package com.example.rxretrofitdaggermvp.injector.module;

import android.content.Context;
import android.widget.Toast;

import com.example.rxretrofitdaggermvp.MyApp;
import com.example.rxretrofitdaggermvp.injector.scope.PerApplication;
import com.example.rxretrofitdaggermvp.manager.AppManager;
import com.example.rxretrofitdaggermvp.manager.RequestManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MrKong on 2017/4/1.
 */
@Module
public class AppModule {
    private Context context;
    private MyApp myApp;

    public AppModule(Context context, MyApp myApp) {
        this.context = context;
        this.myApp = myApp;
    }

    /**
     * @return 全局context单例
     */
    @Provides
    @PerApplication
    Context provideContext() {
        return context;
    }

    /**
     * @return MyApp
     */
    @Provides
    @PerApplication
    MyApp provideMyApp() {
        return myApp;
    }

    /**
     * @return 全局Toast单例
     */
    @Provides
    @PerApplication
    Toast provideToast() {
        return Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }

    /**
     * @return 全局AppManager单例
     */
    @Provides
    @PerApplication
    AppManager provideAppManager() {
        return new AppManager();
    }
}
