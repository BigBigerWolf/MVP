package com.example.rxretrofitdaggermvp.ui.fragments.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rxretrofitdaggermvp.MyApp;
import com.example.rxretrofitdaggermvp.injector.component.DaggerFragmentComponent;
import com.example.rxretrofitdaggermvp.injector.component.FragmentComponent;
import com.example.rxretrofitdaggermvp.injector.module.FragmentModule;
import com.example.rxretrofitdaggermvp.mvp.presenter.base.BasePresenter;
import com.example.rxretrofitdaggermvp.mvp.view.base.BaseView;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by MrKong on 2017/4/1.
 */

public abstract class BaseFragment extends Fragment implements BaseView {

    protected FragmentComponent fragmentComponent;
    protected View contentView;
    protected BasePresenter basePresenter;
    protected boolean isVisible;
    protected String TAG;

    @Inject
    Activity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent = DaggerFragmentComponent.builder()
                .appComponent(((MyApp) getActivity().getApplication()).getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
        initInjector();
        TAG = getClass().getSimpleName();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, contentView);
            initView(contentView);
            if (basePresenter != null) {
                basePresenter.setView(this);
                basePresenter.initialize();
            }
        }

        return contentView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (basePresenter != null) {
            basePresenter.destroy();
        }
    }

    //实现懒加载
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //当前Fragment可见
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onInvisible() {
    }

    protected void onVisible() {
        onLazyLoad();
    }

    protected abstract int getLayoutId();

    protected abstract void initInjector();

    protected abstract void initView(View contentView);

    protected void onLazyLoad() {
    }

    @Override
    public void goToLogin() {

    }

    @Override
    public void showNetFaileUI(int errorCode, String msg) {
        // TODO: 2017/12/8 网络请求超时回调
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void hideLoading() {

    }

    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(activity, clazz);
        startActivity(intent);
    }

    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(activity, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void showMessage(int errorCode, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }
}
