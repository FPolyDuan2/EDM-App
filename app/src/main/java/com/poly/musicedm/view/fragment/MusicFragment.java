package com.poly.musicedm.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.poly.musicedm.R;
import com.poly.musicedm.util.Constant;
import com.poly.musicedm.view.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Vuong_IT on 28/10/2017.
 */

public class MusicFragment extends BaseFragment {

    public static final int BEST = 0;
    public static final int ARTISTS = 1;
    public static final int GENRES = 2;
    public static final int FAVORITE = 3;

    int typeList;
    @BindView(R.id.tvNameFragment)
    TextView tvNameFragment;
    Unbinder unbinder;


    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View v) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            typeList = bundle.getInt(Constant.BUNDLE_POSITION_ORDER, BEST);

            if (typeList == BEST) {
                tvNameFragment.setText(R.string.best);
            } else if (typeList == ARTISTS) {
                tvNameFragment.setText(R.string.artists);
            } else if (typeList == GENRES) {
                tvNameFragment.setText(R.string.genres);
            } else if (typeList == FAVORITE) {
                tvNameFragment.setText(R.string.favorite);
            } else {

            }
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_music;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
