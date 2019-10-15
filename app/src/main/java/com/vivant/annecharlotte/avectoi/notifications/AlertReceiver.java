package com.vivant.annecharlotte.avectoi.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * Receive alert launched by Main Activity
 */
public class AlertReceiver extends BroadcastReceiver {

    private static final String TAG = "AlertReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        displayNotif(context);
    }

    public void displayNotif(Context context) {
        Log.d(TAG, "displayNotif");
        NotificationResult mResultsSearchNotification = new NotificationResult(context);
    }
}
