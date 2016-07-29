package com.jansen.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jansen.myapplication.base.MyApplication;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created Jansen on 2016/4/5.
 */
public class HotFix extends Activity {
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
                MyApplication myApplication = (MyApplication) getApplication();
                File patch = new File(
                        Environment.getExternalStorageDirectory(), "patch.jar");
                Log.e("file:", "" + patch.exists());
                myApplication.getFixBugManage().addPatch(patch.getAbsolutePath());
                break;
            case R.id.toast:
                //Toast.makeText(HotFix.this, "bug", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
