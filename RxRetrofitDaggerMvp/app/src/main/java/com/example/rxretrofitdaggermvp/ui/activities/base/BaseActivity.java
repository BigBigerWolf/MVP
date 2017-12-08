package com.example.rxretrofitdaggermvp.ui.activities.base;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.rxretrofitdaggermvp.MyApp;
import com.example.rxretrofitdaggermvp.R;
import com.example.rxretrofitdaggermvp.injector.module.ActivityModule;
import com.example.rxretrofitdaggermvp.injector.component.ActivityComponent;
import com.example.rxretrofitdaggermvp.injector.component.DaggerActivityComponent;
import com.example.rxretrofitdaggermvp.manager.AppManager;
import com.example.rxretrofitdaggermvp.mvp.presenter.base.BasePresenter;
import com.example.rxretrofitdaggermvp.mvp.view.base.BaseView;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.zhy.autolayout.AutoLayoutActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AutoLayoutActivity implements BaseView {
    protected static String TAG;
    protected ActivityComponent activityComponent;
    protected BasePresenter basePresenter;

    @Inject
    AppManager appManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        } else {
            throw new IllegalArgumentException("the LayoutId can not be 0!");
        }
        TAG = getClass().getSimpleName();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            getBundle(bundle);
        }
        initActivityComponent();
        initInjector();
        SetStatusBar();
        ButterKnife.bind(this);
        initPresenter();
        if (basePresenter != null) {
            basePresenter.setView(this);
            basePresenter.initialize();
        }
        appManager.addAcitivty(this);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void SetStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isSetStatusBar()) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimary);
        }
    }

    protected void getBundle(Bundle bundle) {}

    protected abstract int getLayoutId();

    protected abstract void initPresenter();

    protected abstract void initInjector();

    protected boolean isSetStatusBar() {
        return false;
    }

    private void initActivityComponent() {
        activityComponent = DaggerActivityComponent.builder()
                .appComponent(((MyApp) getApplication()).getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void goToLogin() {
        //readyGo(LoginActivity.class);
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

    @Override
    public void showMessage(int errorCode, String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (basePresenter != null) {
            basePresenter.destroy();
        }
        appManager.removeActivity(this);
    }
}
