package com.vivant.annecharlotte.avectoi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.vivant.annecharlotte.avectoi.firestore.SosEvent;
import com.vivant.annecharlotte.avectoi.firestore.SosEventHelper;


public class MainAllFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "MainAllFragment";
    public static final String EVENT_ID = "EventId";

    private EventAdapter adapter;
    private View view;
    private RecyclerView recyclerView;
    private Context context;
    private String whichIndex;

    public MainAllFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();
        view = inflater.inflate(R.layout.fragment_main_all, container, false);
        whichIndex = getTag();
        Log.d(TAG, "onCreateView: index " + whichIndex);
        recyclerView = view.findViewById(R.id.events_fragment_recycler_view);

        setupRecyclerView();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    // ------------------------
    // RECYCLERVIEW
    // ------------------------

    public void setupRecyclerView() {

        Log.d(TAG, "setupRecyclerView");
        Query query;
        if (whichIndex.equals("1")) {
            query = SosEventHelper.getAllEventsFromToday().whereEqualTo("missionOK", false);
        } else {
            query = SosEventHelper.getAllEventsFromToday();
        }



        FirestoreRecyclerOptions<SosEvent> options = new FirestoreRecyclerOptions.Builder<SosEvent>()
                .setQuery(query, SosEvent.class)
                .build();

        adapter = new EventAdapter(options);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String id = documentSnapshot.getId();
                //Toast.makeText(MainActivity.this, "doc id " + id, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), EventDetailActivity.class);
                Log.d(TAG, "onItemClick: eventId " +id);
                intent.putExtra(EVENT_ID, id);
                intent.putExtra(MainActivity.FROM_ID, UserEventsActivity.ALL_INDEX);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
