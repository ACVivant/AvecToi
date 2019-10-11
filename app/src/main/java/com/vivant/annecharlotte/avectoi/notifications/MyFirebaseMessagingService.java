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
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.events.Subscriber;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.platforminfo.UserAgentPublisher;
import com.vivant.annecharlotte.avectoi.MainActivity;
import com.vivant.annecharlotte.avectoi.R;
import com.vivant.annecharlotte.avectoi.WelcomeActivity;
import com.vivant.annecharlotte.avectoi.firestore.SosEvent;
import com.vivant.annecharlotte.avectoi.firestore.User;
import com.vivant.annecharlotte.avectoi.firestore.UserHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

/**
 * Created by Anne-Charlotte Vivant on 08/08/2019.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessagingServ";
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

    @Override
    public void onNewToken(String s) {
        Log.d(TAG,"New Token " + s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived ");
        if (remoteMessage.getData().size()>0) {
            Map<String, String> data = remoteMessage.getData();
            Log.d(TAG, "onMessageReceived: data " +data.toString());
            Log.d(TAG, "onMessageReceived: data size " + data.size());

            String channelId = getString(R.string.default_notification_channel_id);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);

            // Create the icon and text
            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.vignetteheros);
            String message = data.get("body");
            String title = data.get("title");

            //  Build a Notification object
            builder .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(icon)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setContentTitle(getString(R.string.notification_hero_title))
                    .setAutoCancel(true)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(message));

            Intent resultIntent = new Intent(this, MainActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(resultPendingIntent);

            //  Support Version >= Android 8
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence channelName = "Firebase Message";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
                notificationManager.createNotificationChannel(mChannel);
            }

            //  Show notification
            notificationManager.notify(0, builder.build());
        } else {
            Log.d(TAG, "onMessageReceived: else");
            userId = UserHelper.getCurrentUserId();

            stringMissionTab[0] = getResources().getString(R.string.create_ironingSP);
            stringMissionTab[1] = getResources().getString(R.string.create_householdSP);
            stringMissionTab[2] = getResources().getString(R.string.create_shoppingSP);
            stringMissionTab[3] = getResources().getString(R.string.create_cookingSP);
            stringMissionTab[4] = getResources().getString(R.string.create_driveSP);
            stringMissionTab[5] = getResources().getString(R.string.create_gardeningSP);
            stringMissionTab[6] = getResources().getString(R.string.create_diySP);
            stringMissionTab[7] = getResources().getString(R.string.create_worksSP);
            stringMissionTab[8] = getResources().getString(R.string.create_relocationSP);
            stringMissionTab[9] = getResources().getString(R.string.create_readingSP);
            stringMissionTab[10] = getResources().getString(R.string.create_babysittingSP);
            stringMissionTab[11] = getResources().getString(R.string.create_sewingSP);
            stringMissionTab[12] = getResources().getString(R.string.create_flowerSP);
            stringMissionTab[13] = getResources().getString(R.string.create_tutoringSP);
            stringMissionTab[14] = getResources().getString(R.string.create_companySP);
            stringMissionTab[15] = getResources().getString(R.string.create_adminSP);

            getNewEvent();
            getTomorrowEvents();
            try {   // Il faut absolument que je comprenne comment éviter ça... et capter le retour de firebase dans le Main Thread
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            textEvent = createTextNotificationEvent(displayEvent, displayTomorrow);
            Log.d(TAG, "onMessageReceived: textEvent " + textEvent);

            // On envoie la notification
            sendVisualNotification(textEvent);
        }
    }
    private void getNewEvent() {
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
                            //calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR);

                            Log.d(TAG, "onSuccess: yesterday " + yesterday);
                            Log.d(TAG, "onSuccess: today " + today);
                            Log.d(TAG, "onSuccess: eventCreated " + eventCreated);

                            long delay = today.getTime()-eventCreatedDay.getTime();
                            Log.d(TAG, "onSuccess: delay " + delay );

                            if (today.getTime()-eventCreatedDay.getTime()<86400000) {
                                UserHelper.getUser(userId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        User user = documentSnapshot.toObject(User.class);
                                        List<Integer> userSP = user.getUserSPList();
                                        Log.d(TAG, "onSuccess: userSP " + userSP);
                                        Log.d(TAG, "onSuccess: eventIndex " + eventIndex);
                                        if (userSP.contains(eventIndex)) {
                                            displayEvent = true;
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
    }

    public void getTomorrowEvents() {
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
                    }
                });
    }
    //---------------------------------------------------------------------------
    // NOTIFICATION DESIGN
    //---------------------------------------------------------------------------

    private String createTextNotificationEvent(boolean displayEv, boolean displayTom) {
        Log.d(TAG, "createTextNotificationEvent: displayEv " + displayEv);
        Log.d(TAG, "createTextNotificationEvent: displayTom " + displayTom);
        String textToDisplay = "";
        if (displayTom) {
            textToDisplay += getResources().getString(R.string.dont_forget)+ "\n";
        }

        if (displayEv) {
            textToDisplay += getResources().getString(R.string.new_event_for_you);
        }

        Log.d(TAG, "createTextNotificationEvent: " + textToDisplay);
        return textToDisplay;
    }

    private void sendVisualNotification(String message1) {

            Log.d(TAG, "sendVisualNotification: message " + message1);

            Intent openAppIntent = new Intent(this, WelcomeActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, openAppIntent, 0);

            //  Create a Style for the Notification
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            inboxStyle.setBigContentTitle(getString(R.string.notification_hero_title));

            //  Create a Channel (Android 8)
            String channelId = getString(R.string.default_notification_channel_id);

            // Create the icon
            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.vignetteheros);

            //  Build a Notification object
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, channelId)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setLargeIcon(icon)
                            .setContentTitle(getString(R.string.app_name))
                            .setContentText(message1)
                            .setContentTitle(getString(R.string.notification_event_title))
                            .setAutoCancel(true)
                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(message1))
                            .setContentIntent(contentIntent);

            //  Add the Notification to the Notification Manager and show it.
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

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
