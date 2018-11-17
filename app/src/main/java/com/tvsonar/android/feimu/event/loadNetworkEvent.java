package com.tvsonar.android.feimu.event;

import com.tvsonar.android.feimu.view.base.MvpView;

public class loadNetworkEvent {
    private MvpView mvpView;
    public loadNetworkEvent(MvpView mvpView) {
        this.mvpView=mvpView;
    }

    public MvpView getMvpView() {
        return mvpView;
    }

    public void setMvpView(MvpView mvpView) {
        this.mvpView = mvpView;
    }
}

