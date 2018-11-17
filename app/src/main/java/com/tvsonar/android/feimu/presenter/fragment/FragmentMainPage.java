package com.tvsonar.android.feimu.presenter.fragment;

import com.tvsonar.android.feimu.presenter.base.BasePresentFragment;
import com.tvsonar.android.feimu.view.MainPageView;

public class FragmentMainPage extends BasePresentFragment<MainPageView> {
    @Override
    public Class<MainPageView> getPresentClass() {
        return MainPageView.class;
    }

    @Override
    public void onNetWorkErorRetry() {

    }

    public static FragmentMainPage newInstance() {
        FragmentMainPage fragment = new FragmentMainPage();
        return fragment;
    }

}
