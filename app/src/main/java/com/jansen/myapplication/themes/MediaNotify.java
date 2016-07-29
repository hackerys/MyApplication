package com.jansen.myapplication.themes;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;

import com.jansen.myapplication.utils.WnLogsUtils;

/**
 * Created Jansen on 2016/7/11.
 */
public class MediaNotify implements MediaScannerConnection.MediaScannerConnectionClient {
    private ThemeActivity mContext;
    private MediaScannerConnection mConnection;
    public static final String SD = Environment.getExternalStorageDirectory().getAbsolutePath();

    public MediaNotify(Context mContext) {
        this.mContext = (ThemeActivity) mContext;
        mConnection = new MediaScannerConnection(mContext, this);
        mConnection.connect();
        WnLogsUtils.e(SD);
    }

    @Override
    public void onMediaScannerConnected() {
        if (mConnection.isConnected()) {
            mConnection.scanFile(SD + "/a.jpg", "image/*");
        }
    }

    @Override
    public void onScanCompleted(String path, Uri uri) {
        WnLogsUtils.e(uri.toString());
        mContext.onScanCompleted(uri);
        mConnection.disconnect();
    }
}
