package com.example.stock4u.main;

import android.os.Bundle;

import com.example.stock4u.R;

import com.example.stock4u.databinding.ActivityCrudsMenusBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class CrudsMenusActivity extends AppCompatActivity {

    private ActivityCrudsMenusBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCrudsMenusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.fragment_ec_mei, R.id.fragment_economicOperation, R.id.fragment_seals,R.id.fragment_client).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_cruds_menus);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}