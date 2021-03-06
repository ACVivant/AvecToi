package com.vivant.annecharlotte.avectoi;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.vivant.annecharlotte.avectoi.Adapters.HeroAdapter;
import com.vivant.annecharlotte.avectoi.Utils.DateFormat;
import com.vivant.annecharlotte.avectoi.firestore.BaseActivity;
import com.vivant.annecharlotte.avectoi.firestore.SosEvent;
import com.vivant.annecharlotte.avectoi.firestore.SosEventHelper;
import com.vivant.annecharlotte.avectoi.firestore.User;
import com.vivant.annecharlotte.avectoi.firestore.UserHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Display event details
 */
public class EventDetailActivity extends BaseActivity {

    private static final String TAG = "EventDetailActivity";
    public static final String NO_PHOTO = "noPhoto";

    private String eventId;
    private String userId;

    private Button eventBtn;
    private TextView eventDateTV;
    private TextView eventTownTV;
    private TextView eventDescriptionTV;
    private TextView eventNumberAlreadyTV;
    private TextView eventNumberWaitedTV;
    private TextView eventCarTV;

    private ImageView userPhotoIV;
    private TextView userNameTV;
    private TextView eventDateCreatedTV;

    private FloatingActionButton validate;
    private ConstraintLayout userInfos;

    private List<User> listHeros = new ArrayList<>();
    private User userHero;
    private SosEvent thisEvent;
    private int toFindHeros;
    private String fromIndex;

    // RecyclerView
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mHeroId = new ArrayList<>();
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        // get index to know which event to show
        eventId = getIntent().getStringExtra(MainActivity.EVENT_ID);
        fromIndex = getIntent().getStringExtra(MainActivity.FROM_ID);

        UserHelper.getUser(getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userHero = documentSnapshot.toObject(User.class);
            }
        });
        SosEventHelper.getEvent(eventId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                thisEvent = documentSnapshot.toObject(SosEvent.class);
            }
        });


        layoutlinks();
        updateView(eventId);

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromIndex.equals(UserEventsActivity.ALL_INDEX)) {
                    listHeros.add(userHero);
                    openDialog(listHeros);
                } else {
                    if (fromIndex.equals(UserEventsActivity.NEED_INDEX)) {
                        Intent intent = new Intent(EventDetailActivity.this, UpdateEventActivity.class);
                        intent.putExtra(MainActivity.EVENT_ID, eventId);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_event_detail;
    }

    //------------------------------
    // Update UI
    //-----------------------------

    public void layoutlinks() {
        eventBtn = findViewById(R.id.detail_event_button);
        eventDateTV = findViewById(R.id.detail_event_date);
        eventTownTV = findViewById(R.id.detail_event_town);
        eventDescriptionTV = findViewById(R.id.detail_event_description);
        eventNumberAlreadyTV = findViewById(R.id.detail_event_number_already_heros);
        eventNumberWaitedTV = findViewById(R.id.detail_event_number_notfound);
        eventCarTV = findViewById(R.id.detail_event_car_possible);
        eventDateCreatedTV = findViewById(R.id.detail_event_created);

        userPhotoIV = findViewById(R.id.detail_event_user_photo);
        userNameTV = findViewById(R.id.detail_event_userName);
        userInfos = findViewById(R.id.detail_event_user_ask_infos);

        recyclerView = findViewById(R.id.event_user_heros_rv);

        validate = findViewById(R.id.add_new_hero_button);

        userInfos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventDetailActivity.this, HeroDetailActivity.class);
                intent.putExtra(HeroAdapter.HERO_ID, userId);
                startActivity(intent);
            }
        });

    }


    public void updateView(String eid) {
        if (fromIndex.equals(UserEventsActivity.NEED_INDEX)) {
            validate.setImageResource(R.drawable.ic_topic_13);
        } else {
            if (fromIndex.equals(UserEventsActivity.ALL_INDEX)) {
                validate.setImageResource(R.drawable.ic_done);
            } else {
                validate.setVisibility(View.GONE);
            }
        }

        SosEventHelper.getEvent(eid).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    User userAsk = Objects.requireNonNull(documentSnapshot.toObject(SosEvent.class)).getUserAsk();
                    userNameTV.setText(userAsk.getUserName());
                    userId = userAsk.getUid();

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
                    listHeros = Objects.requireNonNull(documentSnapshot.toObject(SosEvent.class)).getUserHeroList();
                    eventNumberAlreadyTV.setText(String.valueOf(listHeros.size()));

                    toFindHeros = Objects.requireNonNull(documentSnapshot.toObject(SosEvent.class)).getNumberHeroNotFound();
                    eventNumberWaitedTV.setText(String.valueOf(toFindHeros));

                    for (int i=0; i<listHeros.size();i++) {
                        mNames.add(listHeros.get(i).getUserName());
                        mHeroId.add(listHeros.get(i).getUid());
                        if (listHeros.get(i).getUrlPicture()!=null) {
                            mImages.add(listHeros.get(i).getUrlPicture());
                        } else {
                            mImages.add(NO_PHOTO);
                        }
                    }

                    if(mNames.size()>0) {
                        initRecyclerView();
                    } else {
                        recyclerView.setVisibility(View.GONE);
                    }

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
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_topic_0), null, null);
                            return;
                        case 1:
                            eventBtn.setText(themeArray[1]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_topic_1), null, null);
                            return;
                        case 2:
                            eventBtn.setText(themeArray[2]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_topic_2), null, null);
                            return;
                        case 3:
                            eventBtn.setText(themeArray[3]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_topic_3), null, null);
                            return;
                        case 4:
                            eventBtn.setText(themeArray[4]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_topic_4), null, null);
                            return;
                        case 5:
                            eventBtn.setText(themeArray[5]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_topic_5), null, null);
                            return;
                        case 6:
                            eventBtn.setText(themeArray[6]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_topic_6), null, null);
                            return;
                        case 7:
                            eventBtn.setText(themeArray[7]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_topic_7), null, null);
                            return;
                        case 8:
                            eventBtn.setText(themeArray[8]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_topic_8), null, null);
                            return;
                        case 9:
                            eventBtn.setText(themeArray[9]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_topic_9), null, null);
                            return;
                        case 10:
                            eventBtn.setText(themeArray[10]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_topic_10), null, null);
                            return;
                        case 11:
                            eventBtn.setText(themeArray[11]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_topic_11), null, null);
                            return;
                        case 12:
                            eventBtn.setText(themeArray[12]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_topic_12), null, null);
                            return;
                        case 13:
                            eventBtn.setText(themeArray[13]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_topic_13), null, null);
                            return;
                        case 14:
                            eventBtn.setText(themeArray[14]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_topic_14), null, null);
                            return;
                        case 15:
                            eventBtn.setText(themeArray[15]);
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_topic_15), null, null);
                            return;
                        default:
                            eventBtn.setText(getResources().getString(R.string.app_name));
                            eventBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_add), null, null);
                    }
                }
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        HeroAdapter adapter = new HeroAdapter(this, mNames, mImages, mHeroId);
        recyclerView.setAdapter(adapter);
    }

    //---------------------------------
    // Update data in Firebase
    //---------------------------------

// Update references of user when he accepts mission
    public void updateMyRef(final User userAsk) {
        UserHelper.getUser(userId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    List<User> userRefList = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getEventHeroRefList();
                    userRefList.add(userAsk);
                    UserHelper.updateEventHeroRefList(userRefList, userHero.getUid());
                }
            }
        });
    }

// update event with new user engagement
    public void updateEvent(List<User> list) {
        List<String> listId = new ArrayList<>();   // We create a List of String to use filter in query
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

    //-------------------------------------
    // Dialog to confirm user engagement
    //-------------------------------------

    // Generate dialog window when user offers help
    private void openDialog(final List<User> list) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.detail_event_confirmation_title)
                .setMessage(R.string.detail_event_hero_confirmation)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateEvent(list);
                        UserHelper.getUser(thisEvent.getUserAskId()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    User userAsk = Objects.requireNonNull(documentSnapshot.toObject(User.class));
                                    updateMyRef(userAsk);
                                }
                            }
                        });
                        finish();
                    }
                })
                .setNegativeButton("Non", null)
                .show();
    }
}