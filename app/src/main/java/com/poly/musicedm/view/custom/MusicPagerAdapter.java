package com.poly.musicedm.view.custom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.poly.musicedm.util.Constant;
import com.poly.musicedm.view.fragment.MusicFragment;

import java.util.List;

/**
 * Created by Vuong_IT on 30/10/2017.
 */

public class MusicPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> listTitle;

    public MusicPagerAdapter(List<String> listTitle, FragmentManager fm) {
        super(fm);
        this.listTitle = listTitle;
    }

    @Override
    public Fragment getItem(int position) {
        MusicFragment fragment = new MusicFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.BUNDLE_POSITION_ORDER, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listTitle.get(position);
    }

    @Override
    public int getCount() {
        return 4;
    }
}
