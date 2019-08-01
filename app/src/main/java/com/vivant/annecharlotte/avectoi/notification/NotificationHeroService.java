package com.vivant.annecharlotte.avectoi.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.vivant.annecharlotte.avectoi.R;
import com.vivant.annecharlotte.avectoi.WelcomeActivity;
import com.vivant.annecharlotte.avectoi.firestore.SosEvent;
import com.vivant.annecharlotte.avectoi.firestore.User;
import com.vivant.annecharlotte.avectoi.firestore.UserHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.core.app.NotificationCompat;

public class NotificationHeroService extends FirebaseMessagingService {

    private static final String TAG = "NotificationHeroService";
    private final int NOTIFICATION_ID = 007;
    private final String NOTIFICATION_TAG = "FIREBASESUPERTOI1";
    private String userId;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference  eventsRef =  db.collection("events");

    private String[] stringMissionTab = new String[16];

    private List<Integer> listHeros = new ArrayList<>();
    private List<Integer> listNewEvents = new ArrayList<>();
    String textHero = "";
    String textEvent ="";
    Date yesterday;

    boolean displayHero = false;
    boolean displayEvent = false;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "onMessageReceived");
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



        // On récupère la liste des événements pour lesquels j'ai des nouveaux héros - dont la date d'acceptation est aujourd'hui et l'userAskId moi-même
       List<Integer> herosIndex= getMyHeros();


        try {   // Il faut absolument que je comprenne comment éviter ça... et capter le retour de firebase dans le Main Thread
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        textHero = createTextNotificationHero(herosIndex);



        // On récupère la liste des nouvelles missions mises en ligne et correspondent à nos super pouvoirs

        List<Integer> eventsIndex= getNewEvent();
        try {   // Il faut absolument que je comprenne comment éviter ça... et capter le retour de firebase dans le Main Thread
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        textEvent = createTextNotificationEvent(eventsIndex);
        Log.d(TAG, "onMessageReceived: textEvent " + textEvent);

        // On envoie la notification
        sendVisualNotification(textHero, textEvent);

    }

    //---------------------------------------------------------------
    // GET INFOS
    //---------------------------------------------------------------

    public List<Integer> getMyHeros() {
        Log.d(TAG, "getMyHeros");
        Log.d(TAG, "getMyHeros: yesterday " +yesterday);

        eventsRef.whereEqualTo("userAskId", userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        int size = queryDocumentSnapshots.size();
                        Log.d(TAG, "onSuccess: il y a " + size +" événement(s) que j'ai créés");

                        for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots) {
                            final SosEvent event = documentSnapshot.toObject(SosEvent.class);
                            Date now = new Date();
                             long diffTime = now.getTime() - event.getDateHeroOk().getTime();
                             if (diffTime<86400000) { //24h de différence en ms
                                 listHeros.add(event.getThemeIndex());
                             }
                            }
                        Log.d(TAG, "getMyHeros: listHeros " +listHeros.toString());
                    }
                });

        return listHeros;
    }

    private List<Integer> getNewEvent() {

        /*Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -24);
        yesterday = calendar.getTime();

        eventsRef.whereGreaterThan("dateCreated", yesterday)   // Ca aurait été cool que ça marche...
                .get()*/

                eventsRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        int size = queryDocumentSnapshots.size();
                        Log.d(TAG, "onSuccess: il y a " + size +" nouveaux événements");

                        for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots) {
                            final SosEvent event = documentSnapshot.toObject(SosEvent.class);
                            // On regarde si un événement correspond à un de mes super pouvoirs
                            final int eventIndex = event.getThemeIndex();

                            UserHelper.getUser(userId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    User user = documentSnapshot.toObject(User.class);
                                    List<Integer> userSP= user.getUserSPList();
                                    if (userSP.contains(eventIndex)) {
                                        listNewEvents.add(event.getThemeIndex());
                                    }
                                }
                            });
                            Log.d(TAG, "getNewEvent: listNewEvents" + listNewEvents.toString());
                        }
                    }
                });

        return listNewEvents;
    }

    //---------------------------------------------------------------------------
    // NOTIFICATION DESIGN
    //---------------------------------------------------------------------------

    private String createTextNotificationHero(List<Integer> list) {
        Log.d(TAG, "createTextNotificationHero: list " + list.toString());
        Log.d(TAG, "createTextNotificationHero: list.size " +list.size());
        String textToDisplay = "";
        switch (list.size()) {
            case 0:
                textToDisplay = "RAS";
                displayHero = false;
                break;
            case 1:
                textToDisplay = "Un héros s'est proposé pour votre mission: " + stringMissionTab[list.get(0)];
                displayHero = true;
                break;
           default:
               textToDisplay = "Des héros se sont proposés pour vos missions: ";
               for (int i = 0; i < list.size()-1; i++) {
                   textToDisplay += stringMissionTab[list.get(i)-1] + " / ";
               }
               textToDisplay += stringMissionTab[list.get(list.size()-1)-1];
               displayHero = true;

        }
        return textToDisplay;
    }

    private String createTextNotificationEvent(List<Integer> list) {
        String textToDisplay = "";
        switch (list.size()) {
            case 0:
                displayEvent = false;
                break;
            case 1:
                textToDisplay = "Une nouvelle mission nécessitant vos super pouvoirs a été mise en ligne: " + stringMissionTab[0];
                displayEvent = true;
                break;
            default:
                textToDisplay = "De nouvelles missions nécessitant vos super pouvoirs ont été mises en ligne: ";
                for (int i = 0; i < list.size() - 1; i++) {
                    textToDisplay += stringMissionTab[i] + " / ";
                }
                textToDisplay += stringMissionTab[list.size()];
                displayEvent = true;
        }
        Log.d(TAG, "createTextNotificationHero: textEvent " + textToDisplay);
        return textToDisplay;
    }

    private void sendVisualNotification(String message1, String message2) {

        if (displayHero || displayEvent) {
            String message = "";
            if (displayHero && !displayEvent) {
                message = message1;
            }
            if (!displayHero && displayEvent) {
                message = message2;
            }
            if (displayHero && displayEvent) {
                message = message1 + "  //  " + message2;
            }

            Log.d(TAG, "sendVisualNotification: message " + message);

            Log.d(TAG, "sendVisualNotification");
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
                            .setContentText(message)
                            .setContentTitle(getString(R.string.notification_hero_title))
                            .setAutoCancel(true)
                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(message))
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

}
