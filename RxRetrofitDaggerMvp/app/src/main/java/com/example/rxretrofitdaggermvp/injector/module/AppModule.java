package com.example.rxretrofitdaggermvp.injector.module;

import android.content.Context;

import com.example.rxretrofitdaggermvp.injector.scope.PerApplication;
import com.example.rxretrofitdaggermvp.manager.AppManager;
import com.example.rxretrofitdaggermvp.manager.RequestManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MrKong on 2017/4/1.
 */
//提供App生命周期内有效的对象
@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    //↓↓↓这里提供的对象可以在依赖AppComponent的所有Component注入的对象中使用
    // 比如（ActivityComponent，FragmentComponent中inject的对象）
    // 自然这里限制的单例，全局有效。

    /**
     * 提供全局单例context
     * @return
     */
    @Provides
    @PerApplication
    Context provideContext() {
        return context;
    }

    /**
     * 提供全局AppManager单例
     * @return
     */
    @Provides
    @PerApplication
    AppManager provideAppManager() {
        return new AppManager(context);
    }

    /**
     * 提供全局单例HttpManager
     * @return
     */
    @Provides
    @PerApplication
    RequestManager provideHttpManager() {
        return new RequestManager();
    }
}
