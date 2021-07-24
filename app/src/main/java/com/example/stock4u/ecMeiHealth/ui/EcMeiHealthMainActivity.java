package com.example.stock4u.ecMeiHealth.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.stock4u.R;
import com.example.stock4u.databinding.EcMeiHealthMainActivityBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
public class EcMeiHealthMainActivity extends AppCompatActivity {
    private EcMeiHealthMainActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = EcMeiHealthMainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view2);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.total_investment_calculation_fragment,
                R.id.profitability_fragment,
                R.id.floating_capital_fragment,
                R.id.check_list_activity,
                R.id.cash_register_flow_activity).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView2, navController);
    }

}
