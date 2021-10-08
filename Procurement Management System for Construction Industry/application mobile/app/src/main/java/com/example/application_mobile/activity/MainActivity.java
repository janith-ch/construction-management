package com.example.application_mobile.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.application_mobile.R;
import com.example.application_mobile.fragment.delivery.DeliveryList;
import com.example.application_mobile.fragment.order.Orders;
import com.example.application_mobile.fragment.invoice.Invoices;
import com.example.application_mobile.fragment.quotation.Quotations;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView;
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.order);
    }

    Orders orders = new Orders();
    Invoices invoices = new Invoices();
    Quotations quotations = new Quotations();
    DeliveryList deliveryList = new DeliveryList();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.order:

                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,orders).commit();
                return true;


            case R.id.delivery:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, deliveryList).commit();
                return true;

            case R.id.invoice:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, invoices).commit();
                return true;
            case R.id.quotation:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, quotations).commit();
                return true;
        }
        return false;
    }

}