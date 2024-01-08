package com.example.ridesharing.Fragment;

import static android.app.ProgressDialog.show;
import static com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ridesharing.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.datepicker.MaterialStyledDatePickerDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

//import com.example.splashscreen.R;
public class HomeFragment extends Fragment {
    View included;
    MaterialButton hidebtn,showbtn,find;
    MaterialTextView currentLocation,finalDestination,timePicker,datePicker;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myview=inflater.inflate(R.layout.fragment_home,container,false);
        included= myview.findViewById(R.id.included);
        currentLocation=included.findViewById(R.id.currentLocation);
        finalDestination=included.findViewById(R.id.finalDestination);
        timePicker=included.findViewById(R.id.timepicker);
        datePicker=included.findViewById(R.id.datePicker);
        hidebtn=myview.findViewById(R.id.hide_button);
        showbtn=myview.findViewById(R.id.show_button);
        find=included.findViewById(R.id.find);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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
                included.setVisibility(View.VISIBLE );
                hidebtn.setVisibility(View.VISIBLE);
                showbtn.setVisibility(View.INVISIBLE);
            }
        });
        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(getView(), R.string.textDemo,Snackbar.LENGTH_LONG).show();

            }
        });
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calender = Calendar.getInstance();
                MaterialTimePicker picker= new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(calender.get(Calendar.HOUR_OF_DAY))
                        .setMinute(calender.get(Calendar.MINUTE))
                        .setTitleText("Pick Time").setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
//                        .setTheme(R.style.TimePickerTheme)
                        .build();
                picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View v) {
                        String time = new String();
                        if(String.valueOf(picker.getMinute()).length()!=2){
                        time= String.valueOf(picker.getHour())+":0"+String.valueOf(picker.getMinute());
                        }else{
                            time= String.valueOf(picker.getHour())+":"+String.valueOf(picker.getMinute());
                        }
                        timePicker.setText(time);
                    }
                });
                picker.show(getFragmentManager(),"Tag");

            }
        });
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker<Long> materialDatePicker= MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select Date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .setTheme(com.google.android.material.R.style.ThemeOverlay_Material3_MaterialCalendar).build();

                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        String date=new SimpleDateFormat("MM-dd-yyyy",Locale.getDefault()).format(new Date(selection));
                        datePicker.setText(MessageFormat.format("{0}",date));
                    }
                });
                materialDatePicker.show(getFragmentManager(),"Tag");

            }
        });
        return myview;

    }



}
