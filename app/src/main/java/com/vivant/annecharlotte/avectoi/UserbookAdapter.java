package com.vivant.annecharlotte.avectoi;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.vivant.annecharlotte.avectoi.Utils.DateFormat;
import com.vivant.annecharlotte.avectoi.firestore.SosEvent;
import com.vivant.annecharlotte.avectoi.firestore.User;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Anne-Charlotte Vivant on 25/09/2019.
 */
public class UserbookAdapter extends FirestoreRecyclerAdapter<User, UserbookAdapter.UserbookViewHolder> {

    private static final String TAG = "UserbookAdapter";
    //Variables
    private Context mContext;

    private OnItemClickListener listener;

    public UserbookAdapter(@NonNull FirestoreRecyclerOptions<User> options) {
        super(options);
        Log.d(TAG, "UserbookAdapter called");
    }

    @NonNull
    @Override
    public UserbookAdapter.UserbookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_userbook, parent, false);
        mContext = parent.getContext();
        return new UserbookAdapter.UserbookViewHolder(v);
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull UserbookAdapter.UserbookViewHolder userViewHolder, int i, @NonNull User user) {

        Log.d(TAG, "onBindViewHolder: ");


        // Name
        String myName = user.getUserName();
        userViewHolder.nameRV.setText(myName);

        // Image
        if (user.getUrlPicture() != null) {
            Glide.with(mContext)
                    .load(user.getUrlPicture())
                    .apply(RequestOptions.circleCropTransform())
                    .into(userViewHolder.imageRV);
        }
    }

    class UserbookViewHolder extends RecyclerView.ViewHolder {
        TextView nameRV;
        ImageView imageRV;

        public UserbookViewHolder(@NonNull View itemView) {
            super(itemView);

            nameRV = itemView.findViewById(R.id.userbook_rv_name);
            imageRV = itemView.findViewById(R.id.userbook_rv_image);

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
