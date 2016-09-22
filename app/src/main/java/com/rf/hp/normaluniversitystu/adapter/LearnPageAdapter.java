package com.rf.hp.normaluniversitystu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * 资料共享展示的适配器
 * Created by hp on 2016/7/18.
 */
public class LearnPageAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> pager;
    public LearnPageAdapter(FragmentManager fm ,List<Fragment> pager) {
        super(fm);
        this.pager = pager;
    }

    @Override
    public Fragment getItem(int position) {

        return pager.get(position);
    }

    @Override
    public int getCount() {
        return pager.size();
    }
}
