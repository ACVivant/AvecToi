package com.vivant.annecharlotte.avectoi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vivant.annecharlotte.avectoi.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

/**
 * Adapter for recyclerview of the Charte Activity
 */
public class CharteAdapter extends PagerAdapter {

    private List<CharteModel> models;
    private LayoutInflater layoutInflater;
    private Context context;

    public CharteAdapter(List<CharteModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_charte, container, false);

        ImageView imageView;
        TextView title, text;

        imageView = view.findViewById(R.id.item_charte_image);
        title = view.findViewById(R.id.item_charte_title);
        text = view.findViewById(R.id.item_charte_text);

        imageView.setImageResource(models.get(position).getImage());
        title.setText(models.get(position).getDesc());
        text.setText(models.get(position).getTitle());

        container.addView(view,0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
