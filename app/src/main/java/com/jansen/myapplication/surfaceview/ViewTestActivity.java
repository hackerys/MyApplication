package com.jansen.myapplication.surfaceview;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created Jansen on 2016/5/30.
 */
public class ViewTestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MySurfaceView(this));
    }
}
