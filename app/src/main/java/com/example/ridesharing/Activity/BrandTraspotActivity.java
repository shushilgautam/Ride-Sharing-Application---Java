package com.example.ridesharing.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ridesharing.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BrandTraspotActivity extends AppCompatActivity {

    TextInputEditText brandEditText;

    TextInputEditText mileageEditText;
    TextInputEditText mileageEditText1;
    Button doneButton;
    DatabaseReference brandReference;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brandtransport);

        brandEditText = findViewById(R.id.first_brand);
        mileageEditText = findViewById(R.id.first_miles);
        mileageEditText1 = findViewById(R.id.first_usermiles);
        doneButton = findViewById(R.id.btndone);

        // brandReference  = FirebaseDatabase.getInstance().getReference("users/brands");  before
        brandReference = FirebaseDatabase.getInstance().getReference("users/driversinfo").child(user.getUid()).child("VehicleInfo");

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadBrandToFirebase();
            }
        });
    }

    private void uploadBrandToFirebase() {
        String brandName = brandEditText.getText().toString().trim();
        String mileage = mileageEditText.getText().toString().trim();
        String userMileage = mileageEditText1.getText().toString().trim();

        if (!brandName.isEmpty() && !mileage.isEmpty() && !userMileage.isEmpty()) {


            brandReference.child("BrandName").setValue(brandName);
            brandReference.child("CompanyClaimMileage").setValue(mileage);
            brandReference.child("UserClaimMileage").setValue(userMileage)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(BrandTraspotActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                        brandEditText.setText(""); // Clear the EditText
                        mileageEditText.setText(""); // Clear the EditText
                        mileageEditText1.setText(""); // Clear the EditText
                        // Navigate back to VehicleInfoActivity
                        navigateBackToVehicleInfoActivity();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(BrandTraspotActivity.this, "Failed to save data", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(BrandTraspotActivity.this, "Please enter brand name, mileage, and user mileage", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateBackToVehicleInfoActivity() {
        // Finish this activity and return to VehicleInfoActivity
        finish();
    }
}