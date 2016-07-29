package com.jansen.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created Jansen on 2016/4/14.
 */
public class RestartApp extends Activity {
    @Bind(R.id.restart)
    Button mRestart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restart_app_layout);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.restart)
    public void onClick() {
        int a = 0;
        int b = 10 / a;
    }
}
