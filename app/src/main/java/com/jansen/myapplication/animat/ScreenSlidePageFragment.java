package com.jansen.myapplication.animat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jansen.myapplication.R;

/**
 * Created Jansen on 2016/7/15.
 */
public class ScreenSlidePageFragment extends Fragment {
    private int color;

    public ScreenSlidePageFragment(int mColor) {
        color = mColor;
    }

    public ScreenSlidePageFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup mViewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
        mViewGroup.setBackgroundColor(color);
        return mViewGroup;
    }
}
