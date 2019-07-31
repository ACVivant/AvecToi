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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

 //   private OnFragmentInteractionListener mListener;

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
        /*Caused by: android.view.InflateException: Binary XML file line #2: RecyclerView has no LayoutManager androidx.recyclerview.widget.RecyclerView{dc8e489 VFED..... ......I. 0,0-0,0 #7f0900cb app:id/events_fragment_recycler_view}, adapter:null, layout:null, context:com.vivant.annecharlotte.avectoi.MainActivity@dd3b03
     Caused by: java.lang.IllegalStateException: RecyclerView has no LayoutManager androidx.recyclerview.widget.RecyclerView{dc8e489 VFED..... ......I. 0,0-0,0 #7f0900cb app:id/events_fragment_recycler_view}, adapter:null, layout:null, context:com.vivant.annecharlotte.avectoi.MainActivity@dd3b03
        at androidx.recyclerview.widget.RecyclerView.generateLayoutParams(RecyclerView.java:4304)
        at android.view.LayoutInflater.inflate(LayoutInflater.java:502)
        at android.view.LayoutInflater.inflate(LayoutInflater.java:423)
        at com.vivant.annecharlotte.avectoi.MainAllFragment.onCreateView(MainAllFragment.java:60)*/

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

        //Query query = eventsRef.orderBy("dateNeed", Query.Direction.ASCENDING);
        //Query query = eventsRef.whereGreaterThan("numberHeroNotFound",0);
        Log.d(TAG, "setupRecyclerView");

        Query query = SosEventHelper.getAllEventsFromToday().whereEqualTo("missionOK", false);

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


   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
           }

    @Override
    public void onDetach() {
        super.onDetach();
    }
*/
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
/*    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
