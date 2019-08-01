package com.vivant.annecharlotte.avectoi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.vivant.annecharlotte.avectoi.firestore.SosEvent;
import com.vivant.annecharlotte.avectoi.firestore.SosEventHelper;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "MainActivity";
    public static final String EVENT_ID = "EventId";
    public static final String QUERY_ID = "QueryId";
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView  navigationView;

    private ImageView userPhoto;
    private TextView userName;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference eventsRef = db.collection("events");

    private EventAdapter adapter;

    final MainAllFragment fragmentAll = new MainAllFragment();
    final MainAllFragment fragmentToFind = new MainAllFragment();

    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragmentToFind;

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

            }
        });


        this.configureDrawerLayout();
        this.configureNavigationView();
        this.configureDrawerToolbar();
        this.layoutLinks();
        updateUIWhenCreating();

        fm.beginTransaction().add(R.id.fragment_events_RV, fragmentToFind, "1").commit();
    }

// ---------------------
    // CONFIGURATION
    // ---------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: ");
        //Inflate the menu and add it to the top Toolbar
            getMenuInflater().inflate(R.menu.main_activity_menu, menu);

        return true;
    }

    // On va pouvoir supprimer ce menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: ");
        switch (item.getItemId()) {

            case R.id.main_my_events:
                Intent intent = new Intent(MainActivity.this, UserEventsActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }

    // Configure toolbar
    private void configureDrawerToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.drawer_profil:
                Intent intent = new Intent(MainActivity.this, CreateUserActivity.class);
                startActivity(intent);
                return true;
            case R.id.drawer_myevents:
                Intent intent2 = new Intent(MainActivity.this, UserEventsActivity.class);
                startActivity(intent2);
                return true;
            case R.id.drawer_quit:
                return true;
            case R.id.drawer_allusers:
                return true;
            case R.id.drawer_invitation:
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //----------------------------
    // UPDATE UI
    //---------------------------

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
}
