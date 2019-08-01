package com.vivant.annecharlotte.avectoi;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Anne-Charlotte Vivant on 25/07/2019.
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

        Log.d(TAG, "onBindViewHolder: ");

        if (from==UserEventsActivity.NEED_INDEX) {
            eventViewHolder.eventCV.setCardBackgroundColor(eventViewHolder.itemView.getContext().getResources().getColor(R.color.colorSecondaryVeryTransparent));
            eventViewHolder.themeRV.setBackgroundColor(eventViewHolder.itemView.getContext().getResources().getColor(R.color.colorSecondary));
        } else {
            eventViewHolder.eventCV.setCardBackgroundColor(eventViewHolder.itemView.getContext().getResources().getColor(R.color.colorPrimaryVeryTransparent));
            eventViewHolder.themeRV.setBackgroundColor(eventViewHolder.itemView.getContext().getResources().getColor(R.color.colorPrimary));
        }

        //Date
        DateFormat myFormat = new DateFormat();
        final String myDate = myFormat.getRegisteredDate(sosEvent.getDateNeed());
        Log.d(TAG, "onBindViewHolder: myDate " + myDate);
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
            case 0: eventViewHolder.themeRV.setImageResource(R.drawable.ic_ironing);
                return;
            case 1: eventViewHolder.themeRV.setImageResource(R.drawable.ic_household);
                return;
            case 2: eventViewHolder.themeRV.setImageResource(R.drawable.ic_shopping);
                return;
            case 3: eventViewHolder.themeRV.setImageResource(R.drawable.ic_cooking);
                return;
            case 4: eventViewHolder.themeRV.setImageResource(R.drawable.ic_driving);
                return;
            case 5: eventViewHolder.themeRV.setImageResource(R.drawable.ic_gardening);
                return;
            case 6: eventViewHolder.themeRV.setImageResource(R.drawable.ic_diy);
                return;
            case 7: eventViewHolder.themeRV.setImageResource(R.drawable.ic_works);
                return;
            case 8: eventViewHolder.themeRV.setImageResource(R.drawable.ic_relocation);
                return;
            case 9: eventViewHolder.themeRV.setImageResource(R.drawable.ic_reading);
                return;
            case 10: eventViewHolder.themeRV.setImageResource(R.drawable.ic_babysitting);
                return;
            case 11: eventViewHolder.themeRV.setImageResource(R.drawable.ic_sewing);
                return;
            case 12: eventViewHolder.themeRV.setImageResource(R.drawable.ic_flower);
                return;
            case 13: eventViewHolder.themeRV.setImageResource(R.drawable.ic_tutoring);
                return;
            case 14: eventViewHolder.themeRV.setImageResource(R.drawable.ic_compagny);
                return;
            case 15: eventViewHolder.themeRV.setImageResource(R.drawable.ic_admin);
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
