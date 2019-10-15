package com.vivant.annecharlotte.avectoi.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.vivant.annecharlotte.avectoi.Adapters.HeroAdapter;
import com.vivant.annecharlotte.avectoi.Adapters.UserbookAdapter;
import com.vivant.annecharlotte.avectoi.HeroDetailActivity;
import com.vivant.annecharlotte.avectoi.R;
import com.vivant.annecharlotte.avectoi.firestore.User;
import com.vivant.annecharlotte.avectoi.firestore.UserHelper;

public class UserbookFragment extends Fragment {
    private static final String TAG = "UserbookFragment";

    private UserbookAdapter adapter;
    private View view;
    private RecyclerView recyclerView;
    private Context context;

    /**
     * Fragment for Userbook Activity
     */
    public UserbookFragment() {
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
        // We need only active accounts
        Query query;
        query = UserHelper.getAllUsers().whereEqualTo("activeAccount", true);

        // iplementation of recyclerview
        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query, User.class)
                .build();

        adapter = new UserbookAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        // launch HeroDetailActivity on click on item
        adapter.setOnItemClickListener(new UserbookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String id = documentSnapshot.getId();
                Intent intent = new Intent(getContext(), HeroDetailActivity.class);
                intent.putExtra(HeroAdapter.HERO_ID, id);
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
