package com.example.indoortracking.ui.floorplan;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.indoortracking.R;
import com.example.indoortracking.ui.testing.TestingViewModel;

public class FloorPlanFragment extends Fragment {
    private FloorPlanViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(FloorPlanViewModel.class);
        View root = inflater.inflate(R.layout.floor_plan_fragment, container, false);
        final TextView textView = root.findViewById(R.id.textView);
        mViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}