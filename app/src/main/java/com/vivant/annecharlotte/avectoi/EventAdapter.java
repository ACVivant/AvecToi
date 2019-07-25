package com.vivant.annecharlotte.avectoi;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.vivant.annecharlotte.avectoi.Utils.DateFormat;
import com.vivant.annecharlotte.avectoi.firestore.SosEvent;
import com.vivant.annecharlotte.avectoi.firestore.User;
import com.vivant.annecharlotte.avectoi.firestore.UserHelper;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Anne-Charlotte Vivant on 25/07/2019.
 */
public class EventAdapter extends FirestoreRecyclerAdapter<SosEvent, EventAdapter.EventViewHolder> {

    private static final String TAG = "EventAdapter";
    private String myName;

    public EventAdapter(@NonNull FirestoreRecyclerOptions<SosEvent> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, int i, @NonNull SosEvent sosEvent) {

        //Date
        DateFormat myFormat = new DateFormat();
        final String myDate = myFormat.getRegisteredDate(sosEvent.getDateNeed());
        eventViewHolder.dateRV.setText(myDate);

        //Theme
        String themeArr[] = eventViewHolder.itemView.getContext().getResources().getStringArray(R.array.event_theme);
        int themeIndex = sosEvent.getThemeIndex();
        eventViewHolder.themeRV.setText(themeArr[themeIndex]);

        // Town
        eventViewHolder.townRV.setText(sosEvent.getTown());

        // Name
        String userAskId = sosEvent.getUserAskId();
        final TextView namePlace = eventViewHolder.nameRV;

        UserHelper.getUser(userAskId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Log.d(TAG, "onSuccess: documentSnapshot exists");
                    myName = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getUserName();
                    namePlace.setText(myName);
                }
            }
        });
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new EventViewHolder(v);
    }

    class EventViewHolder extends RecyclerView.ViewHolder {

        TextView themeRV;
        TextView dateRV;
        TextView nameRV;
        TextView townRV;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            themeRV = itemView.findViewById(R.id.RV_event_theme);
            dateRV = itemView.findViewById(R.id.RV_event_date_needed);
            nameRV = itemView.findViewById(R.id.RV_event_name);
            townRV = itemView.findViewById(R.id.RV_event_town);
        }
    }
}
