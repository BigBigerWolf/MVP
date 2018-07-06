package com.example.rxretrofitdaggermvp.utils;

import android.widget.Toast;

import com.example.rxretrofitdaggermvp.MyApp;
import com.example.rxretrofitdaggermvp.injector.component.DaggerAppComponent;
import com.example.rxretrofitdaggermvp.injector.module.AppModule;

import javax.inject.Inject;

/**
 * @author Kongjian
 * @createTime 2018/7/6
 */
public class ForTestCommonComponent {

    @Inject
    Toast toast;

    {
        DaggerAppComponent
                .builder()
                .appModule(new AppModule(MyApp.getAppContext(), (MyApp) MyApp.getAppContext()))
                .build()
                .getCommonComponent()
                .inject(this);
    }

    public void showToast(String msg) {
        toast.setText(msg);
        toast.show();
    }
}
