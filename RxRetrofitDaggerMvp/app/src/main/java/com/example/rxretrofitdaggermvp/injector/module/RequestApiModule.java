package com.example.rxretrofitdaggermvp.injector.module;

import com.example.rxretrofitdaggermvp.injector.scope.PerApplication;
import com.example.rxretrofitdaggermvp.manager.RequestInterceptor;
import com.example.rxretrofitdaggermvp.manager.RequestManager;
import com.example.rxretrofitdaggermvp.manager.RetrofitManager;

import dagger.Module;
import dagger.Provides;

/**
 * @author MrKong
 * @createTime 2018/7/3
 */
@Module
public class RequestApiModule {

    @Provides
    @PerApplication
    RequestManager provideHttpManager(RetrofitManager retrofitManager) {
        return new RequestManager(retrofitManager);
    }

    /**
     * 这种提供RetrofitManager对象的方式，对此对象有限制，比如是@PerApplication(全局唯一的)。
     * 也可以在RetrofitManager构造函数的上添加@Inject注解。生成的对象跟new出来的无差别。
     * @return RetrofitManager单例
     */
    @Provides
    @PerApplication
    RetrofitManager provideRetrofitManager() {
        return new RetrofitManager();
    }
}
