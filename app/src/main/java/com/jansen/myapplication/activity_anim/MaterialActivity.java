package com.jansen.myapplication.activity_anim;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jansen.myapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created Jansen on 2016/7/28.
 */
public class MaterialActivity extends AppCompatActivity {
    @Bind(R.id.button1)
    Button mButton1;
    @Bind(R.id.button2)
    Button mButton2;
    @Bind(R.id.my_textview)
    TextView mMyTextview;
    @Bind(R.id.con)
    LinearLayout mCon;
    @Bind(R.id.img_source)
    ImageView mImgSource;
    @Bind(R.id.button3)
    Button mButton3;
    @Bind(R.id.button4)
    Button mButton4;
    @Bind(R.id.button5)
    Button mButton5;
    @Bind(R.id.button6)
    Button mButton6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo1_layout);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6})
    public void onClick(View view) {
        Intent mIntent = new Intent(MaterialActivity.this, ActivityAnim0.class);

        switch (view.getId()) {
            case R.id.button1:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //5.0以上
                    getWindow().setExitTransition(new Explode());
                    startActivity(mIntent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    //4.1以上
                    startActivity(mIntent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
                } else {
                    //minsdk以上
                    //overridingpending...
                    startActivity(mIntent);
                }

                break;
            case R.id.button2:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    //4.1以上
                    ActivityOptionsCompat mCompat = ActivityOptionsCompat
                            .makeCustomAnimation(this, R.anim.slide_bottom_in, R.anim.slide_bottom_out);
                    startActivity(mIntent, mCompat.toBundle());
                }
                break;
            case R.id.button3:
                //4.1以上
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    Intent mIntent1 = new Intent(MaterialActivity.this, MaterialActivity2.class);
                    ActivityOptionsCompat mCompat = ActivityOptionsCompat
                            .makeScaleUpAnimation(mImgSource, mImgSource.getWidth() / 2, mImgSource.getHeight() / 2, 0, 0);
                    startActivity(mIntent1, mCompat.toBundle());
                }
                break;
            case R.id.button4:
                //4.1以上
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    Intent mIntent1 = new Intent(MaterialActivity.this, MaterialActivity2.class);
                    Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image2);
                    ActivityOptionsCompat mCompat = ActivityOptionsCompat
                            .makeThumbnailScaleUpAnimation(mImgSource, mBitmap, 0, 0);
                    startActivity(mIntent1, mCompat.toBundle());
                }
                break;
            case R.id.button5:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    Intent mIntent1 = new Intent(MaterialActivity.this, MaterialActivity2.class);
                    ActivityOptionsCompat mCompat = ActivityOptionsCompat
                            .makeSceneTransitionAnimation(this, mImgSource, getResources().getString(R.string.img_scale));
                    startActivity(mIntent1, mCompat.toBundle());
                }
                break;
            case R.id.button6:
                Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image1);
                Palette mPalette = Palette.from(mBitmap).generate();
                mButton6.setBackgroundColor(mPalette.getLightVibrantColor(Color.BLUE));
                break;
        }
    }


}
