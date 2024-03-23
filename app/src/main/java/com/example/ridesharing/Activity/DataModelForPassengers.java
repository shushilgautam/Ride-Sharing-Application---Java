package com.example.ridesharing.Activity;

public class DataModelForPassengers {
    private String fullname, current_location, final_destination, passenger_uid;

    public DataModelForPassengers(String fullname, String from, String to, String uid) {
        this.fullname = fullname;
        this.current_location = from;
        this.final_destination = to;
        this.passenger_uid = uid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

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

    public String getPassenger_uid() {
        return passenger_uid;
    }

    public void setPassenger_uid(String passenger_uid) {
        this.passenger_uid = passenger_uid;
    }

    public  DataModelForPassengers(){}

}
