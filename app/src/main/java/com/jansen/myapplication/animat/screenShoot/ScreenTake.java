package com.jansen.myapplication.animat.screenShoot;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jansen.myapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created Jansen on 2016/7/18.
 */
public class ScreenTake extends Activity {
    @Bind(R.id.button)
    Button mButton;
    @Bind(R.id.preview)
    ImageView mPreview;
    @Bind(R.id.con)
    RelativeLayout mCon;
    @Bind(R.id.anim_image)
    ImageView mAnimImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_screen);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button, R.id.preview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                mCon.setDrawingCacheEnabled(true);
                mCon.buildDrawingCache();
                Bitmap mBitmap = mCon.getDrawingCache();
                if (mBitmap != null) {
                    mAnimImage.setImageBitmap(mBitmap);
                    mAnimImage.setVisibility(View.VISIBLE);
/*                    mAnimImage.animate()
                            .setDuration(500)
                            .scaleX(0.1f)
                            .scaleY(0.1f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                }
                            })
                            .start();*/

/*                    AnimatorSet mSet = new AnimatorSet();
                    mSet.play(ObjectAnimator.ofFloat(mAnimImage, View.SCALE_X, 1f, 0.1f))
                            .with(ObjectAnimator.ofFloat(mAnimImage, View.SCALE_Y, 1f, 0.1f))
                            .with(ObjectAnimator.ofFloat(mAnimImage, View.ALPHA, 1f, 0f));
                    mSet.setDuration(500);
                    mSet.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                        }
                    });
                    mSet.start();*/

/*                    ValueAnimator mAnimator = ValueAnimator.ofFloat(1f, 0f);
                    mAnimator.setTarget(mAnimImage);
                    mAnimator.setDuration(500);
                    mAnimator.start();
                    mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float scale = (float) animation.getAnimatedValue();
                            mAnimImage.setScaleX(scale);
                            mAnimImage.setScaleY(scale);
                            mAnimImage.setAlpha(scale);
                        }
                    });*/

                    AnimationSet mSet = new AnimationSet(true);
                    ScaleAnimation mScaleAnimation = new ScaleAnimation(1f, 0f, 1f, 0f);
                    AlphaAnimation mAlphaAnimation = new AlphaAnimation(1f, 0f);
                    mSet.addAnimation(mScaleAnimation);
                    mSet.addAnimation(mAlphaAnimation);
                    mSet.setDuration(500);
                    mAnimImage.startAnimation(mSet);
                }

                break;
            case R.id.preview:
                break;
        }
    }

}
