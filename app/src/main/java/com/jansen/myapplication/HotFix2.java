package com.jansen.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created Jansen on 2016/4/5.
 */
public class HotFix2 extends Activity {
    @Bind(R.id.fix)
    Button mFix;
    @Bind(R.id.toast)
    Button mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hot_fix_layout);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.fix, R.id.toast})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fix:
                break;
            case R.id.toast:
                Toast.makeText(HotFix2.this, "bug have fixed", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
