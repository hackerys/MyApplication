package com.jansen.myapplication.dialogActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;

import com.jansen.myapplication.R;
import com.jansen.myapplication.utils.WnLogsUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created Jansen on 2016/7/4.
 */
public class DialogActivity extends Activity {
    @Bind(R.id.button)
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        ButterKnife.bind(this);
        PackageManager pm = getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_NFC)) {
            WnLogsUtils.e("Android Beam file transfer isn't supported");
            /*
             * Disable NFC features here.
             * For example, disable menu items or buttons that activate
             * NFC-related features
             */
            // Android Beam file transfer isn't supported
        }else {
            WnLogsUtils.e("支持NFC");
        }
    }

    @OnClick(R.id.button)
    public void onClick() {
        Intent mIntent = new Intent(this, MenuActivity.class);
        startActivity(mIntent);
    }
}
