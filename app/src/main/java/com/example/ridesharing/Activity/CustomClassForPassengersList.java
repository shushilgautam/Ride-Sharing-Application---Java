package com.example.ridesharing.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ridesharing.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomClassForPassengersList extends BaseAdapter {
    Context c;
    ArrayList<DataModelForPassengers> data;
    String rideTableName;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser user= auth.getCurrentUser();
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    public CustomClassForPassengersList(PassengersListviewActivity passengersListviewActivity, ArrayList<DataModelForPassengers> data,String rideTableName) {
        c=passengersListviewActivity;
        this.data=data;
        this.rideTableName=rideTableName;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View myview= LayoutInflater.from(c).inflate(R.layout.custom_listview,null);
        TextView fullname=myview.findViewById(R.id.name);
        TextView from_loacation=myview.findViewById(R.id.from);
        TextView to_location=myview.findViewById(R.id.to);
        MaterialButton acceptBtn=myview.findViewById(R.id.btnaccept);
        String passengeruid=data.get(position).getPassenger_uid();
        fullname.setText(data.get(position).getFullname().toString());
        from_loacation.setText(data.get(position).getCurrent_location().toString());
        to_location.setText(data.get(position).getFinal_destination().toString());
//        MaterialButton declineBtn=myview.findViewById(R.id.btnaccept);
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFinalPassengersList(fullname.getText().toString().trim(),from_loacation.getText().toString().trim(),to_location.getText().toString().trim(),passengeruid);
            }
        });
        return myview;
    }

    private void addToFinalPassengersList(String fullname, String from_location, String to_location,String passenger_uid) {
        HashMap<String ,Object> map=new HashMap<>();
        map.put("fullname",fullname);
        map.put("from_location",from_location);
        map.put("to_location",to_location);
        map.put("uid",passenger_uid);
        firebaseDatabase.getReference("driverRides").child("Realtime").child(rideTableName).child("Final_passengers_list").child(passenger_uid).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(c, "Ride Confirmed Intent to next activity", Toast.LENGTH_SHORT).show();
//                    intent to new activity that shows map and driver details;
                }else{
                    Toast.makeText(c, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


}
