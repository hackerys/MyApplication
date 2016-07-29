package com.jansen.myapplication.BackStack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;
import android.widget.Button;

import com.jansen.myapplication.R;
import com.jansen.myapplication.utils.WnLogsUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created Jansen on 2016/7/25.
 */
public class Activity1 extends Activity {
    @Bind(R.id.button1)
    Button mButton1;
    @Bind(R.id.button2)
    Button mButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo1_layout);
        ButterKnife.bind(this);
        mButton2.setText("Activity1");
        WnLogsUtils.e("Activity1");

    }

    @OnClick({R.id.button1, R.id.button2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                Intent mIntent = new Intent(this, Activity4.class);
                Intent[] mIntents = TaskStackBuilder.create(this)
                        .addNextIntentWithParentStack(mIntent)
                        .getIntents();
                startActivities(mIntents);
                break;
            case R.id.button2:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        WnLogsUtils.e("onResume");
    }
}
