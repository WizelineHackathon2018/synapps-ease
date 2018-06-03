package com.comynt.launa.adapt;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapterGraficos extends FragmentStatePagerAdapter {

    private final int PAGES = 2;
    private String[] titles={"VISITA", "COBRANZA"};

    public ViewPagerAdapterGraficos(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TabFragment4();
            case 1:
                return new TabFragment5();
            default:
                throw new IllegalArgumentException("The item position should be less or equal to:" + PAGES);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return PAGES;
    }
}
