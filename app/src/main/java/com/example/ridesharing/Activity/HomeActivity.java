package com.example.ridesharing.Activity;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.ridesharing.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback {
    FrameLayout fl;
    FragmentTransaction ft;
    DrawerLayout dl;
    NavigationView nv;
    ActionBarDrawerToggle adt;
    MaterialToolbar tb;
    private GoogleMap gMap;
    MaterialButton createride;
    String[] items = {"Today", "Weekly", "Custom"};
    AutoCompleteTextView dropdown_menu;
    ArrayAdapter<String> adapter;
    int Location_Request_Code = 100;
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        dl = findViewById(R.id.drawerlayout);
        nv = findViewById(R.id.navigation);
//        fl= findViewById(R.id.framelayout);
        tb = findViewById(R.id.toolbar);
        createride = findViewById(R.id.loadEventMenu);
        tb = findViewById(R.id.toolbar);
        tb.setTitle("Driver Mode");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        checkLocationPermission();

        createride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final Dialog dialog=new Dialog(HomeActivity.this);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.uppersheet_menu_layout);
//                dropdown_menu=dialog.findViewById(R.id.dropdown_menu);
//                adapter=new ArrayAdapter<String>(HomeActivity.this,R.layout.dropdown_list,items);
//                dropdown_menu.setAdapter(adapter);
//                FragmentManager fm=getSupportFragmentManager();
//                FragmentTransaction ft= fm.beginTransaction();
//                ft.add(R.id.frame, new WeaklySubscriptionFragment());
//                ft.commit();
//                dropdown_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        if (items[position].equals("Today")) {
////                    do nothing
//                            FragmentManager fm=getSupportFragmentManager();
//                            FragmentTransaction ft= fm.beginTransaction();
//                            ft.replace(R.id.frame, new TodaySubscriptionFragment());
//                            ft.commit();
//                        } else if (items[position].equals("Weekly")) {
//                            FragmentManager fm=getSupportFragmentManager();
//                            FragmentTransaction ft= fm.beginTransaction();
//                            ft.replace(R.id.frame, new WeaklySubscriptionFragment());
//                            ft.commit();
//                        } else if (items[position].equals("Custom")) {
//                            FragmentManager fm=getSupportFragmentManager();
//                            FragmentTransaction ft= fm.beginTransaction();
//                            ft.replace(R.id.frame, new CustomSubscriptionFragment());
//                            ft.commit();
//                        }else{
//                            Toast.makeText(HomeActivity.this, "error", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
//                dialog.getWindow().setGravity(Gravity.TOP);
//                dialog.show();


                DialogFragment dialogFragment = FullScreenDialog.newInstance();
                dialogFragment.show(getSupportFragmentManager(), "tag");

            }
        });
        adt = new ActionBarDrawerToggle(HomeActivity.this, dl, tb, R.string.openDrawer, R.string.closeDrawer);
//        setSupportActionBar(tb)
//        getSupportActionBar().setTitle("Introduction");
//        ft=getSupportFragmentManager().beginTransaction();
//        ft.add(R.id.framelayout,new HomeFragment());
//        ft.commit();
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_profile) {
//                    ft=getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.framelayout,new ProfileFragment());
//                    ft.commit();
//                    getSupportActionBar().setTitle("Service");
                }

                if (id == R.id.menu_history) {
//                    ft=getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.framelayout,new HistoryFragment());
//                    ft.commit();
//                    getSupportActionBar().setTitle("Service");
                }
                if (id == R.id.menu_setting) {
//                    ft=getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.framelayout,new SettingFragment());
//                    ft.commit();
//                    getSupportActionBar().setTitle("Training");
                }
                if (id == R.id.menu_ridedetails) {
//                    ft=getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.framelayout,new RideDetailsFragment());
//                    ft.commit();
                    //                  getSupportActionBar().setTitle("Features Products");
                }
                if (id == R.id.menu_logout) {
//                    ft=getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.framelayout,new LogoutFragment());
//                    ft.commit();
                    //                   getSupportActionBar().setTitle("Contact US");
                }
                if (id == R.id.menu_home) {
//                    ft=getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.framelayout,new HomeFragment());
//                    ft.commit();
                    //                   getSupportActionBar().setTitle("Contact US");
                }
                dl.closeDrawers();

                return true;
            }
        });

    }

    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            requestForPermission();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, Location_Request_Code);
            requestForPermission();
//            getUserLocation();
        }
    }



    private void requestForPermission() {
                // asking for location
        locationRequest=LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);

        LocationSettingsRequest.Builder builder=new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true);
        Task<LocationSettingsResponse> locationSettingsResponseTask= LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());
        locationSettingsResponseTask.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response=task.getResult(ApiException.class);
                } catch (ApiException e) {
                    if (e.getStatusCode()== LocationSettingsStatusCodes.RESOLUTION_REQUIRED){
                        ResolvableApiException resolvableApiException=(ResolvableApiException) e;
                        try {
                            resolvableApiException.startResolutionForResult(HomeActivity.this,101);
                        } catch (IntentSender.SendIntentException ex) {
                            ex.printStackTrace();
                        }
                    }
                    if (e.getStatusCode()==LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE){
                        Toast.makeText(HomeActivity.this, e.getMessage(), LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Location_Request_Code) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Accepted", LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Rejected", LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        adt.syncState();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        gMap.getUiSettings().setMyLocationButtonEnabled(true);
        gMap.getUiSettings().setMapToolbarEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        gMap.setMyLocationEnabled(true);
        gMap.setBuildingsEnabled(true);
        gMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {

                return false;
            }
        });
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        gMap.animateCamera(CameraUpdateFactory.zoomBy(14));



    }
    private void notengaged(String existing_value) {
        if(existing_value.equals("engaged")){
            Log.d("Status","true");
            startActivity(new Intent(HomeActivity.this,PassengersListviewActivity.class));
        }
    };
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseDatabase firebaseDatabase1=FirebaseDatabase.getInstance();
        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();

        firebaseDatabase1.getReference("users/drivers").child(user.getUid()).child("existing_rides").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String existing_value;
                Log.d("Snapshot",snapshot.toString());
                existing_value=snapshot.getValue().toString();
                Log.d( "onDataChange: ",existing_value);
                notengaged(existing_value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==101){
            if (resultCode==RESULT_OK){
                Toast.makeText(this, "GPS Enabled", LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Denied Access GPS", LENGTH_SHORT).show();
            }
        }
    }
}