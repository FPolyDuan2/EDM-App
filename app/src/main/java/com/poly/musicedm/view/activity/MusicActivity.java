package com.poly.musicedm.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;

import com.poly.musicedm.R;
import com.poly.musicedm.view.base.BaseActivity;
import com.poly.musicedm.view.custom.MusicPagerAdapter;
import com.poly.musicedm.view.custom.MusicViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MusicActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.vpMusic)
    MusicViewPager vpMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        ButterKnife.bind(this);
        initToolbar();
        initView();
        initViewPager();

    }

    private void initView() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(mActivity, drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void initViewPager() {
        List<String> listTitle = new ArrayList<>();
        listTitle.add(getString(R.string.best).toUpperCase());
        listTitle.add(getString(R.string.artists).toUpperCase());
        listTitle.add(getString(R.string.genres).toUpperCase());
        listTitle.add(getString(R.string.favorite).toUpperCase());

        MusicPagerAdapter pagerAdapter = new MusicPagerAdapter(listTitle, getSupportFragmentManager());
        vpMusic.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(vpMusic);
        vpMusic.setEnableSwipe(true);
    }

    @OnClick(R.id.imgMenu)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgMenu:
                if (drawerLayout.isDrawerOpen(Gravity.START))
                    drawerLayout.closeDrawer(Gravity.START);
                else
                    drawerLayout.openDrawer(Gravity.START);
                break;

        }
    }
}
