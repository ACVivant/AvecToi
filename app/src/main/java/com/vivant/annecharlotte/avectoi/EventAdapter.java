package com.vivant.annecharlotte.avectoi;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    private OnItemClickListener listener;

    private static final String TAG = "EventAdapter";
    private String myName;

    public EventAdapter(@NonNull FirestoreRecyclerOptions<SosEvent> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, int i, @NonNull SosEvent sosEvent) {

        Log.d(TAG, "onBindViewHolder: ");

        //Date
        DateFormat myFormat = new DateFormat();
        final String myDate = myFormat.getRegisteredDate(sosEvent.getDateNeed());
        eventViewHolder.dateRV.setText(myDate);

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

        //Theme
        String themeArr[] = eventViewHolder.itemView.getContext().getResources().getStringArray(R.array.event_theme);
        int themeIndex = sosEvent.getThemeIndex();
        eventViewHolder.themeRV.setText(themeArr[themeIndex]);
        Drawable top;
        switch (themeIndex) {
            case 0: top = eventViewHolder.itemView.getContext().getResources().getDrawable(R.drawable.ic_household);
                eventViewHolder.themeRV.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
            return;
            case 1: top = eventViewHolder.itemView.getContext().getResources().getDrawable(R.drawable.ic_shopping);
                eventViewHolder.themeRV.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                return;
            case 2: top = eventViewHolder.itemView.getContext().getResources().getDrawable(R.drawable.ic_cooking);
                eventViewHolder.themeRV.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                return;
            case 3: top = eventViewHolder.itemView.getContext().getResources().getDrawable(R.drawable.ic_driving);
                eventViewHolder.themeRV.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                return;
            case 4: top = eventViewHolder.itemView.getContext().getResources().getDrawable(R.drawable.ic_ironing);
                eventViewHolder.themeRV.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                return;
            case 5: top = eventViewHolder.itemView.getContext().getResources().getDrawable(R.drawable.ic_gardening);
                eventViewHolder.themeRV.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                return;
            case 6: top = eventViewHolder.itemView.getContext().getResources().getDrawable(R.drawable.ic_diy);
                eventViewHolder.themeRV.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                return;
            case 7: top = eventViewHolder.itemView.getContext().getResources().getDrawable(R.drawable.ic_works);
                eventViewHolder.themeRV.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                return;
            case 8: top = eventViewHolder.itemView.getContext().getResources().getDrawable(R.drawable.ic_relocation);
                eventViewHolder.themeRV.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                return;
            case 9: top = eventViewHolder.itemView.getContext().getResources().getDrawable(R.drawable.ic_reading);
                eventViewHolder.themeRV.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                return;
            case 10: top = eventViewHolder.itemView.getContext().getResources().getDrawable(R.drawable.ic_compagny);
                eventViewHolder.themeRV.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                return;
            case 11: top = eventViewHolder.itemView.getContext().getResources().getDrawable(R.drawable.ic_babysitting);
                eventViewHolder.themeRV.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                return;
            case 12: top = eventViewHolder.itemView.getContext().getResources().getDrawable(R.drawable.ic_tutoring);
                eventViewHolder.themeRV.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                return;
            case 13: top = eventViewHolder.itemView.getContext().getResources().getDrawable(R.drawable.ic_sewing);
                eventViewHolder.themeRV.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                return;
            case 14: top = eventViewHolder.itemView.getContext().getResources().getDrawable(R.drawable.ic_admin);
                eventViewHolder.themeRV.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                return;
            case 15: top = eventViewHolder.itemView.getContext().getResources().getDrawable(R.drawable.ic_flower);
                eventViewHolder.themeRV.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                return;
            default: top = eventViewHolder.itemView.getContext().getResources().getDrawable(R.drawable.ic_add);
                eventViewHolder.themeRV.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
        }
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new EventViewHolder(v);
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    class EventViewHolder extends RecyclerView.ViewHolder {

        Button themeRV;
        TextView dateRV;
        TextView nameRV;
        TextView townRV;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            themeRV = itemView.findViewById(R.id.RV_event_theme);
            dateRV = itemView.findViewById(R.id.RV_event_date_needed);
            nameRV = itemView.findViewById(R.id.RV_event_name);
            townRV = itemView.findViewById(R.id.RV_event_town);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener !=null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);

                    }
                }
            });
        }
    }
}
