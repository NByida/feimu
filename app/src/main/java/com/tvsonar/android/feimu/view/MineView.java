package com.tvsonar.android.feimu.view;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.tvsonar.android.feimu.R;
import com.tvsonar.android.feimu.view.base.MvpView;
import com.tvsonar.android.feimu.view.weidget.SmartToolbar;

import net.lucode.hackware.magicindicator.MagicIndicator;

import butterknife.BindView;

public class MineView extends MvpView {
    @BindView(R.id.toolbar)
    SmartToolbar toolbar;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.view_pager_content)
    ViewPager viewPagerContent;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public View getToolbar() {
        return toolbar;
    }

    @Override
    public void regist(@NonNull LayoutInflater inflater, LifecycleProvider provider) {
        super.regist(inflater, provider);
        toolbar.dismiss();
    }
}
