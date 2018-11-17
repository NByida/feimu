package com.tvsonar.android.feimu.application;

import android.support.multidex.MultiDexApplication;

import com.tvsonar.android.feimu.common.Density;

public class FeimuApp extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Density.setDensity(this, 375f);
    }
}
