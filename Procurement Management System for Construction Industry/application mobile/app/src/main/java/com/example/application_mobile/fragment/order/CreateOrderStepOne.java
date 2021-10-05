package com.example.application_mobile.fragment.order;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.application_mobile.R;
import com.example.application_mobile.model.Material;
import com.example.application_mobile.model.Order;
import com.example.application_mobile.model.Site;
import com.example.application_mobile.service.JsonReader;
import com.google.gson.JsonArray;
import com.kofigyan.stateprogressbar.StateProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.SneakyThrows;


public class CreateOrderStepOne extends Fragment {

    JSONObject object;

    public CreateOrderStepOne() {
        // Required empty public constructor
    }

    private Spinner site_dropdown, location_dropdown;
    private Button button;
    private StateProgressBar stateProgressBar;
    String[] descriptionData = {"Select Project", "Select Material", "Select Deadline"};
    String url = "http://192.168.1.5:8088/api/v1/sites";


    @SneakyThrows
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_order_step_one, container, false);
        stateProgressBar = view.findViewById(R.id.your_state_progress_bar_id);
        site_dropdown = view.findViewById(R.id.spinner_location);
        location_dropdown = view.findViewById(R.id.spinner_site);
        button = view.findViewById(R.id.create_order_step_01);

        stateProgressBar.setStateDescriptionData(descriptionData);
        getSiteDetails();
        Orders orders = new Orders();
        CreateOrderStepTwo createOrderStepTwo = new CreateOrderStepTwo();
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Bundle bundle = getArguments();
                bundle.putString("site", site_dropdown.getSelectedItem().toString());
                bundle.putString("location", location_dropdown.getSelectedItem().toString());
                createOrderStepTwo.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, createOrderStepTwo).commit();
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {

            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, orders).commit();
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        return view;

    }

    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;

    private void getSiteDetails() {

        ArrayAdapter locationAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1);

        //RequestQueue initialized
        requestQueue = Volley.newRequestQueue(getContext());
        List<Site> sitesList = new ArrayList<>();
        jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @SneakyThrows
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("Response is", response.toString());

                        JSONArray jsonArray = response.getJSONArray("dataBundle");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                Site site = new Site();
                                JSONObject obj = jsonArray.getJSONObject(i);


                                site.setName(obj.getString("name"));
                                site.setId(obj.getInt("id"));
                                site.setSiteManagerId(obj.getInt("siteManagerId"));
                                site.setAddress(obj.getString("address"));
                                locationAdapter.add(obj.getString("address"));

                                sitesList.add(site);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        ArrayAdapter<Site> siteAdapter = new ArrayAdapter<Site>(getContext(), android.R.layout.simple_spinner_item, sitesList);
                        siteAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                     //   location_dropdown.setAdapter(locationAdapter);
                        site_dropdown.setAdapter(siteAdapter);

                        site_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                CreateOrderStepTwo createOrderStepTwo = new CreateOrderStepTwo();
                                Site selectedSite = (Site) site_dropdown.getSelectedItem();

                                Bundle bundle = getArguments();
                                bundle.putInt("siteId", selectedSite.getId());
                                location_dropdown.setAdapter(locationAdapter);
                                createOrderStepTwo.setArguments(bundle);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Response", error.toString());
            }
        }

        );
        requestQueue.add(jsonObjectRequest);

    }

}