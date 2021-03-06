package com.vivant.annecharlotte.avectoi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.vivant.annecharlotte.avectoi.Fragments.UserbookFragment;

/**
 * List of users activity
 */
public class UserbookActivity extends AppCompatActivity {

    final UserbookFragment fragmentUserbook = new UserbookFragment();
    final FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userbook);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        fm.beginTransaction().add(R.id.fragment_userbook_RV, fragmentUserbook, "10").commit();
    }
}
