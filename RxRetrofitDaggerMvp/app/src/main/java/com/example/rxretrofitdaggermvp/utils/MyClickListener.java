package com.example.rxretrofitdaggermvp.utils;

import android.os.Handler;
import android.os.SystemClock;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by "MrKong" on 2017/6/21.
 */

public class MyClickListener {

    private View mView;
    private int mNum;
    private static int CLICK_TIME = 300;
    private List<Long> times = new ArrayList<>();
    private Handler mHandler = new Handler();
    private OnClickListener onClickListener;

    /**
     * @param num  设置触发连续点击事件的次数
     * @param view 需要被点击的view
     */
    public MyClickListener(int num, View view) {
        this.mNum = num;
        this.mView = view;

    }

    private void click() {
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                times.add(SystemClock.uptimeMillis());
                int size = times.size();
                //处理多击事件
                if (size > 1) {
                    if (times.get(size - 1) - times.get(size - 2) < CLICK_TIME) {
                        if (onClickListener != null && size == mNum) {
                            onClickListener.onMultiClick();
                        }
                        if (mHandler != null) {
                            //如果连续点击生效，就移除前一个按钮的点击事件
                            mHandler.removeCallbacks(null);
                        }
                    } else {
                        long oldtime = times.get(size - 1);
                        times.clear();
                        //这里要添加最后一个时间，否则如果click......click.click的情况就会将前两个清掉，只剩一个的时候，就执行单机事件。
                        //所以保持最后一个时间，是为了与他后面的点击事件做衔接。
                        times.add(oldtime);
                    }
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (onClickListener != null) {
                            onClickListener.onSinleClick();
                        }
                    }
                }, CLICK_TIME);
            }
        });
    }

    public interface OnClickListener {
        void onSinleClick();

        void onMultiClick();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        click();
    }
}
