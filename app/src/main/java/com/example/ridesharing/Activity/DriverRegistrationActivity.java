package com.example.ridesharing.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ridesharing.R;

public class DriverRegistrationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_registration);

        TextView info,driverlicense,vehinfo,idinfo;

        info=findViewById(R.id.txt_basic);
        driverlicense=findViewById(R.id.txt_license);
        vehinfo=findViewById(R.id.txt_vehicleinfo);
        idinfo=findViewById(R.id.txt_idinfo);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DriverRegistrationActivity.this, BasicInfoActivity.class);
                startActivity(intent);
            }
        });
        driverlicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DriverRegistrationActivity.this, DriverLiscenseActivity.class);
                startActivity(intent);
            }
        });
        vehinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DriverRegistrationActivity.this, VehicleInfoActivity.class);
                startActivity(intent);
            }
        });
        idinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DriverRegistrationActivity.this, IdConfirmation.class);
                startActivity(intent);
            }
        });

    }
}
