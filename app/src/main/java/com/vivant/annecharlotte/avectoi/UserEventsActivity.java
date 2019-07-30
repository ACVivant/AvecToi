package com.vivant.annecharlotte.avectoi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.vivant.annecharlotte.avectoi.firestore.SosEvent;

public class UserEventsActivity extends BaseActivity{

    private static final String TAG = "UserEventsActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference eventsRef = db.collection("events");

    private EventAdapter adapter;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_events);

        userId = this.getCurrentUser().getUid();

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close); // Pour mettre une petite croix à la place de la petite flèche en haut à gauche

        FloatingActionButton floatingActionButton = findViewById(R.id.modify_profil);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchUserProfil();
            }
        });

        setupIamheroRecyclerView();
        setupIneedhelpRecyclerView();
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_user_events;
    }

    public void launchUserProfil() {
        Intent intent = new Intent(UserEventsActivity.this ,CreateUserActivity.class);
        startActivity(intent);
    }

    public void setupIamheroRecyclerView() {

    }

    public void setupIneedhelpRecyclerView() {
        Log.d(TAG, "setupIneedhelpRecyclerView: userId ");
        Query query = eventsRef.whereEqualTo("userAskId", userId).orderBy("dateNeed", Query.Direction.ASCENDING);
        //Query query = eventsRef.orderBy("dateNeed", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<SosEvent> options = new FirestoreRecyclerOptions.Builder<SosEvent>()
                .setQuery(query, SosEvent.class)
                .build();

        adapter = new EventAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.ineedhelp_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String id = documentSnapshot.getId();
                Intent intent = new Intent(UserEventsActivity.this, EventDetailActivity.class);
                Log.d(TAG, "onItemClick: eventId " +id);
                intent.putExtra(MainActivity.EVENT_ID, id);
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
}
