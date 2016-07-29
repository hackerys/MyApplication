package com.jansen.myapplication.Notify;

import android.app.IntentService;
import android.content.Intent;

import com.jansen.myapplication.utils.WnLogsUtils;

/**
 * Created Jansen on 2016/7/25.
 */
public class PingService extends IntentService {
    public static final String DISMISS = "dismiss";
    public static final String SNOOP = "snoop";

    public PingService() {
        super("PingService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        WnLogsUtils.e(action);

        if (DISMISS.equals(action)) {
            WnLogsUtils.e(DISMISS + Thread.currentThread());
        }

        if (SNOOP.equals(action)) {
            WnLogsUtils.e(SNOOP + Thread.currentThread());
        }
    }
}
