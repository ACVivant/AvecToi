package com.vivant.annecharlotte.avectoi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CharteActivity extends AppCompatActivity {

    ViewPager viewPager;
    CharteAdapter adapter;
    List<CharteModel> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    Button okBtn;

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
        models.add(new CharteModel(R.drawable.logo, "logo", "texte 1"));
        models.add(new CharteModel(R.drawable.vignetteheros, "titre2", "texte 2"));
        models.add(new CharteModel(R.drawable.heros2vertical, "titre3", "texte 3"));

        adapter = new CharteAdapter(models, this);

        viewPager = (ViewPager) findViewById(R.id.charte_viewpager);
        //viewPager.findViewById(R.id.charte_viewpager);
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
