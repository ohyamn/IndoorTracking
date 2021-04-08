package com.example.indoortracking.ui.testing;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.indoortracking.APICallback;
import com.example.indoortracking.APIRequests;
import com.example.indoortracking.FloorplanScanner;
import com.example.indoortracking.MyApp;
import com.example.indoortracking.R;
import com.example.indoortracking.SharedViewModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.List;

import static android.content.ContentValues.TAG;

public class TestingFragment extends Fragment {
    Button scanButton, locationButton;
    ImageView floorPlanImage, dotImage;
    private TestingViewModel testingViewModel;
    private WifiManager wifiManager;
    private BroadcastReceiver wifiReceiver;
    public SharedViewModel sharedViewModel;
    FloorplanScanner floorplanScanner;
    List<ScanResult> results;
    APIRequests apiRequests;
    String locationTitle;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        testingViewModel =
                new ViewModelProvider(this).get(TestingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_testing, container, false);
        final TextView textView = root.findViewById(R.id.text_testing);
        testingViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        floorplanScanner = new FloorplanScanner();
        apiRequests = new APIRequests();

        scanButton = root.findViewById(R.id.scanButton2);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanWifi();
            }
        });

        wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                results = wifiManager.getScanResults();
                requireActivity().unregisterReceiver(wifiReceiver);

                String display = "";
                for (ScanResult sr: results) {
                    display = display.concat("SSID: "+sr.SSID+", RSSI: "+sr.level+"\n");
                    Log.i(TAG, "SSID: "+sr.SSID+", Level: "+sr.level);
                }
                testingViewModel.setmText(display);
            }
        };

        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(getActivity().getApplicationContext(), "WiFi needs to be enabled", Toast.LENGTH_SHORT).show();
            wifiManager.setWifiEnabled(true);
        }

        floorPlanImage = root.findViewById(R.id.image_testing);
        sharedViewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel.class);
        Observer<String> nameObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Picasso.with(getActivity().getApplicationContext()).load(s).into(floorPlanImage);
            }
        };
        sharedViewModel.getNameData().observe(getViewLifecycleOwner(), nameObserver);

        dotImage = root.findViewById(R.id.image_dot);

        locationButton = root.findViewById(R.id.locationButton);

        Observer<String> locationObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                locationTitle = s;
            }
        };
        sharedViewModel.getLocation().observe(getViewLifecycleOwner(), locationObserver);

        locationButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                StringRequest currentPlan = apiRequests.sendCurrentPlan(getContext(), MyApp.Domain, locationTitle);
                queue.add(currentPlan);
                Request<JSONArray> request = floorplanScanner.getLocation(getContext(), MyApp.Domain, results, new APICallback() {
                    @Override
                    public void onSuccess(String result) {
                    }

                    @Override
                    public void onSuccess(JSONArray result) {
                        List<String> predictedLocation = floorplanScanner.getPredictedLocation();
                        String str = predictedLocation.get(0).substring(1,predictedLocation.get(0).length()-1);
                        String[] temp = str.split(",");
                        Toast.makeText(getActivity().getApplicationContext(),str,Toast.LENGTH_SHORT).show();
                        dotImage.setVisibility(View.VISIBLE);
                        float x = Float.parseFloat(temp[0]);
                        float y = Float.parseFloat(temp[1]);
                        float newx = x/100 * floorPlanImage.getWidth();
                        float newy = y/100 * floorPlanImage.getHeight();

                        dotImage.setX(newx-5);
                        dotImage.setY(newy+5);

                        predictedLocation.clear();
                    }

                    @Override
                    public void onError(String result) throws Exception {
                    }
                });
                queue.add(request);
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