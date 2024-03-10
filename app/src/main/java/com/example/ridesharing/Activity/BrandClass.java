package com.example.ridesharing.Activity;

public class BrandClass {

    private String brandName;

    public BrandClass() {
        // Default constructor required for calls to DataSnapshot.getValue(Brand.class)
    }

    public BrandClass(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}