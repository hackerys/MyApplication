package com.jansen.myapplication.animat;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created Jansen on 2016/7/15.
 */
public class CustomTransformer implements ViewPager.PageTransformer {

    private LinearLayout mLinearLayout;

    public CustomTransformer(LinearLayout mLinearLayout) {
        this.mLinearLayout = mLinearLayout;
    }

    @Override
    public void transformPage(View page, float position) {
        int pageWidth=page.getWidth();
        if (position < -1) {
            page.setAlpha(0f);
        } else if (position < 0) {
            page.setTranslationX(pageWidth * -position);
            page.setAlpha(Math.abs(1 + position));
        } else if (position <= 1) {
            //抵消位移效果
            page.setTranslationX(pageWidth * -position);
            page.setAlpha(Math.abs(1 - position));
        } else {
            page.setAlpha(0f);
        }
    }
}
