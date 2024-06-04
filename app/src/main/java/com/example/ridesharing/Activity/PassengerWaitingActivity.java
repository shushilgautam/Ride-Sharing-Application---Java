package com.example.ridesharing.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.ridesharing.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class PassengerWaitingActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    String Ride_Key = null;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_waiting);
        Intent i = getIntent();
        Ride_Key = i.getStringExtra("key");
        Boolean value = true;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        firebaseDatabase.getReference("driverRides").child("Realtime").child(Ride_Key).child("passengers_list").child(user.getUid())
                                .child("status").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Log.d("onuiThread","Thread is running");
                                        if (snapshot.getValue().toString().equals("Confirmed")) {
                                            startActivity(new Intent(PassengerWaitingActivity.this, OngoingRideActivity.class));
                                        } else if (snapshot.getValue().toString().equals("Pending")) {

                                        } else if (snapshot.getValue().toString().equals("Cancled")) {

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                    }
                });
            }
        }, 10000, 20000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        timer.cancel();
    }
}
