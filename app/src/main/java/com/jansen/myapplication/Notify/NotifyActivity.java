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
import com.jansen.myapplication.Demo1;
import com.jansen.myapplication.R;

import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created Jansen on 2016/7/25.
 */
public class NotifyActivity extends Activity {
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
                // Sets up the Snooze and Dismiss action buttons that will appear in the
                // big view of the notification.
                Intent dismissIntent = new Intent(this, PingService.class);
                dismissIntent.setAction("dismiss");
                PendingIntent piDismiss = PendingIntent.getService(this, 0, dismissIntent, 0);
                //intentservice只能对应一个Intent
                Intent snoozeIntent = new Intent(this, PingService.class);
                dismissIntent.setAction("snoop");
                PendingIntent piSnooze = PendingIntent.getService(this, 0, snoozeIntent, 0);

                final android.support.v4.app.NotificationCompat.Builder mCompat = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_list_remove)
                        .setContentTitle("这是标题")
                        .setContentText("这是内容")
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("大文字信息"))
                        .addAction(R.drawable.ic_list_remove,
                                "dismiss", piDismiss)
                        .addAction(R.drawable.btn_save,
                                "snooze", piSnooze);

                Intent mIntent = new Intent(NotifyActivity.this, Activity4.class);
                PendingIntent mIntent1 = TaskStackBuilder.create(this)
                        .addNextIntentWithParentStack(mIntent)
                        .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                //直接打开activity没有回退栈
                //                PendingIntent mIntent1 = PendingIntent.getActivity(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                mCompat.setContentIntent(mIntent1);
                // Sets an ID for the notification
                mNotifyMgr.notify(mNotificationId, mCompat.build());

/*                mTimer = new Timer();

                TimerTask mTask = new TimerTask() {
                    @Override
                    public void run() {

                        // Builds the notification and issues it.
                        mNotifyMgr.notify(mNotificationId, mCompat.build());


                        num++;
                        mCompat.setContentText("发送了:" + num + "次");
                    }
                };
                mTimer.schedule(mTask, 500, 1000);*/
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
