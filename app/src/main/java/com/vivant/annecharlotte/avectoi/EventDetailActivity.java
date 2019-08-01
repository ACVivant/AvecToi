package com.vivant.annecharlotte.avectoi;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.vivant.annecharlotte.avectoi.Utils.DateFormat;
import com.vivant.annecharlotte.avectoi.firestore.SosEvent;
import com.vivant.annecharlotte.avectoi.firestore.SosEventHelper;
import com.vivant.annecharlotte.avectoi.firestore.User;
import com.vivant.annecharlotte.avectoi.firestore.UserHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class EventDetailActivity extends BaseActivity {

    private static final String TAG = "EventDetailActivity";

    private String eventId;

    private Button eventBtn;
    private TextView eventDateTV;
    private TextView eventTownTV;
    private TextView eventDescriptionTV;
    private TextView eventNumberAskedTV;
    private TextView eventNumberWaitedTV;
    private TextView eventCarTV;

    private ImageView userPhotoIV;
    private TextView userNameTV;
    private TextView userTelTV;
    private TextView userEmailTV;
    private TextView eventDateCreatedTV;

    private FloatingActionButton validate;

    //private List<String> listHeros = new ArrayList<>();
    private List<User> listHeros = new ArrayList<>();
    //private String userHeroId;
    private User userHero;
    private int toFindHeros;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        eventId = getIntent().getStringExtra(MainActivity.EVENT_ID);

        UserHelper.getUser(getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userHero = documentSnapshot.toObject(User.class);
            }
        });

        Log.d(TAG, "onCreate: eventId " + eventId);

        layoutlinks();
        updateView(eventId);

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listHeros.add(userHero);
                openDialog(listHeros);

            }
        });
    }

    private void openDialog(final List<User> list) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.detail_event_confirmation_title)
                .setMessage(R.string.detail_event_hero_confirmation)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateEvent(list);
                        finish();
                    }
                })
                .setNegativeButton("Non", null)
                .show();
    }

    public void updateEvent(List<User> list) {
        List<String> listId = new ArrayList<>();   // Pour pouvoir gérer les query (en particulier dans les tableaux, ca ne marche pas avec des formats comme User d'où une copie des id pour avoir un tableau sur lequel on peut filtrer
        for (User item : list) {
            listId.add(item.getUid());
        }
        SosEventHelper.updateUserHerosIdList(listId, eventId);
        SosEventHelper.updateUserHerosList(list, eventId);

        Date today = new Date();
        SosEventHelper.updateDateHeroOk(today, eventId);
        SosEventHelper.updateUserHerosNotFound(toFindHeros - 1, eventId);


        if (toFindHeros - 1 == 0) {
            SosEventHelper.updateMissionOk(true, eventId);
        }
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_event_detail;
    }

    public void layoutlinks() {
        eventBtn = findViewById(R.id.detail_event_button);
        eventDateTV = findViewById(R.id.detail_event_date);
        eventTownTV = findViewById(R.id.detail_event_town);
        eventDescriptionTV = findViewById(R.id.detail_event_description);
        eventNumberAskedTV = findViewById(R.id.detail_event_number_asked);
        eventNumberWaitedTV = findViewById(R.id.detail_event_number_notfound);
        eventCarTV = findViewById(R.id.detail_event_car);
        eventDateCreatedTV = findViewById(R.id.detail_event_created);

        userPhotoIV = findViewById(R.id.detail_event_user_photo);
        userNameTV = findViewById(R.id.detail_event_userName);
        userTelTV = findViewById(R.id.detail_event_userTel);
        userEmailTV = findViewById(R.id.detail_event_userEmail);

        validate = findViewById(R.id.add_new_hero_button);
    }


    public void updateView(String eid) {
        SosEventHelper.getEvent(eid).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    User userAsk = Objects.requireNonNull(documentSnapshot.toObject(SosEvent.class)).getUserAsk();
                    userNameTV.setText(userAsk.getUserName());
                    userTelTV.setText(userAsk.getUserPhone());
                    userEmailTV.setText(userAsk.getUserEmail());

                    if (userAsk.getUrlPicture() != null) {
                        Glide.with(EventDetailActivity.this)
                                .load(userAsk.getUrlPicture())
                                .apply(RequestOptions.circleCropTransform())
                                .into(userPhotoIV);
                    }


                    //DATE
                    Date eventDate = Objects.requireNonNull(documentSnapshot.toObject(SosEvent.class)).getDateNeed();
                    DateFormat myFormat = new DateFormat();
                    final String myDate = myFormat.getRegisteredDate(eventDate);
                    eventDateTV.setText(myDate);

                    Date eventDateCreated = Objects.requireNonNull(documentSnapshot.toObject(SosEvent.class)).getDateCreated();
                    final String myDateCreated = myFormat.getRegisteredDate(eventDateCreated);
                    eventDateCreatedTV.setText(myDateCreated);

                    //TOWN
                    eventTownTV.setText(Objects.requireNonNull(documentSnapshot.toObject(SosEvent.class)).getTown());

                    //DESCRIPTION
                    eventDescriptionTV.setText(Objects.requireNonNull(documentSnapshot.toObject(SosEvent.class)).getDescription());

                    //HEROS
                    int totalHeros = Objects.requireNonNull(documentSnapshot.toObject(SosEvent.class)).getNumberHeroWanted();
                    eventNumberAskedTV.setText(String.valueOf(totalHeros));

                    toFindHeros = Objects.requireNonNull(documentSnapshot.toObject(SosEvent.class)).getNumberHeroNotFound();
                    eventNumberWaitedTV.setText(String.valueOf(toFindHeros));

                    listHeros = Objects.requireNonNull(documentSnapshot.toObject(SosEvent.class)).getUserHeroList();

                    //CAR
                    boolean car = Objects.requireNonNull(documentSnapshot.toObject(SosEvent.class)).getCar();
                    if (car) {
                        eventCarTV.setText(R.string.detail_event_car_yes);
                    } else {
                        eventCarTV.setText(R.string.detail_event_car_no);
                    }

                    //THEME
                    String[] themeArray = getResources().getStringArray(R.array.event_theme);
                    int themeIndex = Objects.requireNonNull(documentSnapshot.toObject(SosEvent.class)).getThemeIndex();
                    Log.d(TAG, "onSuccess: themeIndex " + themeIndex);
                    switch (themeIndex) {
                        case 0:
                            eventBtn.setText(themeArray[0]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_ironing), null, null);
                            return;
                        case 1:
                            eventBtn.setText(themeArray[1]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_household), null, null);
                            return;
                        case 2:
                            eventBtn.setText(themeArray[2]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_shopping), null, null);
                            return;
                        case 3:
                            eventBtn.setText(themeArray[3]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_cooking), null, null);
                            return;
                        case 4:
                            eventBtn.setText(themeArray[4]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_driving), null, null);
                            return;
                        case 5:
                            eventBtn.setText(themeArray[5]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_gardening), null, null);
                            return;
                        case 6:
                            eventBtn.setText(themeArray[6]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_diy), null, null);
                            return;
                        case 7:
                            eventBtn.setText(themeArray[7]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_works), null, null);
                            return;
                        case 8:
                            eventBtn.setText(themeArray[8]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_relocation), null, null);
                            return;
                        case 9:
                            eventBtn.setText(themeArray[9]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_reading), null, null);
                            return;
                        case 10:
                            eventBtn.setText(themeArray[10]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_babysitting), null, null);
                            return;
                        case 11:
                            eventBtn.setText(themeArray[11]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_sewing), null, null);
                            return;
                        case 12:
                            eventBtn.setText(themeArray[12]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_flower), null, null);
                            return;
                        case 13:
                            eventBtn.setText(themeArray[13]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_tutoring), null, null);
                            return;
                        case 14:
                            eventBtn.setText(themeArray[14]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_compagny), null, null);
                            return;
                        case 15:
                            eventBtn.setText(themeArray[15]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_admin), null, null);
                            return;
                        default:
                            eventBtn.setText(getResources().getString(R.string.app_name));
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_add), null, null);
                    }
                }
            }
        });
    }
}