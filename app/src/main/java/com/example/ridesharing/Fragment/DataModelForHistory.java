package com.example.ridesharing.Fragment;

public class DataModelForHistory {
    private String current_location;
    private String final_destination;
    private String time;
    private String date;
    private String ride_status;

    public String getCurrent_location() {
        return current_location;
    }

    public void setCurrent_location(String current_location) {
        this.current_location = current_location;
    }

    public String getFinal_destination() {
        return final_destination;
    }

    public void setFinal_destination(String final_destination) {
        this.final_destination = final_destination;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRide_status() {
        return ride_status;
    }

    public void setRide_status(String ride_status) {
        this.ride_status = ride_status;
    }

    public DataModelForHistory(String current_location, String final_destination, String time, String date, String ride_status) {
        this.current_location = current_location;
        this.final_destination = final_destination;
        this.time = time;
        this.date = date;
        this.ride_status = ride_status;
    }
    public  DataModelForHistory(){
        //empty constructor
    }
}
