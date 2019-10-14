package com.vivant.annecharlotte.avectoi;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.vivant.annecharlotte.avectoi.Adapters.UpdateAdapter;
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

public class UpdateEventActivity extends BaseActivity implements UpdateAdapter.DeleteHeroListener{
    private static final String TAG = "UpdateEventActivity";
    public static final String NO_PHOTO = "noPhoto";

    private String eventId;
    private String userId;

    private Button eventBtn;
    private TextView eventDateTV, eventTownTV, eventCarTV, eventHerosWaitedTV;
    private EditText eventDescription, eventNumberWaited;
    private int totalHeros;
    private FloatingActionButton validate;
    private FloatingActionButton delete;

    private List<User> listHeros = new ArrayList<>();
    private List<String> listHerosId = new ArrayList<>();
    private User userHero;
    private SosEvent thisEvent;
    private int toFindHeros;

    private String descriptionToSave;
    private int newNumberHeros;
    private int alreadyNumberHeros;
    private String heroToDeleteId;
    private List<String> herosToDeleteList;
    private Date dateToday;

    // RecyclerView
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mHeroId = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_event);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        eventId = getIntent().getStringExtra(MainActivity.EVENT_ID);

        SosEventHelper.getEvent(eventId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                thisEvent = documentSnapshot.toObject(SosEvent.class);
            }
        });

        layoutlinks();
        updateView(eventId);
        initRecyclerView();

    }

    public void layoutlinks() {
        eventBtn = findViewById(R.id.update_event_button);
        eventDateTV = findViewById(R.id.update_event_date);
        eventTownTV = findViewById(R.id.update_event_town);
        eventDescription = findViewById(R.id.update_event_description);
        eventHerosWaitedTV = findViewById(R.id.update_event_number_hero_total);
        eventCarTV = findViewById(R.id.update_event_car_possible);
        eventNumberWaited = findViewById(R.id.update_event_number_total);
        recyclerView = findViewById(R.id.update_event_user_heros_rv);


        validate = findViewById(R.id.update_event_ok_button);
        delete = findViewById(R.id.update_event_delete_button);

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

    }

    public void updateView(String eid) {

        SosEventHelper.getEvent(eid).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    //DATE
                    Date eventDate = Objects.requireNonNull(documentSnapshot.toObject(SosEvent.class)).getDateNeed();
                    DateFormat myFormat = new DateFormat();
                    final String myDate = myFormat.getRegisteredDate(eventDate);
                    eventDateTV.setText(myDate);

                    //TOWN
                    eventTownTV.setText(Objects.requireNonNull(documentSnapshot.toObject(SosEvent.class)).getTown());

                    //DESCRIPTION
                    eventDescription.setText(Objects.requireNonNull(documentSnapshot.toObject(SosEvent.class)).getDescription());

                    //HEROS
                    listHeros = Objects.requireNonNull(documentSnapshot.toObject(SosEvent.class)).getUserHeroList();
                    listHerosId = Objects.requireNonNull(documentSnapshot.toObject(SosEvent.class)).getUserHeroIdList();
                    alreadyNumberHeros = listHerosId.size();

                    totalHeros = Objects.requireNonNull(documentSnapshot.toObject(SosEvent.class)).getNumberHeroWanted();
                    eventNumberWaited.setText(String.valueOf(totalHeros));

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

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        UpdateAdapter adapter = new UpdateAdapter(this, mNames, mImages, mHeroId);
        recyclerView.setAdapter(adapter);

    }

    //---------------------------------------------------------
    // Enregistrement des modifications
    //---------------------------------------------------------

    public void saveData() {
        descriptionToSave = eventDescription.getText().toString();
        newNumberHeros = Integer.parseInt(eventNumberWaited.getText().toString());
        userId = this.getCurrentUser().getUid();
        dateToday = new Date();
        toFindHeros = newNumberHeros-alreadyNumberHeros;
        updateEvent();
    }

    public void updateEvent() {
        Log.d(TAG, "createEvent");
        Log.d(TAG, "updateEvent: description " + descriptionToSave);
        Log.d(TAG, "updateEvent: newNumberHeros " + newNumberHeros);
        Log.d(TAG, "updateEvent:newToken toFindHeros " + toFindHeros);
        Log.d(TAG, "updateEvent: dateToday " + dateToday);

        SosEventHelper.updateEventDescription(descriptionToSave, eventId);
        SosEventHelper.updateEventNumberHeroWanted(newNumberHeros, eventId);
        SosEventHelper.updateEventNumberHeroStillToFind(toFindHeros, eventId);
        SosEventHelper.updateEventDateCreated(dateToday, eventId);

        if (newNumberHeros>alreadyNumberHeros) {
            Log.d(TAG, "updateEvent: newNumberHeros " + newNumberHeros);
            SosEventHelper.updateMissionOk(false, eventId);
        }

        Intent intent = new Intent(UpdateEventActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_update_event;
    }

    @Override
    public void heroToDelete(String userId, final int position) {
        Log.d(TAG, "heroToDelete: on passe par le listener " + userId);
        // Les héros sont stockés deux fois : par leurs id et par User
        heroToDeleteId = userId;
        listHerosId.remove(heroToDeleteId);
        SosEventHelper.updateUserHerosIdList(listHerosId, eventId);

        UserHelper.getUser(heroToDeleteId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User userToDelete = Objects.requireNonNull(documentSnapshot.toObject(User.class));
               // listHeros.remove(userToDelete); // Cette ligne n'a aucun effet
                Log.d(TAG, "onSuccess: position " + position);
                listHeros.remove(position);
                SosEventHelper.updateUserHerosList(listHeros, eventId);

                String tokenToDelete = userToDelete.getUserToken();
                Log.d(TAG, "onSuccess:newToken  tokenToDelete " + tokenToDelete );
                SosEventHelper.updateDeletedHeroToken(tokenToDelete, eventId);

                alreadyNumberHeros -=1;

                saveData();
            }
        });

    }
}
