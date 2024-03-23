package com.example.ridesharing.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ridesharing.Fragment.CustomSubscriptionFragment;
import com.example.ridesharing.Fragment.TodaySubscriptionFragment;
import com.example.ridesharing.Fragment.WeaklySubscriptionFragment;
import com.example.ridesharing.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class FullScreenDialog2 extends DialogFragment {
    private ArrayAdapter<String> adapter;
    String[] items={"Today","Weekly","Custom"};
    ListView listView;
    AutoCompleteTextView dropdown_menu,current_location,final_destination,startDate,endDate,time;
    CheckBox sunday,monday,tuesday,wednesday,thrusday,friday,saturday,selectAll;
    TextInputLayout current,final_loc;
    private  FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private FirebaseAuth auth=FirebaseAuth.getInstance();
    private FirebaseUser user=auth.getCurrentUser();

    static FullScreenDialog2 newInstance(){
        return new FullScreenDialog2();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTheme);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myview=inflater.inflate(R.layout.uppersheet_menu_layout,container,false);
        adapter=new ArrayAdapter<String>(getContext(),R.layout.dropdown_list,items);
        MaterialButton dialog_cancel=myview.findViewById(R.id.dialog_cancel);
        MaterialButton createRide=myview.findViewById(R.id.createRide);
        createRide.setText("Find Ride");
//        createRide.setIcon(R.drawable.search);
        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        current_location=myview.findViewById(R.id.current_location);
        final_destination=myview.findViewById(R.id.final_destination);
        ArrayList<String> location=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("locations").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()){
                    String name=ds.getValue().toString();
//                    Log.d("value",name);
                    location.add(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        current_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.bottomsheet_location);
                listView=dialog.findViewById(R.id.listview);
                listView.setAdapter(new CustomClassForLocations(getActivity(),location,current_location,dialog));
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations=R.style.BottomDialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);

            }
        });
        final_destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.bottomsheet_location);
                listView=dialog.findViewById(R.id.listview);
                listView.setAdapter(new CustomClassForLocations(getActivity(),location,final_destination,dialog));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations=R.style.BottomDialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.show();
            }
        });
        dropdown_menu=myview.findViewById(R.id.dropdown_menu);
        dropdown_menu.setAdapter(adapter);
        dropdown_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (items[position].equals("Today")) {
//                    do nothing
                            FragmentManager fm=getChildFragmentManager();
                            FragmentTransaction ft= fm.beginTransaction();
                            ft.replace(R.id.frame, new TodaySubscriptionFragment());
                            ft.commit();
                        } else if (items[position].equals("Weekly")) {
                            FragmentManager fm=getChildFragmentManager();
                            FragmentTransaction ft= fm.beginTransaction();
                            ft.replace(R.id.frame, new WeaklySubscriptionFragment());
                            ft.commit();
                        } else if (items[position].equals("Custom")) {
                            FragmentManager fm=getChildFragmentManager();
                            FragmentTransaction ft= fm.beginTransaction();
                            ft.replace(R.id.frame, new CustomSubscriptionFragment());
                            ft.commit();
                        }else{
                            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                        }
            }
        });
        createRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseDatabase.getReference("users/drivers").child(user.getUid()).child("existing_rides").setValue("engaged").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()){

                        }else{
                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//                Log.d("hello","hello world after firebase");
                if(dropdown_menu.getText().toString().equals(items[0])){
//
                    findTodayRide();
                } else if (dropdown_menu.getText().toString().equals(items[1])) {
//
//                    createWeeklyRide();
                } else if (dropdown_menu.getText().toString().equals(items[2])) {
//
//                    createCustomRide();
                }
            }
        });




        return myview;
    }
    String currentlocation, finaldestiantion;
    private void findTodayRide() {
        currentlocation = current_location.getText().toString().trim();
        finaldestiantion = final_destination.getText().toString().trim();
        String Ride_Key=currentlocation + "-to-" + finaldestiantion;
        HashMap<String, Object> map = new HashMap<>();
        map.put("current_location", currentlocation);
        map.put("final_destination", finaldestiantion);
        map.put("passenger_uid",user.getUid());
        map.put("fullname",user.getDisplayName());
        firebaseDatabase.getReference("driverRides").child("Realtime").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(Ride_Key)){
                    firebaseDatabase.getReference("driverRides").child("Realtime").child(Ride_Key).child("passengers_list").child(user.getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isComplete()){
                                new MaterialAlertDialogBuilder(getActivity()).setTitle("Request Has been Submitted ")
                                        .setMessage("Please wait while the driver accept the offer.It can take a few minutes.")
                                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        }).show();
                            }else{
                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    new MaterialAlertDialogBuilder(getActivity()).setTitle("Ride Not Found")
                            .setMessage("Sorry currently no drivers are available.Please try again after few minutes.")
                            .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
