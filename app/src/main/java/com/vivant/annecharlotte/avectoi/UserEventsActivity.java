package com.vivant.annecharlotte.avectoi;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.vivant.annecharlotte.avectoi.Adapters.EventSmallAdapter;
import com.vivant.annecharlotte.avectoi.firestore.BaseActivity;
import com.vivant.annecharlotte.avectoi.firestore.SosEvent;
import com.vivant.annecharlotte.avectoi.firestore.SosEventHelper;
import com.vivant.annecharlotte.avectoi.firestore.User;
import com.vivant.annecharlotte.avectoi.firestore.UserHelper;

/**
 * List of user events
 */
public class UserEventsActivity extends BaseActivity {

    private static final String TAG = "UserEventsActivity";
    public static final String HERO_INDEX = "iamheroIndex";
    public static final String NEED_INDEX = "inededhelpIndex";
    public static final String ALL_INDEX = "alleventsIndex";

    private EventSmallAdapter adapter;
    private String userId;
    private String fromHeroOrNeed;
    private User currentUser;

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_events);

        fromHeroOrNeed = getIntent().getStringExtra(MainActivity.FROM_ID);
        userId = this.getCurrentUser().getUid();
        getCurrentUserFromFirestore();
        title = findViewById(R.id.iamhero_title);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setupRecyclerView();
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_user_events;
    }

    private void getCurrentUserFromFirestore(){
        UserHelper.getUser(getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                currentUser = documentSnapshot.toObject(User.class);
            }
        });
    }

    public void setupRecyclerView() {
        // We have to adjust view on origin of click (need help or offer help)
        Query query;
        String INDEX;
        if (fromHeroOrNeed.equals(NEED_INDEX)) {
            title.setText(R.string.ineedhelp_title);
            title.setBackground(getResources().getDrawable(R.color.colorSecondary));
            query = SosEventHelper.getAllEventsFromToday().whereEqualTo("userAskId", userId);
            INDEX  = NEED_INDEX;
        } else {
            title.setText(R.string.iamhero_title);
            title.setBackground(getResources().getDrawable(R.color.colorPrimary));
            query = SosEventHelper.getAllEventsFromToday().whereArrayContains("userHeroIdList", userId);
            INDEX = HERO_INDEX;
        }

        FirestoreRecyclerOptions<SosEvent> options = new FirestoreRecyclerOptions.Builder<SosEvent>()
                .setQuery(query, SosEvent.class)
                .build();

        adapter = new EventSmallAdapter(options, INDEX);

        RecyclerView recyclerView = findViewById(R.id.iamhero_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new EventSmallAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String id = documentSnapshot.getId();
                Intent intent = new Intent(UserEventsActivity.this, EventDetailActivity.class);
                intent.putExtra(MainActivity.EVENT_ID, id);  // Which event we want to show
                intent.putExtra(MainActivity.FROM_ID, fromHeroOrNeed);  // origin of click
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
