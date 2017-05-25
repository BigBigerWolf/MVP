package com.example.rxretrofitdaggermvp.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.example.rxretrofitdaggermvp.R;
import com.example.rxretrofitdaggermvp.ui.activities.base.BaseActivity;
import com.example.rxretrofitdaggermvp.ui.customviews.ChromeFloatingCirclesDrawable;

import butterknife.Bind;

/**
 * 解决webView导致的内存泄漏
 */
public class NewsDetailActivity extends BaseActivity implements View.OnClickListener, Toolbar.OnMenuItemClickListener {

    @Bind(R.id.backImage)
    ImageView backImage;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsingToolBar)
    CollapsingToolbarLayout collapsingToolBar;
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.loading_progress)
    ProgressBar progressBar;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    private String backImageUrl;
    private String detailUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initPresenter() {
        initToolBar();
        initViews();
    }

    private void initToolBar() {
        collapsingToolBar.setTitle("NewsDetail");
        collapsingToolBar.setExpandedTitleColor(Color.TRANSPARENT);
        collapsingToolBar.setCollapsedTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(this);
        toolbar.inflateMenu(R.menu.info);
        toolbar.setOnMenuItemClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void initViews() {
        Glide.with(this)
                .load(backImageUrl)
                .into(backImage);

        ChromeFloatingCirclesDrawable chromeFloatingCirclesDrawable = (ChromeFloatingCirclesDrawable) new ChromeFloatingCirclesDrawable
                .Builder(this)
                .colors(getResources().getIntArray(R.array.google_colors))
                .build();
        progressBar.setIndeterminateDrawable(chromeFloatingCirclesDrawable);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        webView.loadUrl(detailUrl);
        webView.setWebViewClient(new WebViewClient() {
            //加载完毕回调
            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void initInjector() {
        activityComponent.inject(this);
    }

    @Override
    protected void getBundle(Bundle bundle) {
        backImageUrl = bundle.getString("backImage");
        detailUrl = bundle.getString("webView");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.share) {
            share();
            return true;
        }
        return false;
    }

    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, "欢迎参观 撸啊撸啊： https://github.com/itkong/MVP");
        startActivity(Intent.createChooser(intent, "通过"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }
}
