package com.example.indoortracking.ui.mapping;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.indoortracking.MainActivity;
import com.example.indoortracking.R;

import java.util.List;

import static android.content.ContentValues.TAG;

public class MappingFragment extends Fragment {

    private MappingViewModel mappingViewModel;
    private WifiManager wifiManager;
    private BroadcastReceiver wifiReceiver;
    Button scanButton, uploadButton;
    ImageView floorPlanImage;

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mappingViewModel =
                new ViewModelProvider(this).get(MappingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mapping, container, false);
        final TextView scanText = root.findViewById(R.id.scan_text);
        mappingViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                scanText.setText(s);
            }
        });

        //Enable Scanning Button
        scanButton = root.findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanWifi();
            }
        });

        //Setup WifiScan
        //scanText = root.findViewById(R.id.scan_text);
        //scanText.setText("hello");
        wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                List<ScanResult> results = wifiManager.getScanResults();
                requireActivity().unregisterReceiver(wifiReceiver);

                String display = "";
                for (ScanResult sr: results) {
                    display = display.concat("SSID: "+sr.SSID+", RSSI: "+sr.level+"\n");
                    Log.i(TAG, "SSID: "+sr.SSID+", Level: "+sr.level);
                }
                mappingViewModel.setmText(display);
            }
        };

        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(getActivity().getApplicationContext(), "WiFi needs to be enabled", Toast.LENGTH_SHORT).show();
            wifiManager.setWifiEnabled(true);
        }

        //Upload Button
        uploadButton = root.findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(new View.OnClickListener() {
         @Override
        public void onClick(View v) {
        //upload pic with coordinates
             Toast.makeText(getActivity().getApplicationContext(), "Uploading...", Toast.LENGTH_SHORT).show();
          }
         });

        //ImageView
        floorPlanImage = root.findViewById(R.id.image_mapping);
        floorPlanImage.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    String coordinates = Integer.toString(x) + " " + Integer.toString(y);
                    Toast.makeText(getActivity().getApplicationContext(),coordinates,Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        return root;
    }
    private void scanWifi() {
        requireActivity().registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        boolean success = wifiManager.startScan();
        if (!success){ scanFailed(); }
        Toast.makeText(getActivity().getApplicationContext(), "Scanning...", Toast.LENGTH_SHORT).show();
    }

    private void scanFailed(){
        Toast.makeText(getActivity().getApplicationContext(), "Scan failed", Toast.LENGTH_SHORT).show();
    }
}