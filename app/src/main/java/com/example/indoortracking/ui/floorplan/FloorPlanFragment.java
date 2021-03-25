package com.example.indoortracking.ui.floorplan;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.indoortracking.APIRequests;
import com.example.indoortracking.MyApp;
import com.example.indoortracking.R;

import org.json.JSONArray;

import java.util.HashMap;

import static android.content.ContentValues.TAG;
/*import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;*/

public class FloorPlanFragment extends Fragment{
    private RecyclerView recyclerView;
    FloorPlanAdapter adapter;
    HashMap<String, String> floorPlans = new HashMap<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.floor_plan_fragment,container, false);
        recyclerView = view.findViewById(R.id.floorplan_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager((view.getContext())));

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        Request<JSONArray> request = APIRequests.getAllPlans(view.getContext(), MyApp.Domain);
        queue.add(request);

        HashMap<String,String> temp = APIRequests.getHashMap();
        if(!temp.isEmpty()){
            adapter = new FloorPlanAdapter(view.getContext(), temp, requireActivity());

            Log.d(TAG, temp.toString());
            adapter.setup();

            recyclerView.setAdapter(adapter);
        }

        return view;
    }
}