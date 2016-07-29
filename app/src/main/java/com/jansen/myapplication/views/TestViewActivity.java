package com.jansen.myapplication.views;

import android.app.Activity;
import android.os.Bundle;

import com.jansen.myapplication.R;

/**
 * Created Jansen on 2016/7/25.
 */
public class TestViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_view_layout);
    }
}
