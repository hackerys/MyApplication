package com.jansen.myapplication.activity_anim;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.jansen.myapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created Jansen on 2016/7/28.
 */
public class MaterialActivity2 extends AppCompatActivity {
    @Bind(R.id.img_result)
    ImageView mImgResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matetial_a_layout);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.finishAfterTransition();
        }
    }
}
