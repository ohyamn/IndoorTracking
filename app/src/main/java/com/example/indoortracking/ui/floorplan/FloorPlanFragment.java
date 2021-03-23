package com.example.indoortracking.ui.floorplan;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.indoortracking.R;
import com.example.indoortracking.ui.testing.TestingViewModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FloorPlanFragment extends Fragment {
    private RecyclerView recyclerView;
    FloorPlanAdapter adapter;
    DatabaseReference db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.floor_plan_fragment,container, false);
        db = FirebaseDatabase.getInstance().getReference();
        recyclerView = view.findViewById(R.id.floorplan_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager((view.getContext())));
        FirebaseRecyclerOptions<FloorPlan> options = new FirebaseRecyclerOptions.Builder<FloorPlan>().setQuery(db, FloorPlan.class).build();
        adapter = new FloorPlanAdapter(options, view.getContext());

        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}