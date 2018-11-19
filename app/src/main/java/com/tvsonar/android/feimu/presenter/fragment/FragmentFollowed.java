package com.tvsonar.android.feimu.presenter.fragment;

import com.tvsonar.android.feimu.presenter.base.BasePresentFragment;
import com.tvsonar.android.feimu.view.FollowedView;

public class FragmentFollowed extends BasePresentFragment<FollowedView> {
    @Override
    public Class<FollowedView> getPresentClass() {
        return FollowedView.class;
    }

    @Override
    public void onNetWorkErorRetry() {

    }

    public static FragmentFollowed newInstance() {
        FragmentFollowed fragment = new FragmentFollowed();
        return fragment;
    }
}
