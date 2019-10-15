package com.vivant.annecharlotte.avectoi.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.vivant.annecharlotte.avectoi.R;
import com.vivant.annecharlotte.avectoi.UserEventsActivity;
import com.vivant.annecharlotte.avectoi.Utils.DateFormat;
import com.vivant.annecharlotte.avectoi.firestore.SosEvent;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Adapter of recyclerview of activity with  resumed events list (UserEventsActivity)
 */
public class EventSmallAdapter extends FirestoreRecyclerAdapter<SosEvent, EventSmallAdapter.EventSmallViewHolder> {

    private OnItemClickListener listener;
    private static final String TAG = "EventSmallAdapter";
    private String from;

    public EventSmallAdapter(@NonNull FirestoreRecyclerOptions<SosEvent> options, String from) {
        super(options);
        this.from = from;
    }

    @Override
    protected void onBindViewHolder(@NonNull EventSmallViewHolder eventViewHolder, int i, @NonNull SosEvent sosEvent) {

        // Determination of which events we have to show (events created by user or where he accepted to help)
        if (from== UserEventsActivity.NEED_INDEX) {
            eventViewHolder.eventCV.setCardBackgroundColor(eventViewHolder.itemView.getContext().getResources().getColor(R.color.colorSecondaryVeryTransparent));
            eventViewHolder.themeRV.setBackgroundColor(eventViewHolder.itemView.getContext().getResources().getColor(R.color.colorSecondary));
        } else {
            eventViewHolder.eventCV.setCardBackgroundColor(eventViewHolder.itemView.getContext().getResources().getColor(R.color.colorPrimaryVeryTransparent));
            eventViewHolder.themeRV.setBackgroundColor(eventViewHolder.itemView.getContext().getResources().getColor(R.color.colorPrimary));
        }

        //Date
        DateFormat myFormat = new DateFormat();
        final String myDate = myFormat.getRegisteredDate(sosEvent.getDateNeed());
        eventViewHolder.dateRV.setText(myDate);

        // Town
        eventViewHolder.townRV.setText(sosEvent.getTown());

        // Name or Theme
        final TextView nameOrThemePlace = eventViewHolder.nameRV;

        if (from==UserEventsActivity.HERO_INDEX) {

            nameOrThemePlace.setText(sosEvent.getUserAsk().getUserName());

        } else {
            String themeArr[] = eventViewHolder.itemView.getContext().getResources().getStringArray(R.array.event_theme);
            int themeIndex = sosEvent.getThemeIndex();
            nameOrThemePlace.setText(themeArr[themeIndex]);
        }

        //Theme icon
        int themeIndex = sosEvent.getThemeIndex();

        switch (themeIndex) {
            case 0: eventViewHolder.themeRV.setImageResource(R.drawable.ic_topic_0);
                return;
            case 1: eventViewHolder.themeRV.setImageResource(R.drawable.ic_topic_1);
                return;
            case 2: eventViewHolder.themeRV.setImageResource(R.drawable.ic_topic_2);
                return;
            case 3: eventViewHolder.themeRV.setImageResource(R.drawable.ic_topic_3);
                return;
            case 4: eventViewHolder.themeRV.setImageResource(R.drawable.ic_topic_4);
                return;
            case 5: eventViewHolder.themeRV.setImageResource(R.drawable.ic_topic_5);
                return;
            case 6: eventViewHolder.themeRV.setImageResource(R.drawable.ic_topic_6);
                return;
            case 7: eventViewHolder.themeRV.setImageResource(R.drawable.ic_topic_7);
                return;
            case 8: eventViewHolder.themeRV.setImageResource(R.drawable.ic_topic_8);
                return;
            case 9: eventViewHolder.themeRV.setImageResource(R.drawable.ic_topic_9);
                return;
            case 10: eventViewHolder.themeRV.setImageResource(R.drawable.ic_topic_10);
                return;
            case 11: eventViewHolder.themeRV.setImageResource(R.drawable.ic_topic_11);
                return;
            case 12: eventViewHolder.themeRV.setImageResource(R.drawable.ic_topic_12);
                return;
            case 13: eventViewHolder.themeRV.setImageResource(R.drawable.ic_topic_13);
                return;
            case 14: eventViewHolder.themeRV.setImageResource(R.drawable.ic_topic_14);
                return;
            case 15: eventViewHolder.themeRV.setImageResource(R.drawable.ic_topic_15);
                return;
            default:eventViewHolder.themeRV.setImageResource(R.drawable.ic_add);
        }
    }

    @NonNull
    @Override
    public EventSmallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_small_item, parent, false);
        return new EventSmallViewHolder(v);
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    //-----------------------------------------------------------------
    // View Holder
    //-----------------------------------------------------------------

    class EventSmallViewHolder extends RecyclerView.ViewHolder {

        ImageButton themeRV;
        TextView dateRV;
        TextView nameRV;
        TextView townRV;
        CardView eventCV;

        public EventSmallViewHolder(@NonNull View itemView) {
            super(itemView);

            themeRV = itemView.findViewById(R.id.RV_event_small_theme);
            dateRV = itemView.findViewById(R.id.RV_event_small_date_needed);
            nameRV = itemView.findViewById(R.id.RV_event_small_name);
            townRV = itemView.findViewById(R.id.RV_event_small_town);
            eventCV = itemView.findViewById(R.id.RV_event_small_cardview);

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
