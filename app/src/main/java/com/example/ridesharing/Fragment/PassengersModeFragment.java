package com.example.ridesharing.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ridesharing.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class PassengersModeFragment extends Fragment {

    MaterialToolbar toolbar;
    View included;
    MaterialButton hidebtn, showbtn, find;
    MaterialTextView currentLocation, finalDestination, timePicker, datePicker;
    TextView bikeMode, carMode;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    String time, date, currentlocation, finaldestiantion;
    ProgressDialog progressDialog;

    public PassengersModeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_passengers_mode, container, false);
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Passengers Mode");
        included = myview.findViewById(R.id.included);

//        currentLocation = included.findViewById(R.id.currentLocation);
//        finalDestination = included.findViewById(R.id.finalDestination);
//        timePicker = included.findViewById(R.id.timepicker);
//        datePicker = included.findViewById(R.id.datePicker);
//        hidebtn = included.findViewById(R.id.hide_button);
//        showbtn = myview.findViewById(R.id.show_button);
//        find = included.findViewById(R.id.find);
//        bikeMode = included.findViewById(R.id.bike);
//        carMode = included.findViewById(R.id.car);
//        defaultmode();
//        new MaterialAlertDialogBuilder(getActivity()).setTitle("Testing....")
//                .setMessage("Hello world my name is something....")
//                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                }).show();
//        progressDialog= new ProgressDialog(getActivity());
//        bikeMode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                bikeMode.setBackgroundResource(R.color.primaryButtonColor);
//                bikeMode.setTextColor(Color.WHITE);
//                carMode.setBackgroundResource(R.color.white);
//                carMode.setTextColor(Color.BLACK);
//            }
//        });
//        carMode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                carMode.setBackgroundResource(R.color.primaryButtonColor);
//                carMode.setTextColor(Color.WHITE);
//                bikeMode.setBackgroundResource(R.color.white);
//                bikeMode.setTextColor(Color.BLACK);
//            }
//        });
//
//
//        find.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SearchRide();
//            }
//        });
//        hidebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                included.setVisibility(View.INVISIBLE);
//                hidebtn.setVisibility(View.INVISIBLE);
//                showbtn.setVisibility(View.VISIBLE);
//            }
//        });
//        showbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                included.setVisibility(View.VISIBLE);
//                hidebtn.setVisibility(View.VISIBLE);
//                showbtn.setVisibility(View.INVISIBLE);
//            }
//        });
//        currentLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                currentLocation.setError(null);
//            }
//        });
//        finalDestination.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finalDestination.setError(null);
//            }
//        });
//        timePicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                timePicker.setError(null);
//                final Calendar calender = Calendar.getInstance();
//                MaterialTimePicker picker = new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H).setHour(calender.get(Calendar.HOUR_OF_DAY)).setMinute(calender.get(Calendar.MINUTE)).setTitleText("Pick Time").setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
////                        .setTheme(R.style.TimePickerTheme)
//                        .build();
//                picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void onClick(View v) {
//                        String time = new String();
//                        if (String.valueOf(picker.getMinute()).length() != 2) {
//                            time = String.valueOf(picker.getHour()) + ":0" + String.valueOf(picker.getMinute());
//                        } else {
//                            time = String.valueOf(picker.getHour()) + ":" + String.valueOf(picker.getMinute());
//                        }
//                        timePicker.setText(time);
//                    }
//                });
//                picker.show(getFragmentManager(), "Tag");
//
//            }
//        });
//        datePicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                datePicker.setError(null);
//                MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").setSelection(MaterialDatePicker.todayInUtcMilliseconds()).setTheme(com.google.android.material.R.style.ThemeOverlay_Material3_MaterialCalendar).build();
//
//                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
//                    @Override
//                    public void onPositiveButtonClick(Long selection) {
//                        String date = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date(selection));
//                        datePicker.setText(MessageFormat.format("{0}", date));
//                    }
//                });
//                materialDatePicker.show(getFragmentManager(), "Tag");
//
//            }
//        });
        return myview;
    }

    private void SearchRide() {

        if (valid()) {
            progressDialog.setMessage("Searching...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);
            firebaseDatabase.getReference("users/normal").child(user.getUid()).child("in_ride").setValue("True").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isComplete()) {
                        Log.d("tag", "success");
                    } else {
                        Log.d("tag", task.getException().getMessage());
                    }
                }
            });
            time = timePicker.getText().toString().trim();
            date = datePicker.getText().toString().trim();
            currentlocation = currentLocation.getText().toString().trim();
            finaldestiantion = finalDestination.getText().toString().trim();
            setToEmpty();
            String Ride_Key=currentlocation + "-to-" + finaldestiantion + "-at-time-" + time + "-and-date-" + date;
            HashMap<String, Object> map = new HashMap<>();
            map.put("current_location", currentlocation);
            map.put("final_destination", finaldestiantion);
            map.put("passenger_uid",user.getUid());
            map.put("fullname",user.getDisplayName());
            firebaseDatabase.getReference("driverRides").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(Ride_Key)){
                        firebaseDatabase.getReference("driverRides").child(Ride_Key).child("passengers_list").child(user.getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isComplete()){
                                   progressDialog.cancel();
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
                                    progressDialog.cancel();
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
                        progressDialog.cancel();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void setToEmpty() {
//        currentLocation.setText(null);
//        finalDestination.setText(null);
        timePicker.setText(null);
        datePicker.setText(null);
    }

    private void defaultmode() {
        bikeMode.setBackgroundResource(R.color.primaryButtonColor);
        bikeMode.setTextColor(Color.WHITE);
        carMode.setBackgroundResource(R.color.white);
        carMode.setTextColor(Color.BLACK);
    }

    private boolean valid() {
        boolean validity = true;
        if (currentLocation.getText().toString().trim().isEmpty()) {
            currentLocation.setError("Empty field");
            validity = false;
        }
        if (finalDestination.getText().toString().trim().isEmpty()) {
            validity = false;
            finalDestination.setError("Empty field");
        }
        if (datePicker.getText().toString().trim().isEmpty()) {
            validity = false;
            datePicker.setError("Empty field");
        }
        if (timePicker.getText().toString().trim().isEmpty()) {
            validity = false;
            timePicker.setError("Empty field");
        }
        return validity;
    }
}