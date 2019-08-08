package com.vivant.annecharlotte.avectoi;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.events.Subscriber;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.platforminfo.UserAgentPublisher;

import java.util.Map;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

/**
 * Created by Anne-Charlotte Vivant on 08/08/2019.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessagingServ";

    private static final String DESCRIPTION = "description";
    private static final String THEME = "theme";


    @Override
    public void onNewToken(String s) {
        Log.d(TAG,"New Token " + s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size()>0) {
            Map<String, String> data = remoteMessage.getData();
            Log.d(TAG, "onMessageReceived ");

            String description = data.get(DESCRIPTION);
            String theme = data.get(THEME);

            String channelId = getString(R.string.default_notification_channel_id);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle(theme);
            builder.setContentText(description);

            Intent resultIntent = new Intent(this, MainActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(resultPendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, builder.build());
        }
    }
}
