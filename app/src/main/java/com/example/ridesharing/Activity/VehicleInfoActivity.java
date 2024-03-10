package com.example.ridesharing.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ridesharing.R;

public class VehicleInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_info);

        TextView transport,numberplate,vehiclephoto,billbook;

        transport=findViewById(R.id.txt_transport);
        numberplate=findViewById(R.id.txt_numberplate);
        vehiclephoto=findViewById(R.id.txt_vehiclephoto);
        billbook=findViewById(R.id.txt_billbook);


        transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VehicleInfoActivity.this,BrandTraspotActivity.class);
                startActivity(i);
            }
        });

        numberplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VehicleInfoActivity.this,NumberplateActivity.class);
                startActivity(i);
            }
        });

        vehiclephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VehicleInfoActivity.this,VehiclePhotoActivity.class);
                startActivity(i);

            }
        });

       billbook.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(VehicleInfoActivity.this,BillBookActivity.class);
               startActivity(i);

           }
       });
    }
}
