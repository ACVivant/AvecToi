package com.vivant.annecharlotte.avectoi.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vivant.annecharlotte.avectoi.EventDetailActivity;
import com.vivant.annecharlotte.avectoi.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Adapter for recyclerview with list of voluntaries in UpdateEvent Activity
 */
public class UpdateAdapter extends RecyclerView.Adapter<UpdateAdapter.ViewHolder>{

    private static final String TAG = "UpdateAdapter";

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mHerosId = new ArrayList<>();
    private Context mContext;
    private DeleteHeroListener listener;

    public UpdateAdapter(Context context, ArrayList<String> names, ArrayList<String> images, ArrayList<String> herosId) {
        mNames = names;
        mImages = images;
        mHerosId = herosId;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_rv_item, parent, false);
        try {
            listener = (DeleteHeroListener) mContext;
        } catch (ClassCastException e) {
            throw new ClassCastException(mContext.toString() + "must implement DeletePhotoListener");
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (!mImages.get(position).equals(EventDetailActivity.NO_PHOTO)) {
            Glide.with(mContext)
                    .asBitmap()
                    .load(mImages.get(position))
                    .into(holder.image);
        }

        holder.name.setText(mNames.get(position));

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDeleteDialog(mHerosId.get(position), position);
                Log.d(TAG, "onClick: heroId, position " + mHerosId.get(position) +" , " + position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    private void openDeleteDialog(final String userId, final int pos) {
        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle(R.string.update_hero_delete_title)
                .setMessage(R.string.update_hero_delete_text)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.heroToDelete(userId, pos);
                    }
                })
                .setNegativeButton("Non", null)
                .show();
    }

    public interface DeleteHeroListener{
        void heroToDelete(String userId, int position);
    }
    //-----------------------------------------------------------------------------

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.user_rv_image);
            name = itemView.findViewById(R.id.user_rv_name);
        }
    }
}
