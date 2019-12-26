package com.flyco.tablayout.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


/**
 * Created by tlol20 on 2017/6/2
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    private Fragment[] fragments;
    private String[] mTabTitles = new String[]{};
    private int[] icons = new int[]{};

    public FragmentAdapter(FragmentManager fm, Fragment[] fragments, String[] mTabTitles, int[] mIcon) {
        super(fm);
        this.fragments = fragments;
        this.mTabTitles = mTabTitles;
        this.icons = mIcon;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTabTitles != null) {
            return mTabTitles[position];
        }
        return super.getPageTitle(position);
    }

    public int getIcon(int position) {
        if (icons != null) {
            return icons[position];
        }
        return 0;

    }
}
