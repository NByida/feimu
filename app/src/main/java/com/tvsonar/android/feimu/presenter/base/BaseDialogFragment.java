package com.tvsonar.android.feimu.presenter.base;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxDialogFragment;

import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BaseDialogFragment extends RxDialogFragment {
    protected String tag;

    private OnClickListener onClickListener;

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }



    protected <T> ObservableTransformer<T, T> applyIOSchedulersAndLifecycle() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindUntilEvent(FragmentEvent.DESTROY.DESTROY));
    }

    protected <T> ObservableTransformer<T, T> applySchedulersAndLifecycle() {
        return observable -> observable.observeOn(AndroidSchedulers.mainThread())
                .compose(bindUntilEvent(FragmentEvent.DESTROY));
    }

    protected void clicks(View view) {
        RxView.clicks(view)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .compose(applySchedulersAndLifecycle())
                .subscribe(it -> onClick(view));
    }


    protected void onClick(View view) {
        if (onClickListener != null) {
            onClickListener.onClick(this, view);
        }
    }

    public interface OnClickListener {
        void onClick(BaseDialogFragment dialogFragment, View view);
    }
}


