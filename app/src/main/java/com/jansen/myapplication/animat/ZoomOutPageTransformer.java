package com.jansen.myapplication.animat;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created Jansen on 2016/7/15.
 */
public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    /**
     * 最小放缩比例
     */
    private static final float MIN_SCALE = 0.85f;
    /**
     * 最小的透明值
     */
    private static final float MIN_ALPHA = 0.5f;

    public void transformPage(View view, float position) {
        /**
         * 组件的宽
         */
        int pageWidth = view.getWidth();
        /**
         * 组件的高
         */
        int pageHeight = view.getHeight();
        /**
         * 只处理position -1<=position<=1的情况，即我们看得到的页面
         */
        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);

        } else if (position <= 1) { // [-1,1]
            // Modify the default slide transition to shrink the page as well
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            //抵消默认的动画平移
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                view.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                view.setTranslationX(-horzMargin + vertMargin / 2);
            }

            // Scale the page down (between MIN_SCALE and 1)
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

            // Fade the page relative to its size.
            view.setAlpha(MIN_ALPHA +
                    (scaleFactor - MIN_SCALE) /
                            (1 - MIN_SCALE) * (1 - MIN_ALPHA));

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }
}
