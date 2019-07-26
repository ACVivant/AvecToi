package com.vivant.annecharlotte.avectoi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EventDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
    }
}
