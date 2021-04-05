package com.example.indoortracking.ui.floorplan;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.indoortracking.R;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.example.indoortracking.SharedViewModel;
import com.example.indoortracking.ui.mapping.MappingFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class FloorPlanAdapter extends RecyclerView.Adapter<FloorPlanAdapter.FloorPlanViewHolder> {
    ArrayList<String> locations;
    ArrayList<String> images;
    HashMap<String, String> floorPlans;
    private final Context context;
    private final FragmentActivity activity;
    private final FloorPlanFragment currentFragment;

    public FloorPlanAdapter(Context context, HashMap<String, String> floorPlans, FragmentActivity activity, FloorPlanFragment currentFragment){
        this.context = context;
        this.floorPlans = floorPlans;
        this.activity = activity;
        this.currentFragment = currentFragment;
    }


    public void setup(){
        Set<String> keySet = floorPlans.keySet();
        locations = new ArrayList<>(keySet);
        Collection<String> values = floorPlans.values();
        images = new ArrayList<>(values);
    }



    @NonNull
    @Override
    public FloorPlanAdapter.FloorPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.floorplan_card,parent,false);
        return new FloorPlanAdapter.FloorPlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FloorPlanViewHolder holder, int position) {

        holder.location.setText(locations.get(position));

        Picasso.with(context).load(images.get(position)).into(holder.floorplan);
        holder.download.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                holder.sharedViewModel.setNameData(images.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    class FloorPlanViewHolder extends RecyclerView.ViewHolder{
        public SharedViewModel sharedViewModel;
        TextView location; ImageView floorplan; Button download;
        public FloorPlanViewHolder(@NonNull View itemView){
            super(itemView);

            location = itemView.findViewById(R.id.location);
            floorplan = itemView.findViewById(R.id.image);
            download = itemView.findViewById(R.id.downloadButton);
            sharedViewModel = ViewModelProviders.of(activity).get(SharedViewModel.class);
        }
    }

}
