package com.example.ridesharing.Activity;

public class DriverClass {
    String driverid,vehicleNumber,vehicleName,vehiclePhoto,liscenseNumber,liscensePhoto;

    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehiclePhoto() {
        return vehiclePhoto;
    }

    public void setVehiclePhoto(String vehiclePhoto) {
        this.vehiclePhoto = vehiclePhoto;
    }

    public String getLiscenseNumber() {
        return liscenseNumber;
    }

    public void setLiscenseNumber(String liscenseNumber) {
        this.liscenseNumber = liscenseNumber;
    }

    public String getLiscensePhoto() {
        return liscensePhoto;
    }

    public void setLiscensePhoto(String liscensePhoto) {
        this.liscensePhoto = liscensePhoto;
    }

    public DriverClass(String driverid, String vehicleNumber, String vehicleName, String vehiclePhoto, String liscenseNumber, String liscensePhoto) {
        this.driverid = driverid;
        this.vehicleNumber = vehicleNumber;
        this.vehicleName = vehicleName;
        this.vehiclePhoto = vehiclePhoto;
        this.liscenseNumber = liscenseNumber;
        this.liscensePhoto = liscensePhoto;
    }
}
