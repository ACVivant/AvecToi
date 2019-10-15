package com.vivant.annecharlotte.avectoi.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vivant.annecharlotte.avectoi.R;
import com.vivant.annecharlotte.avectoi.WelcomeActivity;
import com.vivant.annecharlotte.avectoi.firestore.SosEvent;
import com.vivant.annecharlotte.avectoi.firestore.User;
import com.vivant.annecharlotte.avectoi.firestore.UserHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.core.app.NotificationCompat;

/**
 * Personnalisation of notifications launched by AlarmManager
 */
public class NotificationResult {
    private static final String TAG = "NotificationResult";
    private final int NOTIFICATION_ID = 007;
    private final String NOTIFICATION_TAG = "FIREBASESUPERTOI1";

    private String userId;
    private String[] stringMissionTab = new String[16];

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference eventsRef =  db.collection("events");

    private Date today;
    private Date yesterday;
    private Date eventDate;
    private Calendar calendar1;

    boolean displayEvent = false;
    boolean displayTomorrow = false;

    private Context mContext;

    // This notification aims to inform users if a new event corresponding with his talents has been created, or if he has an engagement for tomorrow
    public NotificationResult(Context context) {
        mContext = context;

        userId = UserHelper.getCurrentUserId();

        String[] themeArray = mContext.getResources().getStringArray(R.array.event_theme);

        for (int i=0; i<16; i++) {
            stringMissionTab[i] = themeArray[i];
        }

        /*stringMissionTab[0] = mContext.getResources().getString(R.string.create_ironingSP);
        stringMissionTab[1] = mContext.getResources().getString(R.string.create_householdSP);
        stringMissionTab[2] = mContext.getResources().getString(R.string.create_shoppingSP);
        stringMissionTab[3] = mContext.getResources().getString(R.string.create_cookingSP);
        stringMissionTab[4] = mContext.getResources().getString(R.string.create_driveSP);
        stringMissionTab[5] = mContext.getResources().getString(R.string.create_gardeningSP);
        stringMissionTab[6] = mContext.getResources().getString(R.string.create_diySP);
        stringMissionTab[7] = mContext.getResources().getString(R.string.create_worksSP);
        stringMissionTab[8] = mContext.getResources().getString(R.string.create_relocationSP);
        stringMissionTab[9] = mContext.getResources().getString(R.string.create_readingSP);
        stringMissionTab[10] = mContext.getResources().getString(R.string.create_babysittingSP);
        stringMissionTab[11] = mContext.getResources().getString(R.string.create_sewingSP);
        stringMissionTab[12] = mContext.getResources().getString(R.string.create_flowerSP);
        stringMissionTab[13] = mContext.getResources().getString(R.string.create_tutoringSP);
        stringMissionTab[14] = mContext.getResources().getString(R.string.create_companySP);
        stringMissionTab[15] = mContext.getResources().getString(R.string.create_adminSP);*/

        // We will compare dates of events with today date
        calendar1 = Calendar.getInstance();
        today = calendar1.getTime();

        // Is there a new event with user's power?
        displayEvent=getNewEvent();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // is there an users engagmeent for tomorrow?
        displayTomorrow=getTomorrowEvents();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Is there a new event with user's power
    private boolean getNewEvent() {

        eventsRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        calendar1.add(Calendar.DAY_OF_YEAR, -1);
                        yesterday = calendar1.getTime();

                        for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots) {
                            final SosEvent event = documentSnapshot.toObject(SosEvent.class);
                            final int eventIndex = event.getThemeIndex();
                            final Date eventCreated = event.getDateCreated();

                            calendar1.setTime(eventCreated);
                            final Date eventCreatedDay = calendar1.getTime();

                            if (today.getTime()-eventCreatedDay.getTime()<1000*60*60*24) {  // We want only events created last 24h
                                UserHelper.getUser(userId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        User user = documentSnapshot.toObject(User.class);
                                        List<Integer> userSP = user.getUserSPList();

                                        if (userSP.contains(eventIndex)) {
                                            // notif events has to be displayed
                                            displayEvent = true;
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
        return displayEvent;
    }

    // Is there an users engagmeent for tomorrow?
    public boolean getTomorrowEvents() {

        eventsRef.whereArrayContains("userHeroIdList", userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots) {
                            final SosEvent event = documentSnapshot.toObject(SosEvent.class);
                            eventDate = event.getDateNeed();

                            if (eventDate.getTime() -today.getTime() <1000*60*60*28 && eventDate.getTime() -today.getTime()> 1000*60*60*4) {    // We want only events for tomorrow (notification is programmated at 20h -> +4h +28h)
                                // notification tomorrow has to be displayed
                                displayTomorrow = true;
                            }
                        }
                        createTextNotificationEvent(displayEvent, displayTomorrow);
                    }
                });

        return displayTomorrow;
    }
    //---------------------------------------------------------------------------
    // NOTIFICATION DESIGN
    //---------------------------------------------------------------------------

    // Text of notification is adapted according to results of getEventsToday() and getTomorrowEvents()
    private void createTextNotificationEvent(boolean displayEv, boolean displayTom) {
        String textToDisplay = "";
        if (displayTom) {
            textToDisplay += mContext.getResources().getString(R.string.dont_forget);

        }

        if (displayEv) {
            textToDisplay += mContext.getResources().getString(R.string.new_event_for_you);
        }

        if (displayEv || displayTom) {sendVisualNotification(textToDisplay);}
    }

    private void sendVisualNotification(String message) {
        Intent openAppIntent = new Intent(mContext, WelcomeActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, openAppIntent, 0);
        //  Create a Style for the Notification
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle(mContext.getResources().getString(R.string.notification_hero_title));

        //  Create a Channel (Android 8)
        String channelId = mContext.getResources().getString(R.string.default_notification_channel_id);

        // Create the icon
        Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.vignetteheros);

        //  Build a Notification object
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(mContext, channelId)
                        .setSmallIcon(R.mipmap.my_ic_launcher_round)
                        .setLargeIcon(icon)
                        .setContentTitle(mContext.getResources().getString(R.string.app_name))
                        .setContentText(message)
                        .setContentTitle(mContext.getResources().getString(R.string.notification_hero_title))
                        .setAutoCancel(true)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(message))
                        .setContentIntent(contentIntent);

        //  Add the Notification to the Notification Manager and show it.
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        //  Support Version >= Android 8
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "Firebase Message";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        //  Show notification
        notificationManager.notify(NOTIFICATION_TAG, NOTIFICATION_ID, notificationBuilder.build());
    }
}
