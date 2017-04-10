package com.example.rxretrofitdaggermvp.ui.fragments;

import android.view.View;
import android.widget.TextView;

import com.example.rxretrofitdaggermvp.R;
import com.example.rxretrofitdaggermvp.ui.fragments.base.BaseFragment;

import butterknife.Bind;

/**
 * Created by MrKong on 2017/4/1.
 */

public class Tab4Fragment extends BaseFragment {

    @Bind(R.id.tv_content)
    TextView tvContent;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab4;
    }

    @Override
    protected void initInjector() {
        fragmentComponent.inject(this);
    }

    @Override
    protected void initView(View contentView) {
        tvContent.setText(TAG);
    }

}
