package com.example.ridesharing.Activity;
public class DriverHelper {
    private String driverId;
    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;
    private String imageUrl; // New field for image URL

    public DriverHelper() {
        // Default constructor required for Firebase
    }

    public DriverHelper(String driverId, String firstName, String lastName, String email, String dateOfBirth, String imageUrl) {
        this.driverId = driverId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.imageUrl = imageUrl;
    }

    public DriverHelper(String driverId, String firstName, String lastName, String email, String dateOfBirth) {

    }

    public String getDriverId() {
        return driverId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
