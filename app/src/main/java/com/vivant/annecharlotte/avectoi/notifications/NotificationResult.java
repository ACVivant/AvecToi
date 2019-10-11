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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.core.app.NotificationCompat;

/**
 * Created by Anne-Charlotte Vivant on 10/10/2019.
 */
public class NotificationResult {
    private static final String TAG = "NotificationResult";
    private final int NOTIFICATION_ID = 007;
    private final String NOTIFICATION_TAG = "FIREBASESUPERTOI1";

    private static final String DESCRIPTION = "description";
    private static final String THEME = "theme";

    private String userId;
    private String[] stringMissionTab = new String[16];

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference eventsRef =  db.collection("events");

    private List<Integer> listHeros = new ArrayList<>();
    private List<Integer> listNewEvents = new ArrayList<>();
    String textHero = "";
    String textEvent ="";
    Date yesterday;
    Date tomorrow;
    Date eventDate;

    boolean displayHero = false;
    boolean displayEvent = false;
    boolean displayTomorrow = false;

    Context mContext;

    public NotificationResult(Context context) {
        mContext = context;

        Log.d(TAG, "NotificationResult");
    userId = UserHelper.getCurrentUserId();

    stringMissionTab[0] = mContext.getResources().getString(R.string.create_ironingSP);
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
    stringMissionTab[15] = mContext.getResources().getString(R.string.create_adminSP);

    displayEvent=getNewEvent();
        try {   // Il faut absolument que je comprenne comment éviter ça... et capter le retour de firebase dans le Main Thread
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    displayTomorrow=getTomorrowEvents();
        try {   // Il faut absolument que je comprenne comment éviter ça... et capter le retour de firebase dans le Main Thread
        Thread.sleep(2000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    //textEvent = createTextNotificationEvent(displayEvent, displayTomorrow, context);
        Log.d(TAG, "NotificationResult: displayEvent " + displayEvent + "displayTomorrow " + displayTomorrow);
        Log.d(TAG, "onMessageReceived: textEvent " + textEvent);

    // On envoie la notification
    //sendVisualNotification(textEvent, context);
}

    private boolean getNewEvent() {
        Log.d(TAG, "getNewEvent");

        eventsRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        int size = queryDocumentSnapshots.size();
                        Log.d(TAG, "onSuccess: il y a " + size +" événements");

                        final Calendar calendar1 = Calendar.getInstance();
                        Date today = calendar1.getTime();

                        calendar1.add(Calendar.DAY_OF_YEAR, -1);
                        yesterday = calendar1.getTime();

                        Log.d(TAG, "getMyHeros: yesterday " +yesterday);


                        for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots) {
                            final SosEvent event = documentSnapshot.toObject(SosEvent.class);
                            // On regarde si un événement correspond à un de mes super pouvoirs
                            final int eventIndex = event.getThemeIndex();
                            final Date eventCreated = event.getDateCreated();

                            calendar1.setTime(eventCreated);
                            final Date eventCreatedDay = calendar1.getTime();

                            Log.d(TAG, "onSuccess: today " + today);
                            Log.d(TAG, "onSuccess: eventCreated " + eventCreated);

                            long delay = today.getTime()-eventCreatedDay.getTime();
                            Log.d(TAG, "onSuccess: delay " + delay );

                            if (today.getTime()-eventCreatedDay.getTime()<86400000) {
                                Log.d(TAG, "onSuccess: delay OK");
                                UserHelper.getUser(userId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        User user = documentSnapshot.toObject(User.class);
                                        List<Integer> userSP = user.getUserSPList();
                                        Log.d(TAG, "onSuccess: userSP " + userSP);
                                        Log.d(TAG, "onSuccess: eventIndex " + eventIndex);
                                        if (userSP.contains(eventIndex)) {
                                            Log.d(TAG, "onSuccess: displayEvent " + displayEvent);
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

    public boolean getTomorrowEvents() {
        Log.d(TAG, "getTomorrowEventss");
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, +1);
        tomorrow = calendar.getTime();
        Log.d(TAG, "getMyHeros: tomorrow " +tomorrow);

        eventsRef.whereArrayContains("userHeroIdList", userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots) {
                            final SosEvent event = documentSnapshot.toObject(SosEvent.class);
                            eventDate = event.getDateNeed();
                            calendar.get(Calendar.DAY_OF_YEAR);
                            eventDate = calendar.getTime();

                            Log.d(TAG, "onSuccess: eventDate " + eventDate);
                            Log.d(TAG, "onSuccess: tomorrow " + tomorrow);
                            if (tomorrow.getTime() - eventDate.getTime()<100800000) {   // (de 20h aujourd'hui à minuit demain)
                                displayTomorrow = true;
                            }
                            Log.d(TAG, "onSuccess: displayTomorrow " + displayTomorrow);
                        }
                        createTextNotificationEvent(displayEvent, displayTomorrow, mContext);
                    }
                });

        return displayTomorrow;
    }
    //---------------------------------------------------------------------------
    // NOTIFICATION DESIGN
    //---------------------------------------------------------------------------

    private String createTextNotificationEvent(boolean displayEv, boolean displayTom, Context context) {
        Log.d(TAG, "createTextNotificationEvent: displayEv " + displayEv);
        Log.d(TAG, "createTextNotificationEvent: displayTom " + displayTom);
        String textToDisplay = "";
        if (displayTom) {
            textToDisplay += mContext.getResources().getString(R.string.dont_forget);

        }

        if (displayEv) {
            textToDisplay += mContext.getResources().getString(R.string.new_event_for_you);
        }

        Log.d(TAG, "createTextNotificationEvent: " + textToDisplay);
        sendVisualNotification(textToDisplay, mContext);
        return textToDisplay;
    }

    private void sendVisualNotification(String message1, Context context) {

        Log.d(TAG, "sendVisualNotification: message " + message1);

        /*NotificationHelper notificationHelper = new NotificationHelper(mContext);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification("Super Moi", message1);
        notificationHelper.getManager().notify(1, nb.build());*/

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
                        .setContentText(message1)
                        .setContentTitle(mContext.getResources().getString(R.string.notification_hero_title))
                        .setAutoCancel(true)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(message1))
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
