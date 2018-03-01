package com.uowee.xmusic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.uowee.xmusic.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GuoWee on 2018/3/1.
 */

public class TabNetFragment extends BaseFragment {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_net, container, false);

        mViewPager = view.findViewById(R.id.viewpager);
        if (mViewPager != null) {
            setupViewPager(mViewPager);
        }

        mTabLayout = view.findViewById(R.id.tabs);
        mTabLayout.setTabTextColors(R.color.text_color, ThemeUtils.getThemeColorStateList(mActivity, R.color.colorPrimary).getDefaultColor());
        mTabLayout.setSelectedTabIndicatorColor(ThemeUtils.getThemeColorStateList(mActivity, R.color.colorPrimary).getDefaultColor());

        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }


    private void setupViewPager(ViewPager viewPager) {
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getChildFragmentManager());

        adapter.addFragment(new RecommendFragment(), getString(R.string.new_songs));
        adapter.addFragment(new AllPlaylistFragment(), getString(R.string.song_list));
        adapter.addFragment(new RankingFragment(), getString(R.string.song_rank));

        viewPager.setAdapter(adapter);
    }


    class MyViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();


        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
