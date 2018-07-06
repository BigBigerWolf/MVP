package com.example.rxretrofitdaggermvp.injector.component;

import com.example.rxretrofitdaggermvp.injector.module.AppModule;
import com.example.rxretrofitdaggermvp.injector.scope.CommonScope;
import com.example.rxretrofitdaggermvp.reciever.NetReciever;
import com.example.rxretrofitdaggermvp.utils.ForTestCommonComponent;

import dagger.Subcomponent;

/**
 * {@link AppComponent}的子Component, 可向inject()中提供{@link AppModule}提供的对象。
 * 当然他还可以存在自己的Module。
 *
 * 这里展示了如何注入任意对象
 *
 * @author MrKong
 * @createTime 2018/7/4
 */

@CommonScope
@Subcomponent()
public interface CommonComponent {

    void inject(NetReciever netReciever);

    void inject(ForTestCommonComponent forTestCommonComponent);
}
