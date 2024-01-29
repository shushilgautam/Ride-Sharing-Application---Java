package com.example.ridesharing.Fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.ridesharing.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class CustomClassForHistory extends BaseAdapter {
    Context c;
    ArrayList<DataModelForHistory> data;
    public CustomClassForHistory(Context c, ArrayList<DataModelForHistory> data) {
        this.c=c;
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
        View myview= LayoutInflater.from(c).inflate(R.layout.custom_history_listview,null);
        TextView current_location=myview.findViewById(R.id.addressFrom);
        TextView final_destination=myview.findViewById(R.id.addressTo);
        TextView date=myview.findViewById(R.id.date);
        TextView time=myview.findViewById(R.id.time);
        TextView ride_status=myview.findViewById(R.id.ride_status);
        ShapeableImageView imageView=myview.findViewById(R.id.imageview);
        current_location.setText(data.get(position).getCurrent_location());
        final_destination.setText(data.get(position).getFinal_destination());
        date.setText(data.get(position).getDate());
        time.setText(data.get(position).getTime());
        ride_status.setText(data.get(position).getRide_status());
        if(data.get(position).getRide_status().equals("Complete")) {
            imageView.setImageResource(R.drawable.tick_mark);
        }
        else if (data.get(position).getRide_status().equals("Incomplete")) {
            imageView.setImageResource(R.drawable.incomplete);
        }else{
            imageView.setImageResource(R.drawable.cancled);
        }
        return myview;
    }
}
