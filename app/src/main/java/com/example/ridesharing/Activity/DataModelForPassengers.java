package com.example.ridesharing.Activity;

public class DataModelForPassengers {
    private String fullname,from,to,uid;

    public DataModelForPassengers(String fullname, String from, String to, String uid) {
        this.fullname = fullname;
        this.from = from;
        this.to = to;
        this.uid = uid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public  DataModelForPassengers(){}

}
