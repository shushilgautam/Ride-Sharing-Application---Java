package com.example.ridesharing.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ridesharing.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

//import com.example.splashscreen.R;

public class HistoryFragment extends Fragment {
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseUser user=firebaseAuth.getCurrentUser();
    MaterialToolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myview = inflater.inflate(R.layout.fragment_history, container, false);
        ListView listView=myview.findViewById(R.id.listview);
        ArrayList<DataModelForHistory> data =new ArrayList<DataModelForHistory>();
        ArrayList<DataModelForHistory> reverse_data =new ArrayList<DataModelForHistory>();
        toolbar=getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("History");
        firebaseDatabase.getReference("users/drivers").child(user.getUid()).child("history").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataModelForHistory value=new DataModelForHistory();
                for(DataSnapshot ds:snapshot.getChildren()){
                    value=ds.getValue(DataModelForHistory.class);
                    data.add(value);
                }
                Collections.reverse(data);
                listView.setAdapter(new CustomClassForHistory(getActivity(), data));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return myview;
    }
}
