package com.tvsonar.android.feimu.presenter.fragment;

import com.tvsonar.android.feimu.presenter.base.BasePresentFragment;
import com.tvsonar.android.feimu.view.MineView;

public class FragmentMine extends BasePresentFragment<MineView> {
    @Override
    public Class<MineView> getPresentClass() {
        return MineView.class;
    }

    @Override
    public void onNetWorkErorRetry() {

    }

    public static FragmentMine newInstance() {
        FragmentMine fragment = new FragmentMine();
        return fragment;
    }

}
