package com.vivant.annecharlotte.avectoi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.vivant.annecharlotte.avectoi.firestore.SosEvent;
import com.vivant.annecharlotte.avectoi.firestore.SosEventHelper;

import java.util.Date;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";
    public static final String EVENT_ID = "EventId";
    public static final String QUERY_ID = "QueryId";
    private Toolbar toolbar;

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

       // fm.beginTransaction().add(R.id.events_fragment_recycler_view, fragmentToFind, "1").commit();
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

    // ------------------------
    // CREATE NEW EVENT
    // ------------------------

    public void launchCreate() {
        Intent intent = new Intent(this, CreateEventActivity.class);
        startActivity(intent);
    }

  /*  // ------------------------
    // RECYCLERVIEW
    // ------------------------

    private void setupRecyclerView() {
        //Query query = eventsRef.orderBy("dateNeed", Query.Direction.ASCENDING);
        //Query query = eventsRef.whereGreaterThan("numberHeroNotFound",0);
        Query query = SosEventHelper.getAllEventsFromToday().whereEqualTo("missionOK", false);

        FirestoreRecyclerOptions<SosEvent> options = new FirestoreRecyclerOptions.Builder<SosEvent>()
                .setQuery(query, SosEvent.class)
                .build();

        adapter = new EventAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.events_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String id = documentSnapshot.getId();
                //Toast.makeText(MainActivity.this, "doc id " + id, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, EventDetailActivity.class);
                Log.d(TAG, "onItemClick: eventId " +id);
                intent.putExtra(EVENT_ID, id);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
*/
}
