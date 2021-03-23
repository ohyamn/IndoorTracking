package com.example.indoortracking.ui.floorplan;

public class FloorPlan {
    private String location;
    private String floorplan;

    public  FloorPlan(){}

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getFloorPlan(){
        return floorplan;
    }

    public void setFloorPlan(String floorplan){
        this.floorplan = floorplan;
    }
}
