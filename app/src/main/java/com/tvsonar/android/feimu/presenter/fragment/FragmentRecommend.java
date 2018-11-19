package com.tvsonar.android.feimu.presenter.fragment;

import com.tvsonar.android.feimu.presenter.base.BasePresentFragment;
import com.tvsonar.android.feimu.view.RecommendView;

public class FragmentRecommend extends BasePresentFragment<RecommendView> {
    @Override
    public Class<RecommendView> getPresentClass() {
        return RecommendView.class;
    }

    @Override
    public void onNetWorkErorRetry() {

    }

    public static FragmentRecommend newInstance() {
        FragmentRecommend fragment = new FragmentRecommend();
        return fragment;
    }
}
