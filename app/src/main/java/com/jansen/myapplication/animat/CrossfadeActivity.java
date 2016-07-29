package com.jansen.myapplication.animat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jansen.myapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created Jansen on 2016/7/15.
 */
public class CrossfadeActivity extends Activity {
    @Bind(R.id.button)
    Button mButton;
    @Bind(R.id.blue)
    TextView mBlue;
    @Bind(R.id.red)
    TextView mRed;
    private int duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fade_layout);
        ButterKnife.bind(this);
        mBlue.setVisibility(View.GONE);
        duration = getResources().getInteger(android.R.integer.config_shortAnimTime);
    }

    @OnClick(R.id.button)
    public void onClick() {
        fade();
    }

    private void fade() {
        //将要展现的组件
        mBlue.setAlpha(0);
        mBlue.setVisibility(View.VISIBLE);
        mBlue.animate()
                .alpha(1f)
                .setDuration(duration)
                .setListener(null);
        //将要消失的组件
        mRed.animate()
                .alpha(0f)
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mRed.setVisibility(View.GONE);
                    }
                });
    }
}
