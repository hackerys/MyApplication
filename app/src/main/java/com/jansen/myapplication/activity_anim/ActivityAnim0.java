package com.jansen.myapplication.activity_anim;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jansen.myapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created Jansen on 2016/7/28.
 */
public class ActivityAnim0 extends Activity {
    @Bind(R.id.button1)
    Button mButton1;
    @Bind(R.id.button2)
    Button mButton2;
    @Bind(R.id.con)
    LinearLayout mCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo1_layout);
        ButterKnife.bind(this);
        mCon.setBackgroundColor(Color.BLUE);
    }

    @OnClick({R.id.button1, R.id.button2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                Intent mIntent = new Intent(this, ActivityAnim1.class);
                startActivity(mIntent);
                //                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.button2:
                break;
        }
    }
}
