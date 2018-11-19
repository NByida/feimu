package com.tvsonar.android.feimu.view;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.tvsonar.android.feimu.R;
import com.tvsonar.android.feimu.common.Mlog;
import com.tvsonar.android.feimu.presenter.fragment.FragmentMainPage;
import com.tvsonar.android.feimu.presenter.fragment.FragmentMine;
import com.tvsonar.android.feimu.view.base.MvpView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class MainView extends MvpView {
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.tv_mine)
    TextView tvMine;
    private int mCurrentPosition;
    final Fragment[] mFragments = new Fragment[2];

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @SuppressLint("CheckResult")
    @Override
    public void regist(@NonNull LayoutInflater inflater, LifecycleProvider provider) {
        super.regist(inflater, provider);
        setStatusBarDark();
        mFragments[0] = FragmentMainPage.newInstance();
        ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                .add(R.id.lay_contain, mFragments[0], createTag(mFragments[0]))
                .commit();
        RxView.clicks(tvHome)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .compose(applySchedulersAndLifecycle())
                .subscribe(v->switchFragment(0));
        RxView.clicks(tvMine)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .compose(applySchedulersAndLifecycle())
                .subscribe(v->switchFragment(1));
    }

    private String createTag(Fragment fragment) {
        return fragment.getClass().getSimpleName();
    }

    private void switchFragment(int toPosition) {
        if (mCurrentPosition == toPosition) return;
        FragmentTransaction fragmentTransaction =   ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction();
        if (mFragments[toPosition] == null) {
            mFragments[toPosition] = newFragmentInstance(toPosition);
        }
        if (!mFragments[toPosition].isAdded()) {
            fragmentTransaction.add(R.id.lay_contain, mFragments[toPosition],
                    createTag(mFragments[toPosition]));
        }
        fragmentTransaction.hide(mFragments[mCurrentPosition]).show(mFragments[toPosition]).commit();
        mCurrentPosition = toPosition;
    }

    public String getCurrentFragmentTag() {
        return mFragments[mCurrentPosition].getClass().getName();
    }

    private Fragment newFragmentInstance(int position) {
        switch (position) {
            case 0:
                return FragmentMainPage.newInstance();
            case 1:
                return FragmentMine.newInstance();
        }
        return null;
    }

}
