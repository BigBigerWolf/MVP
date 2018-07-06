package com.example.rxretrofitdaggermvp.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.rxretrofitdaggermvp.MyApp;
import com.example.rxretrofitdaggermvp.injector.component.DaggerAppComponent;
import com.example.rxretrofitdaggermvp.injector.module.AppModule;

import javax.inject.Inject;

/**
 * Created by "MrKong" on 2017/4/20.
 */

public class NetReciever extends BroadcastReceiver {

    {
        DaggerAppComponent
                .builder()
                .appModule(new AppModule(MyApp.getAppContext(), (MyApp)MyApp.getAppContext()))
                .build()
                .getCommonComponent()
                .inject(this);
    }

    @Inject
    Toast toast;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        NetworkInfo.State mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if (wifiState != null && mobileState != null) {
            if (NetworkInfo.State.CONNECTED != wifiState && NetworkInfo.State.CONNECTED != mobileState) {
                toast.setText("网络已断开连接！");
            } else {
                toast.setText("网络已重新连接！");
            }
        }
    }
}
