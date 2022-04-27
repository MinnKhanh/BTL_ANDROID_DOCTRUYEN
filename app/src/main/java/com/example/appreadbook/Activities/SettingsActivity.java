package com.example.appreadbook.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.appreadbook.Listener.bahomlistener;
import com.example.appreadbook.Model.Chap;
import com.example.appreadbook.R;

public class SettingsActivity extends AppCompatActivity implements bahomlistener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        if (findViewById(R.id.idFrameLayout) != null) {
            if (savedInstanceState != null) {
                return;
            }
            // below line is to inflate our fragment.
            getFragmentManager().beginTransaction().add(R.id.idFrameLayout, new SettingsFragment(this)).commit();
        }
    }

    @Override
    public void OnClikchap() {
        Intent i=new Intent(SettingsActivity.this,Home.class);
        startActivity(i);
    }
}