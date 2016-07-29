package com.jansen.myapplication.toast;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created Jansen on 2016/7/21.
 */
public class ToastService extends Service {
    public static final String DATA = "yansheng";
    private MyBinder mMyBinder=new MyBinder();
    private static class MyBinder extends Binder {
        public String getData() {
            return DATA;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMyBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(ToastService.this, DATA+Thread.currentThread(), Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }
}
