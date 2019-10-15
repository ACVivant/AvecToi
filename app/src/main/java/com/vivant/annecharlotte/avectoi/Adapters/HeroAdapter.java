package com.vivant.annecharlotte.avectoi.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vivant.annecharlotte.avectoi.EventDetailActivity;
import com.vivant.annecharlotte.avectoi.HeroDetailActivity;
import com.vivant.annecharlotte.avectoi.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Adapter for recyclerview with list of heros (resumed items) - EventDetail Activity, HeroDetail Activity
 */
public class HeroAdapter extends RecyclerView.Adapter<HeroAdapter.ViewHolder>{

    private static final String TAG = "HeroAdapter";
    public static final String HERO_ID = "HeroDetailId";

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mHerosId = new ArrayList<>();
    private Context mContext;

    public HeroAdapter(Context context, ArrayList<String> names, ArrayList<String> images, ArrayList<String> herosId) {
        mNames = names;
        mImages = images;
        mHerosId = herosId;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_rv_item, parent, false);
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
                Intent intent = new Intent(mContext, HeroDetailActivity.class);
                intent.putExtra(HERO_ID, mHerosId.get(position));
                mContext.startActivity(intent);
                Toast.makeText(mContext, mNames.get(position), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNames.size();
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
