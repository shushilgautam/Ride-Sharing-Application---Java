package com.example.ridesharing.Activity;

public class DataModelForPassengers {
    private String fullname, current_location, final_destination, passenger_uid,status;

    public DataModelForPassengers(String fullname, String current_location, String final_destination, String passenger_uid, String status) {
        this.fullname = fullname;
        this.current_location = current_location;
        this.final_destination = final_destination;
        this.passenger_uid = passenger_uid;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public  DataModelForPassengers(){}

}
