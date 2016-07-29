package com.jansen.myapplication.Notify;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import com.jansen.myapplication.BackStack.Activity4;
import com.jansen.myapplication.R;

import java.util.Timer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created Jansen on 2016/7/25.
 */
public class NotifyActivity2 extends Activity {
    @Bind(R.id.button1)
    Button mButton1;
    @Bind(R.id.button2)
    Button mButton2;
    private int num = 1;
    final int mNotificationId = 001;
    NotificationManager mNotifyMgr;
    Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo1_layout);
        ButterKnife.bind(this);
        // Gets an instance of the NotificationManager service
        mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @OnClick({R.id.button1, R.id.button2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                //intent
                Intent mIntent = new Intent(NotifyActivity2.this, Activity4.class);
                PendingIntent mIntent1 = TaskStackBuilder.create(this)
                        .addNextIntentWithParentStack(mIntent)
                        .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                //builder
                final android.support.v4.app.NotificationCompat.Builder mCompat = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_list_remove)
                        .setContentTitle("下载图片")
                        .setContentText("正在下载")
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentIntent(mIntent1);
                //发送通知
                mNotifyMgr.notify(mNotificationId, mCompat.build());
                //更新通知
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 1; i < 100; i += 5) {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException mE) {
                                mE.printStackTrace();
                            }
/*                            //有进度值
                            mCompat.setProgress(100, i, false);*/
                            //无进度值
                            mCompat.setProgress(0, 0, true);
                            mNotifyMgr.notify(mNotificationId, mCompat.build());
                        }
                        mCompat.setProgress(0, 0, false);
                        mCompat.setContentText("下载完成");
                        mNotifyMgr.notify(mNotificationId, mCompat.build());
                    }
                }).start();
                break;
            case R.id.button2:
                mNotifyMgr.cancel(mNotificationId);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }
}
