package com.example.indoortracking;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.indoortracking.ui.floorplan.FloorPlanFragment;
import com.example.indoortracking.ui.mapping.MappingFragment;
import com.example.indoortracking.ui.testing.TestingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
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
        NavController navController2 = Navigation.findNavController(this, R.id.nav_host_fragment2);
        AppBarConfiguration appBarConfiguration2 = new AppBarConfiguration.Builder(
                navController2.getGraph()).setOpenableLayout(drawerLayout)
                .build();
        NavigationView navView2 = findViewById(R.id.nav_view2);
        NavigationUI.setupWithNavController(navView2, navController2);
        NavigationUI.setupActionBarWithNavController(this,navController2,appBarConfiguration2);
        navView2.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                Class fragmentClass;
                switch(item.getItemId()){
                    case R.id.navigation_mapping:
                        fragmentClass = MappingFragment.class;
                        break;
                    case R.id.navigation_testing:
                        fragmentClass = TestingFragment.class;
                        break;
                    case R.id.navigation_floor_plans:
                        fragmentClass = FloorPlanFragment.class;
                        break;
                    default:
                        fragmentClass = MappingFragment.class;
                }
                try{
                    fragment = (Fragment) fragmentClass.newInstance();
                }catch(Exception e){
                    e.printStackTrace();
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment2,fragment).commit();
                item.setChecked(true);
                setTitle(item.getTitle());
                drawerLayout.closeDrawers();
                return true;
            }
        });

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