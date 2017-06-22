package com.example.rxretrofitdaggermvp.utils;

import android.os.Handler;
import android.view.View;

/**
 * Created by "MrKong" on 2017/6/21.
 */

public class MyClickListener {

    private View mView;
    private boolean waitDouble = true;
    private OnClickListener onClickListener;

    public MyClickListener(View view) {
        this.mView = view;
    }

    private void click() {
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //第一次击按钮，等待双击
                if (waitDouble == true) {
                    //准备接受双击
                    waitDouble = false;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //如果在睡眠时间中任未进行第二次点击，
                            if (waitDouble == false) {
                                //如果进行了第二次点击则第一次点击未产生任何效果
                                waitDouble = true;
                                if (onClickListener != null) {
                                    //判断该次操作为单击操作，等待下一次点击
                                    onClickListener.onSinleClick();
                                }
                            }
                        }
                    }, 300);
                } else {
                    //在第一次点击正在睡眠时，进行了第二次点击
                    waitDouble = true;
                    if (onClickListener != null) {
                        //判断出为双击，等待单击
                        onClickListener.onDoubleClick();
                    }
                }
            }
        });
    }

    public interface OnClickListener {
        void onSinleClick();
        void onDoubleClick();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        click();
    }
}
