package com.example.rxretrofitdaggermvp.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.rxretrofitdaggermvp.R;
import com.example.rxretrofitdaggermvp.mvp.contract.NewsListContract;
import com.example.rxretrofitdaggermvp.mvp.module.entity.NewsInfo;
import com.example.rxretrofitdaggermvp.mvp.presenter.impl.NewsListPresenterImpl;
import com.example.rxretrofitdaggermvp.ui.activities.NewsDetailActivity;
import com.example.rxretrofitdaggermvp.ui.adapters.NewsListAdapter;
import com.example.rxretrofitdaggermvp.ui.fragments.base.BaseFragment;
import com.example.rxretrofitdaggermvp.utils.Constant;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by MrKong on 2017/4/2.
 */
public class NewsListFragment extends BaseFragment
        implements NewsListContract.View,
        BaseQuickAdapter.OnItemClickListener,
        PtrHandler,
        BaseQuickAdapter.OnItemLongClickListener {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.ptr_frame)
    PtrClassicFrameLayout ptrFrame;
    @Inject
    Context context;
    @Inject
    Activity activity;
    @Inject
    NewsListPresenterImpl newsListPresenter;
    @Inject
    NewsListAdapter newsListAdapter;
    private boolean isPrepared;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initInjector() {
        fragmentComponent.inject(this);
    }

    @Override
    protected void initView(View contentView) {
        isPrepared = true;
        basePresenter = newsListPresenter;
        onLazyLoad();
        setUpPtrFrame();
        initRecylerView();
    }

    @Override
    public void initNews(NewsInfo newsInfo) {
        newsListAdapter.setNewData(newsInfo.getData());
    }

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();
        if (isPrepared && isVisible) {
            onLoading();
        }
    }

    @Override
    public void onLoading() {
        ptrFrame.post(new Runnable() {
            @Override
            public void run() {
                ptrFrame.autoRefresh();
            }
        });
    }

    @Override
    public void hideLoading() {
        ptrFrame.refreshComplete();
    }

    /**
     * defaultConfigs
     */
    private void setUpPtrFrame() {
        ptrFrame.setResistance(1.7f);
        ptrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        ptrFrame.setDurationToClose(200);
        ptrFrame.setDurationToCloseHeader(1000);
        ptrFrame.setPullToRefresh(false);
        ptrFrame.setKeepHeaderWhenRefresh(true);
        ptrFrame.setLastUpdateTimeRelateObject(this);
        ptrFrame.setPtrHandler(this);
    }

    private void initRecylerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(newsListAdapter);
        newsListAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        newsListAdapter.isFirstOnly(false);
        newsListAdapter.setOnItemClickListener(this);
        newsListAdapter.setOnItemLongClickListener(this);
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        String newsType = getArguments().getString(Constant.NEWS_TYPE);
        newsListPresenter.loadNews(newsType);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<NewsInfo.DataBean> list = adapter.getData();
        Bundle bundle = new Bundle();
        bundle.putString("backImage", list.get(position).getThumbnail_pic_s());
        bundle.putString("webView", list.get(position).getUrl());
        readyGo(NewsDetailActivity.class, bundle);
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        newsListAdapter.remove(position);
        return true;
    }
}
