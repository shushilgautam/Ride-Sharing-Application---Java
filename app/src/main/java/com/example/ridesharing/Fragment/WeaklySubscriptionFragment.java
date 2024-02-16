package com.example.ridesharing.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ridesharing.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeaklySubscriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeaklySubscriptionFragment extends Fragment {



    public WeaklySubscriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview=inflater.inflate(R.layout.fragment_weakly_subscription, container, false);

        return myview;
    }
}