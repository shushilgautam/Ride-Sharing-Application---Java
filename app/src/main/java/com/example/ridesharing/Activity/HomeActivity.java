package com.example.ridesharing.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.ridesharing.Fragment.HistoryFragment;
import com.example.ridesharing.Fragment.HomeFragment;
import com.example.ridesharing.Fragment.LogoutFragment;
import com.example.ridesharing.Fragment.ProfileFragment;
import com.example.ridesharing.Fragment.RideDetailsFragment;
import com.example.ridesharing.Fragment.SettingFragment;
import com.example.ridesharing.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
    FrameLayout fl;
    FragmentTransaction ft;
    DrawerLayout dl;
    NavigationView nv;
    ActionBarDrawerToggle adt;
    MaterialToolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        dl=findViewById(R.id.drawerlayout);
        nv=findViewById(R.id.navigation);
        fl= findViewById(R.id.framelayout);
        tb=findViewById(R.id.toolbar);
        adt=new ActionBarDrawerToggle(HomeActivity.this,dl,tb,R.string.openDrawer,R.string.closeDrawer);
//        setSupportActionBar(tb)
//        getSupportActionBar().setTitle("Introduction");
        ft=getSupportFragmentManager().beginTransaction();
        ft.add(R.id.framelayout,new HomeFragment());
        ft.commit();
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if (id==R.id.menu_profile)
                {
                    ft=getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.framelayout,new ProfileFragment());
                    ft.commit();
//                    getSupportActionBar().setTitle("Service");
                }

                if (id==R.id.menu_history)
                {
                    ft=getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.framelayout,new HistoryFragment());
                    ft.commit();
//                    getSupportActionBar().setTitle("Service");
                }
                if (id==R.id.menu_setting)
                {
                    ft=getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.framelayout,new SettingFragment());
                    ft.commit();
//                    getSupportActionBar().setTitle("Training");
                }
                if (id==R.id.menu_ridedetails)
                {
                    ft=getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.framelayout,new RideDetailsFragment());
                    ft.commit();
  //                  getSupportActionBar().setTitle("Features Products");
                }
                if (id==R.id.menu_logout)
                {
                    ft=getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.framelayout,new LogoutFragment());
                    ft.commit();
 //                   getSupportActionBar().setTitle("Contact US");
                }
                if (id==R.id.menu_home)
                {
                    ft=getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.framelayout,new HomeFragment());
                    ft.commit();
                    //                   getSupportActionBar().setTitle("Contact US");
                }
                dl.closeDrawers();

                return true;
            }
        });
    }
    private void notengaged(String existing_value) {
        if(existing_value.equals("engaged")){
            Log.d("Status","true");
            startActivity(new Intent(HomeActivity.this,PassengersListviewActivity.class));
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseDatabase firebaseDatabase1=FirebaseDatabase.getInstance();
        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        firebaseDatabase1.getReference("users/drivers").child(user.getUid()).child("existing_rides").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String existing_value;
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
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        adt.syncState();
    }
}