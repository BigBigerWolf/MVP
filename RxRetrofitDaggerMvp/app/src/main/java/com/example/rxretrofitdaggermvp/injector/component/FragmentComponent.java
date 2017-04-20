package com.example.rxretrofitdaggermvp.injector.component;

import com.example.rxretrofitdaggermvp.injector.module.FragmentModule;
import com.example.rxretrofitdaggermvp.injector.scope.PerFragment;
import com.example.rxretrofitdaggermvp.ui.fragments.NewsListFragment;
import com.example.rxretrofitdaggermvp.ui.fragments.Tab1Fragment;
import com.example.rxretrofitdaggermvp.ui.fragments.Tab2Fragment;
import com.example.rxretrofitdaggermvp.ui.fragments.Tab3Fragment;
import com.example.rxretrofitdaggermvp.ui.fragments.Tab4Fragment;

import dagger.Component;

/**
 * Created by MrKong on 2017/4/1.
 */

@PerFragment
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    // 将Fragment注入进来，
    // 它会先去Fragment的Module中查找带有@Provides注解的对象，如果找到了Fragment中带有@Inject注解的对象则成功注入，
    // 否则去该对象中寻找带有@Inject注解的构造，找到则成功注入，
    // 否则失败。
    void inject(Tab1Fragment tab1Fragment);

    void inject(Tab3Fragment tab3Fragment);

    void inject(Tab4Fragment tab4Fragment);

    void inject(Tab2Fragment tab2Fragment);

    void inject(NewsListFragment newsListFragment);
}
