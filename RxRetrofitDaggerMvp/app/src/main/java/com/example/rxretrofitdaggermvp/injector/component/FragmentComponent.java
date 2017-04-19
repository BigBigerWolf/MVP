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
    void inject(Tab1Fragment tab1Fragment);

    void inject(Tab3Fragment tab3Fragment);

    void inject(Tab4Fragment tab4Fragment);

    void inject(Tab2Fragment tab2Fragment);

    void inject(NewsListFragment newsListFragment);
}
