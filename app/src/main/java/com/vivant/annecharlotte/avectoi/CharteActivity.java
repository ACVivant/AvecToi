package com.vivant.annecharlotte.avectoi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vivant.annecharlotte.avectoi.Adapters.CharteAdapter;
import com.vivant.annecharlotte.avectoi.Adapters.CharteModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for charte - launched only at the first use before creation of profil
 */
public class CharteActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private CharteAdapter adapter;
    private List<CharteModel> models;
    private Integer[] colors = null;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private Button okBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charte);

        okBtn = (Button) findViewById(R.id.charte_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCreateUserActivity();
            }
        });

        models = new ArrayList<>();
        models.add(new CharteModel(R.drawable.logo, getResources().getString(R.string.welcome_introduction), getResources().getString(R.string.charte1_title)));
        models.add(new CharteModel(R.drawable.superpouvoirs, getResources().getString(R.string.charte2_text), getResources().getString(R.string.charte2_title)));
        models.add(new CharteModel(R.drawable.engagement, getResources().getString(R.string.charte3_text), getResources().getString(R.string.charte3_title)));

        adapter = new CharteAdapter(models, this);

        viewPager = (ViewPager) findViewById(R.id.charte_viewpager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130,0,130,0);

        Integer[] colors_temp = {getResources().getColor(R.color.colorPrimary),getResources().getColor(R.color.colorSecondary),getResources().getColor(R.color.colorAccent)};

        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < (adapter.getCount()-1) && position < (colors.length -1)) {
                    viewPager.setBackgroundColor((Integer) argbEvaluator.evaluate(positionOffset, colors[position], colors[position+1]));
                } else {
                    viewPager.setBackgroundColor(colors[colors.length-1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void startCreateUserActivity() {
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
    }
}
