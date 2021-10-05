package com.example.application_mobile.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.application_mobile.R;
import com.example.application_mobile.fragment.order.ReceiveOrders;


public class Home extends Fragment {
Button button;
    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home, container, false);
        button = view.findViewById(R.id.delivery_button);
        ReceiveOrders receiveOrders = new ReceiveOrders();
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, receiveOrders).commit();
            }
        });
        return view;
    }
}