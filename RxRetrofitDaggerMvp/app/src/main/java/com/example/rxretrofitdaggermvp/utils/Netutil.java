package com.example.rxretrofitdaggermvp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.rxretrofitdaggermvp.MyApp;
import com.example.rxretrofitdaggermvp.R;

/**
 * Created by MrKong on 2017/4/2.
 */

public class Netutil {


    /**
     * 检查当前网络是否可用
     *
     * @return 是否连接到网络
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApp.getAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {

                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isNetworkErrThenShowMsg() {
        if (!isNetworkAvailable()) {
            Toast.makeText(MyApp.getAppContext(), MyApp.getAppContext().getString(R.string.error_net),
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
