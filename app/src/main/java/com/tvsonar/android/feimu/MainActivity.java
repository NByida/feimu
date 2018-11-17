package com.tvsonar.android.feimu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tvsonar.android.feimu.presenter.base.BasePresentActivity;
import com.tvsonar.android.feimu.view.MainView;

public class MainActivity extends BasePresentActivity<MainView> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Class<MainView> getPresentClass() {
        return MainView.class;
    }

    @Override
    public void onNetWorkErorRetry() {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

}
