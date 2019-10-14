package com.vivant.annecharlotte.avectoi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vivant.annecharlotte.avectoi.Adapters.EventAdapter;
import com.vivant.annecharlotte.avectoi.Fragments.MainAllFragment;
import com.vivant.annecharlotte.avectoi.firestore.BaseActivity;
import com.vivant.annecharlotte.avectoi.firestore.UserHelper;
import com.vivant.annecharlotte.avectoi.notifications.AlertReceiver;

import java.util.Calendar;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "MainActivity";
    public static final String EVENT_ID = "EventId";
    public static final String QUERY_ID = "QueryId";
    public static final String FROM_ID = "FromId";
    private static final int DELETE_USER_TASK = 20;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView  navigationView;

    private ImageView userPhoto;
    private TextView userName;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference eventsRef = db.collection("events");

    private EventAdapter adapter;

    final MainAllFragment fragmentAll = new MainAllFragment();
    final MainAllFragment fragmentHerosToFind = new MainAllFragment();
    private boolean switchFragmentToAll=true;
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragmentHerosToFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       FloatingActionButton floatingActionButtonCreate = findViewById(R.id.add_new_event_button);
        floatingActionButtonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCreate();
            }
        });

        FloatingActionButton floatingActionButtonFilter = findViewById(R.id.all_events_button);
        floatingActionButtonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick floatingFilter");
                if (switchFragmentToAll) {
                    fm.beginTransaction().replace(R.id.fragment_events_RV, fragmentAll, "2").commit();
                    switchFragmentToAll=false;
                    Log.d(TAG, "onClick: fragmentAll");

                } else {
                    fm.beginTransaction().replace(R.id.fragment_events_RV, fragmentHerosToFind, "1").commit();
                    switchFragmentToAll=true;
                    Log.d(TAG, "onClick: fragmentHerosToFind");
                }

            }
        });

        this.configureDrawerLayout();
        this.configureNavigationView();
        this.configureDrawerToolbar();
        this.layoutLinks();
        updateUIWhenCreating();

        startAlarm();

        fm.beginTransaction().replace(R.id.fragment_events_RV, fragmentAll, "2").commit();
        switchFragmentToAll=false;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_main_all;
    }

// ---------------------
    // CONFIGURATION
    // ---------------------

    // Configure toolbar
    private void configureDrawerToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger);
    }

    // Configure Drawer Layout
    private void configureDrawerLayout(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // Configure NavigationView
    private void configureNavigationView(){
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.drawer_profil:
                Intent intent = new Intent(MainActivity.this, CreateUserActivity.class);
                startActivity(intent);
                return true;
            case R.id.drawer_ineedhelp:
                Intent intent2 = new Intent(MainActivity.this, UserEventsActivity.class);
                intent2.putExtra(FROM_ID,UserEventsActivity.NEED_INDEX);
                startActivity(intent2);
                return true;
            case R.id.drawer_iamhero:
                Intent intent3 = new Intent(MainActivity.this, UserEventsActivity.class);
                intent3.putExtra(FROM_ID,UserEventsActivity.HERO_INDEX);
                startActivity(intent3);
                return true;
            case R.id.drawer_quit:
                deleteUSer();
                return true;
            case R.id.drawer_allusers:
                openUserbook();
                return true;
            case R.id.drawer_invitation:
                inviteUser();
                return true;
        }
        return false;
    }

    //---------------------------------------------------------------
    // Delete User Account
    //---------------------------------------------------------------
    private void deleteUSer() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.delete_account_message)
                .setPositiveButton(R.string.delete_account_validation, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteUserFromFirebase();
                    }
                })
                .setNegativeButton(R.string.delete_account_reset, null)
                .show();
    }

    private void deleteUserFromFirebase(){
        if (this.getCurrentUser() != null) {
            UserHelper.updateUsername(getResources().getString(R.string.deleted_account),getCurrentUser().getUid() );
            UserHelper.updateTel("0000000000", getCurrentUser().getUid());
            UserHelper.updateEmail(getResources().getString(R.string.deleted_account), getCurrentUser().getUid());
            UserHelper.updatePhoto(EventDetailActivity.NO_PHOTO, getCurrentUser().getUid());
            UserHelper.updateAccount(false, getCurrentUser().getUid());

            AuthUI.getInstance()
                    .delete(this)
                    .addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted(DELETE_USER_TASK));
        }
    }

    private OnSuccessListener<Void> updateUIAfterRESTRequestsCompleted(final int origin){
        return new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                finish();
                }
        };
    }

    //----------------------------
    // UPDATE UI
    //---------------------------
    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    protected void layoutLinks() {
        userName = navigationView.getHeaderView(0).findViewById(R.id.header_name);
        userPhoto = navigationView.getHeaderView(0).findViewById(R.id.header_photo);
    }

    private void updateUIWhenCreating(){

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            //Get picture URL from Firebase
            if (FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl() != null) {
                Glide.with(this)
                        .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .into(userPhoto);
            }

            //Get username from Firebase
            String username = TextUtils.isEmpty(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()) ? getString(R.string.info_no_username_found) :FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

            //Update views with data
            this.userName.setText(username);
        }
    }
    // ------------------------
    // CREATE NEW EVENT
    // ------------------------

    public void launchCreate() {
        Intent intent = new Intent(this, CreateEventActivity.class);
        startActivity(intent);
    }

    //--------------------------
    // Invite User
    //--------------------------

    public void inviteUser() {
        Intent intent = new Intent(this, InviteActivity.class);
        startActivity(intent);
    }

    //--------------------------
    // Userbook
    //--------------------------

    public void openUserbook() {
        Intent intent = new Intent(this, UserbookActivity.class);
        startActivity(intent);
    }

    //--------------------------
    // Notifications
    //--------------------------

    public void startAlarm() {
        Log.d(TAG, "startAlarm ");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 46);
        calendar.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        // send notification each day
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),1000 * 60 * 60 * 24 ,pendingIntent);
    }
}

