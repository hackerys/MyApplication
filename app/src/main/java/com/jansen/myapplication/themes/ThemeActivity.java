package com.jansen.myapplication.themes;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.widget.ShareActionProvider;
import android.view.View;
import android.widget.Button;

import com.jansen.myapplication.R;
import com.jansen.myapplication.utils.WnLogsUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created Jansen on 2016/7/7.
 */
public class ThemeActivity extends Activity {
    @Bind(R.id.button)
    Button mButton;
    @Bind(R.id.phone)
    Button mPhone;
    @Bind(R.id.map)
    Button mMap;
    @Bind(R.id.web)
    Button mWeb;
    @Bind(R.id.send)
    Button mSend;
    @Bind(R.id.pick)
    Button mPick;
    @Bind(R.id.baterry)
    Button mBaterry;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme_layout);
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            Uri mUri = data.getData();
            WnLogsUtils.e(mUri.toString());
            String[] colum = {ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor mCursor = getContentResolver().query(mUri, colum, null, null, null);
            if (mCursor != null) {
                mCursor.moveToFirst();
                int co = mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String num = mCursor.getString(co);
                WnLogsUtils.e(num);
            }
        }
    }

    @OnClick({R.id.button, R.id.phone, R.id.map, R.id.web, R.id.send, R.id.pick, R.id.baterry})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                Intent mIntent = new Intent(this, DiaActivity.class);
                startActivity(mIntent);
                break;
            case R.id.phone:
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:18173382861"));
                startActivity(phoneIntent);
                break;
            case R.id.map:
                //如果没有
                Uri location = Uri.parse("geo:37.422219,-122.08364?z=14");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                startActivity(mapIntent);
                break;
            case R.id.web:
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"));
                PackageManager packageManager = getPackageManager();
                List activities = packageManager.queryIntentActivities(webIntent,
                        PackageManager.MATCH_DEFAULT_ONLY);
                boolean isIntentSafe = activities.size() > 0;
                WnLogsUtils.e("" + activities.size());
                startActivity(webIntent);
                break;
            case R.id.send:
/*                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "a.jpg")));
                shareIntent.setType("image/jpg");
                startActivity(Intent.createChooser(shareIntent, "发送图片"));*/
                //                MediaNotify mNotify = new MediaNotify(this);

                ArrayList<Uri> imageUris = new ArrayList<Uri>();
                imageUris.add(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "a.jpg"))); // Add your image URIs here
                imageUris.add(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "IMG1467618632092.png")));

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
                shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                shareIntent.setType("image/*");
                startActivity(Intent.createChooser(shareIntent, "Share images to.."));
                break;
            case R.id.pick:
                Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                startActivityForResult(pickContactIntent, 1);
                break;
            case R.id.baterry:
                Intent batteryIntent = getApplicationContext().registerReceiver(null,
                        new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                int currLevel = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                int total = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, 1);
                int percent = currLevel * 100 / total;
                WnLogsUtils.e("battery", "battery: " + percent + "%");
                break;
        }
    }

    public void onScanCompleted(Uri mUri) {
        WnLogsUtils.e(mUri.toString());
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://media/external/images/media/20743"));
        shareIntent.setType("image/jpg");
        startActivity(Intent.createChooser(shareIntent, "发送图片"));
    }

}
