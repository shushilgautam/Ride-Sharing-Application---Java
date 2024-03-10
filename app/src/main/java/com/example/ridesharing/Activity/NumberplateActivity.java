package com.example.ridesharing.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ridesharing.Activity.VehicleInfoActivity;
import com.example.ridesharing.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NumberplateActivity extends AppCompatActivity {

    private TextInputEditText editText;
    private DatabaseReference databaseReference;

    private FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.numberplate);

        editText = findViewById(R.id.first_numberplate);
        Button btnDone = findViewById(R.id.btndone);

        // Reference to "Numberplate" node under "users"
      //  databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child("Numberplate");
       // databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child("driversinfo");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users/driversinfo").child(user.getUid()).child("NumberPlate");

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNumberPlateToFirebase();
            }
        });
    }

    private void saveNumberPlateToFirebase() {
        String numberPlate = editText.getText().toString().trim();

        if (!numberPlate.isEmpty()) {
            // Generate a unique key for the number plate
           // String numberPlateId = databaseReference.push().getKey();

            // Save the number plate to Firebase Database under the unique key
            //if (numberPlateId != null) {
                databaseReference.child("NumberPlate").setValue(numberPlate)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(NumberplateActivity.this, "Number plate saved successfully", Toast.LENGTH_SHORT).show();
                            editText.setText(""); // Clear the EditText
                            navigateBackToVehicleInfoActivity(); // Navigate back to VehicleInfoActivity
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(NumberplateActivity.this, "Failed to save number plate", Toast.LENGTH_SHORT).show();
                        });

        } else {
            Toast.makeText(NumberplateActivity.this, "Please enter a number plate", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateBackToVehicleInfoActivity() {
        // Create an intent to go back to VehicleInfoActivity
        Intent intent = new Intent(NumberplateActivity.this, VehicleInfoActivity.class);
        // Start the activity
        startActivity(intent);
        // Finish this activity to prevent the user from navigating back to it using the back button
        finish();
    }
}