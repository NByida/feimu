package com.tvsonar.android.feimu.view;

import android.app.admin.FreezePeriod;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.tvsonar.android.feimu.R;
import com.tvsonar.android.feimu.presenter.fragment.FragmentFollowed;
import com.tvsonar.android.feimu.presenter.fragment.FragmentRecommend;
import com.tvsonar.android.feimu.view.base.MvpView;
import com.tvsonar.android.feimu.view.weidget.SmartToolbar;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import java.util.ArrayList;
import butterknife.BindView;

public class MainPageView extends MvpView {
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.view_pager_content)
    ViewPager viewPagerContent;
    @BindView(R.id.toolbar)
    SmartToolbar toolbar;
    private ArrayList<String> mTitleDataList;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_page;
    }

    @Override
    public View getToolbar() {
        return toolbar;
    }

    @Override
    public void regist(@NonNull LayoutInflater inflater, LifecycleProvider provider) {
        super.regist(inflater, provider);
        setStatusBarDark();
        toolbar.transparentWith();
        mTitleDataList=new ArrayList<>();
        mTitleDataList.add(getActivity().getString(R.string.follow));
        mTitleDataList.add(getActivity().getString(R.string.recommend));
        initViewPage();
        initPagerIndicator();
    }

    private void initViewPage(){
        ArrayList<Fragment> fragments=new ArrayList<>();
        fragments.add(FragmentFollowed.newInstance());
        fragments.add(FragmentRecommend.newInstance());
        viewPagerContent.setOffscreenPageLimit(2);
        viewPagerContent.setCurrentItem( 0,false);
        viewPagerContent.setAdapter(new FragmentPagerAdapter(getFragment("FragmentMainPage").getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
        viewPagerContent.setCurrentItem(0,false);
        viewPagerContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if(magicIndicator==null)return;
                magicIndicator.onPageSelected(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initPagerIndicator(){
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitleDataList == null ? 0 : mTitleDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
                colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
                colorTransitionPagerTitleView.setText(mTitleDataList.get(index));
                colorTransitionPagerTitleView.setOnClickListener(v->viewPagerContent.setCurrentItem(index));
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
    }
}
