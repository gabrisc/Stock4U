package com.example.stock4u.main.ui.providers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.stock4u.R;

public class ProvidersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.providers_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ProvidersFragment.newInstance())
                    .commitNow();
        }
    }



}