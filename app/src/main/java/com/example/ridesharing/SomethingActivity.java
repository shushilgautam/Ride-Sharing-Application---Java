package com.example.ridesharing;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ridesharing.Fragment.CustomSubscriptionFragment;
import com.example.ridesharing.Fragment.TodaySubscriptionFragment;
import com.example.ridesharing.Fragment.WeaklySubscriptionFragment;

public class SomethingActivity extends AppCompatActivity {
    String[] items={"Today","Weakly","Custom"};
    AutoCompleteTextView dropdown_menu;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_creater_fragment_bike);
        dropdown_menu=findViewById(R.id.dropdown_menu);
        adapter=new ArrayAdapter<String>(this,R.layout.dropdown_list,items);
        dropdown_menu.setAdapter(adapter);
        dropdown_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (items[position].equals("Today")) {
//                    do nothing
                    FragmentManager fm=getSupportFragmentManager();
                    FragmentTransaction ft= fm.beginTransaction();
                    ft.replace(R.id.framelayout, new TodaySubscriptionFragment());
                    ft.commit();
                } else if (items[position].equals("Weakly")) {
                    FragmentManager fm=getSupportFragmentManager();
                    FragmentTransaction ft= fm.beginTransaction();
                    ft.replace(R.id.framelayout, new WeaklySubscriptionFragment());
                    ft.commit();
                } else if (items[position].equals("Custom")) {
                    FragmentManager fm=getSupportFragmentManager();
                    FragmentTransaction ft= fm.beginTransaction();
                    ft.replace(R.id.framelayout, new CustomSubscriptionFragment());
                    ft.commit();
                }else{
                    Toast.makeText(SomethingActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
