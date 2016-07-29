package com.jansen.myapplication;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.TextView;

/**
 * Created Jansen on 2016/4/25.
 */
public class Demo1 extends Activity {
    private int a = 0;
    private int key = 0;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewConfiguration configuration = ViewConfiguration.get(this);
       ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        //输出屏幕方向
        getOrintation();
        setContentView(R.layout.demo1_layout);
        a = 10;
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key = 10;
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("a->", a + "");
                Log.e("key->", key + "");
            }
        });
    }

    private void getOrintation() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.e("onCreate", "横屏");
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.e("onCreate", "竖屏");
        }
    }
}
