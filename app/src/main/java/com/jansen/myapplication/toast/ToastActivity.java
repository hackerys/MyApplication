package com.jansen.myapplication.toast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jansen.myapplication.R;
import com.jansen.myapplication.utils.WnLogsUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created Jansen on 2016/7/21.
 */
public class ToastActivity extends Activity {
    @Bind(R.id.button1)
    Button mButton1;
    @Bind(R.id.button2)
    Button mButton2;
    @Bind(R.id.button3)
    Button mButton3;
    @Bind(R.id.button4)
    Button mButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toast_layout);
        ButterKnife.bind(this);
        WnLogsUtils.e(Thread.currentThread() + "");
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                Intent mIntent = new Intent(this, ToastService.class);
                startService(mIntent);
                break;
            case R.id.button2:
                new Thread() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        Toast.makeText(ToastActivity.this, "ToastActivity" + Thread.currentThread(), Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }.start();
                break;
            case R.id.button3:
                break;
            case R.id.button4:
                break;
        }
    }
}
