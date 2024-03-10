package com.example.ridesharing.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TimePicker;

import com.example.ridesharing.R;
import com.google.android.material.button.MaterialButton;


public class CustomSubscriptionFragment extends Fragment {
    String StartDateValue, EndDateValue ,timeSelected;
    CheckBox sunday,monday,tuesday,wednesday,thrusday,friday,saturday,selectAll;
    int checkedCounter=0;


    public CustomSubscriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview=inflater.inflate(R.layout.fragment_custom_subscription, container, false);
        AutoCompleteTextView startdate,enddate,time;
        startdate=myview.findViewById(R.id.startdate);
        enddate=myview.findViewById(R.id.enddate);
        time=myview.findViewById(R.id.time);
        sunday=myview.findViewById(R.id.sunday);
        monday=myview.findViewById(R.id.monday);
        tuesday=myview.findViewById(R.id.tuesday);
        thrusday=myview.findViewById(R.id.thursday);
        wednesday=myview.findViewById(R.id.wednesday);
        friday=myview.findViewById(R.id.friday);
        saturday=myview.findViewById(R.id.saturday);
        selectAll=myview.findViewById(R.id.selectAll);
        sunday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sunday.setTextColor(getResources().getColor(R.color.green_main));
                    checkedCounter++;
                }else{
                    sunday.setTextColor(getResources().getColor(R.color.black));
                    checkedCounter--;
                }
                if (checkedCounter==7){
                    selectAll.setChecked(true);
                }else{
                    selectAll.setChecked(false);
                }
            }
        });
        monday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    monday.setTextColor(getResources().getColor(R.color.green_main));
                    checkedCounter++;
                }else{
                    monday.setTextColor(getResources().getColor(R.color.black));
                    checkedCounter--;
                }
                if (checkedCounter==7){
                    selectAll.setChecked(true);
                }else{
                    selectAll.setChecked(false);
                }
            }
        });
        tuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    tuesday.setTextColor(getResources().getColor(R.color.green_main));
                    checkedCounter++;
                }else{
                    tuesday.setTextColor(getResources().getColor(R.color.black));
                    checkedCounter--;
                }
                if (checkedCounter==7){
                    selectAll.setChecked(true);
                }else{
                    selectAll.setChecked(false);
                }
            }
        });
        wednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    wednesday.setTextColor(getResources().getColor(R.color.green_main));
                    checkedCounter++;
                }else{
                    wednesday.setTextColor(getResources().getColor(R.color.black));
                    checkedCounter--;
                }
                if (checkedCounter==7){
                    selectAll.setChecked(true);
                }else{
                    selectAll.setChecked(false);
                }
            }
        });
        thrusday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    thrusday.setTextColor(getResources().getColor(R.color.green_main));
                    checkedCounter++;
                }else{
                    thrusday.setTextColor(getResources().getColor(R.color.black));
                    checkedCounter--;
                }
                if (checkedCounter==7){
                    selectAll.setChecked(true);
                }else{
                    selectAll.setChecked(false);
                }
            }
        });
        friday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    friday.setTextColor(getResources().getColor(R.color.green_main));
                    checkedCounter++;
                }else{
                    friday.setTextColor(getResources().getColor(R.color.black));
                    checkedCounter--;
                }
                if (checkedCounter==7){
                    selectAll.setChecked(true);
                }else{
                    selectAll.setChecked(false);
                }
            }
        });
        saturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    saturday.setTextColor(getResources().getColor(R.color.green_main));
                    checkedCounter++;
                }else{
                    saturday.setTextColor(getResources().getColor(R.color.black));
                    checkedCounter--;
                }
                if (checkedCounter==7){
                    selectAll.setChecked(true);
                }else{
                    selectAll.setChecked(false);
                }
            }
        });
        selectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    selectAll.setTextColor(getResources().getColor(R.color.blue_button));
                    sunday.setChecked(true);
                    monday.setChecked(true);
                    tuesday.setChecked(true);
                    wednesday.setChecked(true);
                    thrusday.setChecked(true);
                    friday.setChecked(true);
                    saturday.setChecked(true);
                }else{
                    sunday.setTextColor(getResources().getColor(R.color.black));
                    sunday.setChecked(false);
                    monday.setChecked(false);
                    tuesday.setChecked(false);
                    wednesday.setChecked(false);
                    thrusday.setChecked(false);
                    friday.setChecked(false);
                    saturday.setChecked(false);
                }
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.timepicker_view_bottom);
                TimePicker timePicker=dialog.findViewById(R.id.timepicker);
                MaterialButton done=dialog.findViewById(R.id.done);
                MaterialButton cancel=dialog.findViewById(R.id.cancel);

                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        timeSelected=hourOfDay+":"+minute;
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        time.setText(timeSelected);
                        dialog.dismiss();
                    }
                });
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations=R.style.BottomDialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
            }
        });
        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.calenderview_bottom);
                CalendarView calendarView=dialog.findViewById(R.id.calenderView);
                MaterialButton done=dialog.findViewById(R.id.done);
                MaterialButton cancel=dialog.findViewById(R.id.cancel);
                calendarView.setMinDate(System.currentTimeMillis());
                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        StartDateValue =year+"/"+(month+1)+"/"+dayOfMonth;
                        Log.d("onSelectedDayChange: ", StartDateValue);
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startdate.setText(StartDateValue);
                        dialog.dismiss();
                    }
                });
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations=R.style.BottomDialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
            }
        });
        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.calenderview_bottom);
                CalendarView calendarView=dialog.findViewById(R.id.calenderView);
                MaterialButton done=dialog.findViewById(R.id.done);
                MaterialButton cancel=dialog.findViewById(R.id.cancel);
                calendarView.setMinDate(System.currentTimeMillis());
                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        EndDateValue =year+"/"+(month+1)+"/"+dayOfMonth;
                        Log.d("onSelectedDayChange: ", EndDateValue);
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        enddate.setText(EndDateValue);
                        dialog.dismiss();
                    }
                });
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations=R.style.BottomDialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
            }
        });

        return myview;
    }
}