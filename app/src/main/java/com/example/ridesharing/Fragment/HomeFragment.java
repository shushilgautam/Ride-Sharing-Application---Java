package com.example.ridesharing.Fragment;

import static android.app.ProgressDialog.show;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ridesharing.Activity.HomeActivity;
import com.example.ridesharing.Activity.PassengersListviewActivity;
import com.example.ridesharing.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
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

//import com.example.splashscreen.R;
public class HomeFragment extends Fragment {
    View included;
    MaterialButton hidebtn, showbtn, find;
    MaterialTextView currentLocation, finalDestination, timePicker, datePicker;
    TextView bikeMode,carMode;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    String time, date, currentlocation, finaldestiantion;

    ProgressDialog progressDialog ;
    MaterialToolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myview = inflater.inflate(R.layout.fragment_home, container, false);
        included = myview.findViewById(R.id.included);
        currentLocation = included.findViewById(R.id.currentLocation);
        finalDestination = included.findViewById(R.id.finalDestination);
        timePicker = included.findViewById(R.id.timepicker);
        datePicker = included.findViewById(R.id.datePicker);
        hidebtn = included.findViewById(R.id.hide_button);
        showbtn = myview.findViewById(R.id.show_button);
        find = included.findViewById(R.id.find);
        bikeMode = included.findViewById(R.id.bike);
        carMode = included.findViewById(R.id.car);
        defaultmode();
        progressDialog= new ProgressDialog(getActivity());
        toolbar=getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Driver Mode");

        bikeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bikeMode.setBackgroundResource(R.color.primaryButtonColor);
                bikeMode.setTextColor(Color.WHITE);
                carMode.setBackgroundResource(R.color.white);
                carMode.setTextColor(Color.BLACK);
            }
        });
        carMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carMode.setBackgroundResource(R.color.primaryButtonColor);
                carMode.setTextColor(Color.WHITE);
                bikeMode.setBackgroundResource(R.color.white);
                bikeMode.setTextColor(Color.BLACK);
            }
        });


        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewRide();
            }
        });
        hidebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                included.setVisibility(View.INVISIBLE);
                hidebtn.setVisibility(View.INVISIBLE);
                showbtn.setVisibility(View.VISIBLE);
            }
        });
        showbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                included.setVisibility(View.VISIBLE);
                hidebtn.setVisibility(View.VISIBLE);
                showbtn.setVisibility(View.INVISIBLE);
            }
        });
        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentLocation.setError(null);
            }
        });
        finalDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalDestination.setError(null);
            }
        });
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker.setError(null);
                final Calendar calender = Calendar.getInstance();
                MaterialTimePicker picker = new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H).setHour(calender.get(Calendar.HOUR_OF_DAY)).setMinute(calender.get(Calendar.MINUTE)).setTitleText("Pick Time").setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
//                        .setTheme(R.style.TimePickerTheme)
                        .build();
                picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View v) {
                        String time = new String();
                        if (String.valueOf(picker.getMinute()).length() != 2) {
                            time = String.valueOf(picker.getHour()) + ":0" + String.valueOf(picker.getMinute());
                        } else {
                            time = String.valueOf(picker.getHour()) + ":" + String.valueOf(picker.getMinute());
                        }
                        timePicker.setText(time);
                    }
                });
                picker.show(getFragmentManager(), "Tag");

            }
        });
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.setError(null);
                MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker().setTheme(com.google.android.material.R.style.ThemeOverlay_Material3_MaterialCalendar).setTitleText("Select Date").setSelection(MaterialDatePicker.todayInUtcMilliseconds()).setTheme(com.google.android.material.R.style.ThemeOverlay_Material3_MaterialCalendar).build();

                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        String date = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date(selection));
                        datePicker.setText(MessageFormat.format("{0}", date));
                    }
                });
                materialDatePicker.show(getChildFragmentManager(), "Tag");

            }
        });
        return myview;

    }

    private void createNewRide() {

        if (valid()) {
            progressDialog.setMessage("Searching...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);
            firebaseDatabase.getReference("users/drivers").child(user.getUid()).child("existing_rides").setValue("engaged").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isComplete()){
                        Log.d("tag","success");
                    }else {
                        Log.d("tag",task.getException().getMessage());
                    }
                }
            });
            time = timePicker.getText().toString().trim();
            date = datePicker.getText().toString().trim();
            currentlocation = currentLocation.getText().toString().trim();
            finaldestiantion = finalDestination.getText().toString().trim();
            HashMap<String, Object> map = new HashMap<>();
            map.put("current_location", currentlocation);
            map.put("final_destination", finaldestiantion);
            map.put("time", time);
            map.put("date", date);
            map.put("driver_id", user.getUid());
            map.put("passengers_list", "");
            map.put("ride_status", "Incomplete");
            firebaseDatabase.getReference("users/drivers").child(user.getUid()).child("history").child(currentlocation + "-to-" + finaldestiantion + "-at-time-" + time + "-and-date-" + date).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isComplete()) {
                        Snackbar.make(getView(), "Task is completed", Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(getView(), task.getException().getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                }
            });
            firebaseDatabase.getReference("driverRides").child(currentlocation + "-to-" + finaldestiantion + "-at-time-" + time + "-and-date-" + date).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Intent i = new Intent(getActivity(), PassengersListviewActivity.class);
                    i.putExtra("time", time);
                    i.putExtra("date", date);
                    i.putExtra("currentLocation", currentlocation);
                    i.putExtra("finalDestination", finaldestiantion);
                    progressDialog.hide();
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.hide();
                    new MaterialAlertDialogBuilder(getActivity()).setTitle("Error").setMessage(e.getMessage()).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).show();
                }
            });
        }
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
    private void notengaged(String existing_value) {
        if(existing_value.equals("engaged")){
            Log.d("Status","true");
            startActivity(new Intent(getActivity(),PassengersListviewActivity.class));
        }
    };
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
                Log.d("Snapshot",snapshot.toString());
                existing_value=snapshot.getValue().toString();
                Log.d( "onDataChange: ",existing_value);
                notengaged(existing_value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
