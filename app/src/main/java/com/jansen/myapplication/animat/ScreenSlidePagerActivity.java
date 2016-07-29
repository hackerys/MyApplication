package com.jansen.myapplication.animat;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.jansen.myapplication.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created Jansen on 2016/7/15.
 */
public class ScreenSlidePagerActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {
    @Bind(R.id.pager)
    ViewPager mPager;
    FragmentManager mFragmentManager;
    @Bind(R.id.root_view)
    LinearLayout mRootView;
    private ArrayList<ScreenSlidePageFragment> mFragments;
    private ScreenSlidePagerAdapter mAdapter;
    private int current_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        mFragments = new ArrayList<>();
        mFragments.add(new ScreenSlidePageFragment(Color.WHITE));
        mFragments.add(new ScreenSlidePageFragment(Color.RED));
        mFragments.add(new ScreenSlidePageFragment(Color.BLUE));
        mFragments.add(new ScreenSlidePageFragment(Color.YELLOW));
        mFragmentManager = getSupportFragmentManager();
/*        FragmentTransaction mTransaction=mFragmentManager.beginTransaction();
        mTransaction.addToBackStack(null);
        mTransaction.commit();
        使用replace的时候用的
        */
        mAdapter = new ScreenSlidePagerAdapter(mFragmentManager);
        mPager.addOnPageChangeListener(this);
        mPager.setPageTransformer(true, new CustomTransformer(mRootView));
        mPager.setAdapter(mAdapter);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        current_item = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

/*    @Override
    public void onBackPressed() {
        if (current_item == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(current_item - 1,true);
        }
    }*/
}
