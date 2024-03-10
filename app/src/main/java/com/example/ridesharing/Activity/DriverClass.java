package com.example.ridesharing.Activity;

public class DriverClass {
    private String driverId;
    private String licenseNumber;
    private String photo1Url;
    private String photo2Url;


    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getPhoto1Url() {
        return photo1Url;
    }

    public void setPhoto1Url(String photo1Url) {
        this.photo1Url = photo1Url;
    }

    public String getPhoto2Url() {
        return photo2Url;
    }

    public void setPhoto2Url(String photo2Url) {
        this.photo2Url = photo2Url;
    }

    public DriverClass(String licenseNumber, String photo1Url, String photo2Url) {
        this.licenseNumber = licenseNumber;
        this.photo1Url = photo1Url;
        this.photo2Url = photo2Url;
    }
}
