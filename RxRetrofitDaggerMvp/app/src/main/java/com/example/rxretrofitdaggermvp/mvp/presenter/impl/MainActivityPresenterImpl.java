package com.example.rxretrofitdaggermvp.mvp.presenter.impl;

import com.example.rxretrofitdaggermvp.mvp.module.interactor.imp.MainActivityInteractorImpl;
import com.example.rxretrofitdaggermvp.mvp.presenter.base.BasePresenterImpl;
import com.example.rxretrofitdaggermvp.mvp.view.MainView;

import javax.inject.Inject;

/**
 * Created by MrKong on 2017/4/1.
 */

public class MainActivityPresenterImpl extends BasePresenterImpl<MainView> {

    @Inject
    MainActivityInteractorImpl interactor;

    @Inject
    public MainActivityPresenterImpl() {

    }

    @Override
    public void initialize() {
        view.initTabHost(interactor.getTabData());
    }
}
