package com.example.ridesharing.Activity;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.ridesharing.R;

import java.util.ArrayList;

public class CustomClassForLocations extends BaseAdapter {
    Context c;
    ArrayList<String> data;
    AutoCompleteTextView field;
    Dialog dialog;

    public CustomClassForLocations(Context context, ArrayList<String> location, AutoCompleteTextView field, Dialog dialog) {
        c=context;
        data=location;
        this.field=field;
        this.dialog=dialog;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View myview= LayoutInflater.from(c).inflate(R.layout.custom_location_listview,null);
        TextView name=myview.findViewById(R.id.locationName);

        name.setText(data.get(position));
        myview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                field.setText(name.getText().toString());
                dialog.dismiss();
            }
        });
        return myview;
    }
}
