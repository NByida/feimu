package com.tvsonar.android.feimu.event;

import android.view.View;

import com.tvsonar.android.feimu.view.base.BaseView;

public class OnClickEvent {
    private BaseView mvpView;
    private View clickView;

    public OnClickEvent(BaseView mvpView, View clickView) {
        this.mvpView = mvpView;
        this.clickView = clickView;
    }

    public BaseView getMvpView() {
        return mvpView;
    }

    public void setMvpView(BaseView mvpView) {
        this.mvpView = mvpView;
    }

    public View getClickView() {
        return clickView;
    }

    public void setClickView(View clickView) {
        this.clickView = clickView;
    }
}

