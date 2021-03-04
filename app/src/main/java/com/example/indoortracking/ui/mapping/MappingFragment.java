package com.example.indoortracking.ui.mapping;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.indoortracking.MainActivity;
import com.example.indoortracking.R;

import java.util.List;

import static android.content.ContentValues.TAG;

public class MappingFragment extends Fragment {

    private MappingViewModel mappingViewModel;
    TextView scanText;
    private WifiManager wifiManager;
    private BroadcastReceiver wifiReceiver;
    ToggleButton enableButton;
    Button uploadButton;
    public static boolean scanEnable;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mappingViewModel =
                new ViewModelProvider(this).get(MappingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mapping, container, false);
        final TextView textView = root.findViewById(R.id.text_mapping);
        /*mappingViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        //Setup WifiScan
        scanText = root.findViewById(R.id.scan_text);
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
                scanText.setText(display);
            }
        };

        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(getActivity().getApplicationContext(), "WiFi needs to be enabled", Toast.LENGTH_SHORT).show();
            wifiManager.setWifiEnabled(true);
        }
        if(MainActivity.scanEnable){
            scanWifi();
        }

        //Enable Scanning Button
        enableButton = root.findViewById(R.id.toggleButton);
        enableButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // The toggle is enabled
                scanEnable = true;
                scanWifi();
            } else {
                // The toggle is disabled
                scanEnable = false;
            }
        });

        //Upload Button
        uploadButton = root.findViewById(R.id.uploadButton);
        //uploadButton.setOnClickListener(new View.OnClickListener() {
        // @Override
        //public void onClick(View v) {
        //upload pic with coordinates
        //  }
        // });

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