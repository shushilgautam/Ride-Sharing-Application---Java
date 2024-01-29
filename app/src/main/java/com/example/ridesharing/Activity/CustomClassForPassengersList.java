package com.example.ridesharing.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.ridesharing.Fragment.DataModelForHistory;
import com.example.ridesharing.R;

import java.util.ArrayList;

public class CustomClassForPassengersList extends BaseAdapter {
    Context c;
    ArrayList<DataModelForPassengers> data;

    public CustomClassForPassengersList(PassengersListviewActivity passengersListviewActivity, ArrayList<DataModelForPassengers> data) {
        c=passengersListviewActivity;
        this.data=data;
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
        View myview= LayoutInflater.from(c).inflate(R.layout.custom_listview,null);
        TextView fullname=myview.findViewById(R.id.name);
        TextView from_loacation=myview.findViewById(R.id.from);
        TextView to_location=myview.findViewById(R.id.to);
        fullname.setText(data.get(position).getFullname().toString());
        from_loacation.setText(data.get(position).getFrom().toString());
        to_location.setText(data.get(position).getTo().toString());
        return myview;
    }
}
