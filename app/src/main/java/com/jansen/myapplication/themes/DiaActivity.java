package com.jansen.myapplication.themes;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.jansen.myapplication.R;

/**
 * Created Jansen on 2016/7/7.
 */
public class DiaActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.theme_dia_layout);
    }
}
