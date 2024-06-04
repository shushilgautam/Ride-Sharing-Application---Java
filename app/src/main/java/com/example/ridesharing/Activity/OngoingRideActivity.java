package com.example.ridesharing.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ridesharing.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OngoingRideActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongoing_ride);
        ArrayList<DataModelForPassengers> data=new ArrayList<>();
//        this activity is for driver showing passenger details
        sharedPreferences=getSharedPreferences("Ride_Details",MODE_PRIVATE);
        String key= ((SharedPreferences) sharedPreferences).getString("Ride_key","");
        Log.d("key",key);
        FirebaseDatabase.getInstance().getReference("driverRides").child("Realtime").child(key).child("finalpassengerlist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataModelForPassengers value=new DataModelForPassengers();
                for (DataSnapshot ds : snapshot.getChildren()){
                    value=ds.getValue(DataModelForPassengers.class);
                    data.add(value);
                }
                TextView name=findViewById(R.id.name);
                TextView phone=findViewById(R.id.phone);
                TextView current=findViewById(R.id.current_location);
                TextView finaldest=findViewById(R.id.final_destination);
                current.setText(data.get(0).getCurrent_location());
                finaldest.setText(data.get(0).getFinal_destination());
                name.setText(data.get(0).getFullname());
                FirebaseDatabase.getInstance().getReference("users").child("normal").child(data.get(0).getPassenger_uid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}