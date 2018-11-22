package com.carozhu.fastdev.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Fragment adapter
 */
public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList;
    String[] tabTitleList;

    public TabFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] tabTitleList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.tabTitleList = tabTitleList;



    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitleList[position];
    }
}
