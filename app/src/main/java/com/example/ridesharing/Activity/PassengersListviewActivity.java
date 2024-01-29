package com.example.ridesharing.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ridesharing.Fragment.CustomClassForHistory;
import com.example.ridesharing.Fragment.DataModelForHistory;
import com.example.ridesharing.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class PassengersListviewActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    ListView listView;
    MaterialToolbar toolbar;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser user=auth.getCurrentUser();
    ArrayList<DataModelForPassengers> data=new ArrayList<DataModelForPassengers>();
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
         sharedPreferences= PreferenceManager
                .getDefaultSharedPreferences(this);
        setContentView(R.layout.activity_listview);
        listView=findViewById(R.id.listview);
        toolbar=findViewById(R.id.topAppBar);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        Intent i=getIntent();
        String time,date,currentlocation,finaldestination,rideTableName = null;
        if(i.getStringExtra("time")!=null){
            time=i.getStringExtra("time");
            date=i.getStringExtra("date");
            currentlocation=i.getStringExtra("currentLocation");
            finaldestination=i.getStringExtra("finalDestination");
            myEdit.putString("ride_name",currentlocation + "-to-" + finaldestination + "-at-time-" + time + "-and-date-" + date).apply();
        }
//        if(!sharedPreferences.getString("ride_name","").isBlank()){
            rideTableName=sharedPreferences.getString("ride_name","");
        Log.d( "onCreate: ",rideTableName);
        HashMap<String,Object> map=new HashMap<>();
        map.put("from","Baneswor Multiple Campus");
        map.put("to","Kalanki Multiple Campus");
        map.put("uid",user.getUid());
        map.put("fullname","Kaizer Gautam");
        firebaseDatabase.getReference("driverRides").child(rideTableName).child("passengers_list").child("Kaizer").setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(PassengersListviewActivity.this, "Data Added", Toast.LENGTH_SHORT).show();
            }
        });

        firebaseDatabase.getReference("driverRides").child(rideTableName).child("passengers_list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                DataModelForPassengers value=new DataModelForPassengers();
                for(DataSnapshot ds:snapshot.getChildren()){
                    value=ds.getValue(DataModelForPassengers.class);
                    data.add(value);
                }
                Log.d("Hello data",data.toString());
                listView.setAdapter(new CustomClassForPassengersList(PassengersListviewActivity.this, data));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PassengersListviewActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}