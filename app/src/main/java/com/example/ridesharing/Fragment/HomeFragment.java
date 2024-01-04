package com.example.ridesharing.Fragment;

import static com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ridesharing.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//import com.example.splashscreen.R;
public class HomeFragment extends Fragment {
    View included;
    MaterialTextView currentLocation,finalDestination,timePicker,datePicker;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myview=inflater.inflate(R.layout.fragment_home,container,false);
//        final Dialog dialog=new Dialog(getActivity());
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.uppersheet_menu_layout);
//        dialog.show();
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
//        dialog.getWindow().setGravity(Gravity.START);
        included= myview.findViewById(R.id.included);
        currentLocation=included.findViewById(R.id.currentLocation);
        finalDestination=included.findViewById(R.id.finalDestination);
        timePicker=included.findViewById(R.id.timepicker);
        datePicker=included.findViewById(R.id.datePicker);
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialTimePicker picker= new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                                .setHour(12)
                                .setMinute(0)
                        .setTitleText("Pick Time").setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                                .build();
                picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        timePicker.setText(MessageFormat.format("{0}:{1}",String.format(Locale.getDefault(),"%02d",picker.getHour(),String.format(Locale.getDefault(),"%02d",picker.getMinute()))));
                        timePicker.setText(picker.getHour()+":"+picker.getMinute());
                    }
                });
                picker.show(getFragmentManager(),"Tag");
//                picker.setInputMode(INPUT_MODE_KEYBOARD);
            }
        });
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker<Long> materialDatePicker=MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select Date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();
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
