package com.example.rxretrofitdaggermvp.mvp.presenter.impl;

import com.example.rxretrofitdaggermvp.mvp.contract.MainContract;
import com.example.rxretrofitdaggermvp.mvp.module.interactor.imp.MainInteractorImpl;
import com.example.rxretrofitdaggermvp.mvp.presenter.base.BasePresenterImpl;

import javax.inject.Inject;

/**
 * Created by MrKong on 2017/4/1.
 */

public class MainPresenterImpl extends BasePresenterImpl<MainContract.View> {

    private MainInteractorImpl mInteractor;

    @Inject
    public MainPresenterImpl(MainInteractorImpl interactor) {
        this.mInteractor = interactor;
    }

    @Override
    public void initialize() {
        mView.initTabHost(mInteractor.getTabData());
    }
}
