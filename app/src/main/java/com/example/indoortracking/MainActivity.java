package com.example.indoortracking;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    ToggleButton enableButton;
    Button uploadButton;
    boolean scanEnable = false;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Bottom Nav View
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_mapping, R.id.navigation_testing)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        //Drawer Nav View
        AppBarConfiguration appBarConfiguration2 = new AppBarConfiguration.Builder(
         navController.getGraph()).setOpenableLayout(drawerLayout)
         .build();

        NavController navController2 = Navigation.findNavController(this, R.id.nav_host_fragment2);
        NavigationView navView2 = findViewById(R.id.nav_view2);
        NavigationUI.setupWithNavController(navView, navController);

        //Enable Scanning Button
        enableButton = findViewById(R.id.toggleButton);
        //enableButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            //public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //if (isChecked) {
                    // The toggle is enabled
                    //scanEnable = true;
                //} else {
                    // The toggle is disabled
                    //scanEnable = false;
                //}
            //}
        //});

        //Upload Button
        uploadButton = findViewById(R.id.uploadButton);
        //uploadButton.setOnClickListener(new View.OnClickListener() {
           // @Override
            //public void onClick(View v) {
                //upload pic with coordinates
          //  }
       // });


    }

}