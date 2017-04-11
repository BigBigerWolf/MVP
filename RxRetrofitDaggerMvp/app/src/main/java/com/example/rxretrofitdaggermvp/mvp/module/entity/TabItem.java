package com.example.rxretrofitdaggermvp.mvp.module.entity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rxretrofitdaggermvp.MyApp;
import com.example.rxretrofitdaggermvp.R;

/**
 * Created by MrKong on 2017/4/1.
 */

public class TabItem {
    private int imageNormal;
    private int imagePress;
    private int title;
    private String titleString;
    public Class<? extends Fragment> fragmentClass;
    public View view;
    public ImageView imageView;
    public TextView textView;

    public TabItem(int imageNormal, int imagePress, int title, Class<? extends Fragment> fragmentClass) {
        this.imageNormal = imageNormal;
        this.imagePress = imagePress;
        this.title = title;
        this.fragmentClass = fragmentClass;
    }

    public Class<? extends Fragment> getFragmentClass() {
        return fragmentClass;
    }

    public int getTitle() {
        return title;
    }

    public String getTitleString() {
        if (title == 0) {
            return "";
        }
        if (TextUtils.isEmpty(titleString)) {
            titleString = MyApp.getAppContext().getString(title);
        }
        return titleString;
    }

    public View getView(Activity activity) {
        if (this.view == null) {
            this.view = activity.getLayoutInflater().inflate(R.layout.view_tab_indicator, null);
            this.imageView = (ImageView) this.view.findViewById(R.id.tab_iv_image);
            this.textView = (TextView) this.view.findViewById(R.id.tab_tv_text);
            if (this.title == 0) {
                this.textView.setVisibility(View.GONE);
            } else {
                this.textView.setVisibility(View.VISIBLE);
                this.textView.setText(getTitleString());
            }
            this.imageView.setImageResource(imageNormal);
        }
        return this.view;
    }

    //切换tab的方法
    public void setChecked(boolean isChecked) {
        if (imageView != null) {
            if (isChecked) {
                imageView.setImageResource(imagePress);
            } else {
                imageView.setImageResource(imageNormal);
            }
        }
        if (textView != null && title != 0) {
            if (isChecked) {
                textView.setTextColor(MyApp.getAppContext().getResources().getColor(R.color.all_red));
            } else {
                textView.setTextColor(MyApp.getAppContext().getResources().getColor(R.color.main_title));
            }
        }
    }
}
