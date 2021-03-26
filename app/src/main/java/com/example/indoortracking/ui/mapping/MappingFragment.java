package com.example.indoortracking.ui.mapping;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.indoortracking.FloorplanScanner;
import com.example.indoortracking.MyApp;
import com.example.indoortracking.R;
import com.example.indoortracking.SharedViewModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.List;

import static android.content.ContentValues.TAG;

public class MappingFragment extends Fragment {

    private MappingViewModel mappingViewModel;
    private WifiManager wifiManager;
    private BroadcastReceiver wifiReceiver;
    Button scanButton, uploadButton;
    ImageView floorPlanImage;
    FloorplanScanner floorplanScanner;
    List<ScanResult> results;
    public SharedViewModel sharedViewModel;


    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mappingViewModel =
                new ViewModelProvider(this).get(MappingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mapping, container, false);
        final TextView scanText = root.findViewById(R.id.scan_text);
        scanText.setMovementMethod(new ScrollingMovementMethod());
        mappingViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                scanText.setText(s);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        floorplanScanner = new FloorplanScanner();

        //Setup WifiScan
        wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                results = wifiManager.getScanResults();
                requireActivity().unregisterReceiver(this);

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

        //Enable Scanning Button
        scanButton = root.findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanWifi();
            }
        });
        //Upload Button
        uploadButton = root.findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(new View.OnClickListener() {
         @Override
        public void onClick(View v) {
        //upload pic with coordinates
             Toast.makeText(getActivity().getApplicationContext(), "Uploading...", Toast.LENGTH_SHORT).show();
             Request<JSONArray> request = floorplanScanner.sendMapping(getContext(), MyApp.Domain);
             queue.add(request);
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
                    String coordinates = x + " " + y;
                    Toast.makeText(getActivity().getApplicationContext(),coordinates,Toast.LENGTH_SHORT).show();
                    Log.i("APscan Results", mappingViewModel.getText().toString());
                    floorplanScanner.mapPoint(x,y,results);
                }
                return false;
            }
        });

        sharedViewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel.class);
        Observer<String> nameObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Picasso.with(getActivity().getApplicationContext()).load(s).into(floorPlanImage);
            }
        };
        sharedViewModel.getNameData().observe(getViewLifecycleOwner(), nameObserver);



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