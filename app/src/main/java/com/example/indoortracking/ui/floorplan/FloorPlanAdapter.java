package com.example.indoortracking.ui.floorplan;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.indoortracking.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FloorPlanAdapter extends FirebaseRecyclerAdapter<FloorPlan, FloorPlanAdapter.FloorPlanViewHolder> {
    //ArrayList<String> locations;
    //ArrayList<FloorPlan> floorPlans;
    private Context context;

    public FloorPlanAdapter(@NonNull FirebaseRecyclerOptions<FloorPlan> options, Context context){
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull FloorPlanAdapter.FloorPlanViewHolder holder, int position, @NonNull FloorPlan model) {
        holder.location.setText(model.getLocation());
        String imageUrl = model.getFloorPlan();
        //Picasso.with(context)
        //        .load(imageUrl)
        //        .fit().centerCrop().into(holder.floorplan);

    }

    @NonNull
    @Override
    public FloorPlanAdapter.FloorPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.floorplan_card,parent,false);
        return new FloorPlanAdapter.FloorPlanViewHolder(view);
    }

    class FloorPlanViewHolder extends RecyclerView.ViewHolder{
        TextView location; ImageView floorplan;
        public FloorPlanViewHolder(@NonNull View itemView){
            super(itemView);

            location = itemView.findViewById(R.id.location);
            floorplan = itemView.findViewById(R.id.image);
        }
    }
}
