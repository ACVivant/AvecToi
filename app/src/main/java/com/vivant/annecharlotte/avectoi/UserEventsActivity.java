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

    private EventAdapter adapterHero;
    private EventAdapter adapterNeed;
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
        Log.d(TAG, "setupIamHeroRecyclerView: userId " + userId);
        Query queryHero = eventsRef.whereArrayContains("userHeroIdList", userId);//.orderBy("dateNeed", Query.Direction.ASCENDING);

        //2EMdkAnaT0fc6V9k481bIYMxCv63
        FirestoreRecyclerOptions<SosEvent> optionsHero = new FirestoreRecyclerOptions.Builder<SosEvent>()
                .setQuery(queryHero, SosEvent.class)
                .build();

        adapterHero = new EventAdapter(optionsHero);

        RecyclerView recyclerView = findViewById(R.id.iamhero_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterHero);

        adapterHero.setOnItemClickListener(new EventAdapter.OnItemClickListener() {
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

    public void setupIneedhelpRecyclerView() {
        Log.d(TAG, "setupIneedhelpRecyclerView: userId " +userId);
        Query queryNeed = eventsRef.whereEqualTo("userAskId", userId).orderBy("dateNeed", Query.Direction.ASCENDING);
        //Query query = eventsRef.orderBy("dateNeed", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<SosEvent> optionsNeed = new FirestoreRecyclerOptions.Builder<SosEvent>()
                .setQuery(queryNeed, SosEvent.class)
                .build();

        adapterNeed = new EventAdapter(optionsNeed);

        RecyclerView recyclerView = findViewById(R.id.ineedhelp_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterNeed);

        adapterNeed.setOnItemClickListener(new EventAdapter.OnItemClickListener() {
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
        adapterNeed.startListening();
        adapterHero.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterNeed.stopListening();
        adapterHero.stopListening();
    }
}
