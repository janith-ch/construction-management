package com.example.application_mobile.fragment.order;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.application_mobile.R;
import com.example.application_mobile.constant.Common;
import com.example.application_mobile.constant.OrderConstant;
import com.example.application_mobile.model.Site;
import com.kofigyan.stateprogressbar.StateProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;

public class CreateOrderStepOne extends Fragment {

    private Spinner site_dropdown, location_dropdown;
    private Button button;
    private StateProgressBar stateProgressBar;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private Common common = new Common();
    private OrderConstant orderConstant = new OrderConstant();

    public CreateOrderStepOne() {
   //create default constructor
    }

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

        //set data to state progress bar
        stateProgressBar.setStateDescriptionData(orderConstant.getDescriptionData());

        // call getSiteDetails method for get sit details
        getSiteDetails();

        Orders orders = new Orders();
        CreateOrderStepTwo createOrderStepTwo = new CreateOrderStepTwo();

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Bundle bundle = getArguments();

                bundle.putString(orderConstant.getSITE(), site_dropdown.getSelectedItem().toString());
                bundle.putString(orderConstant.getLOCATION(), location_dropdown.getSelectedItem().toString());

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


    private void getSiteDetails() {

        ArrayAdapter locationAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1);

        //RequestQueue initialized
        requestQueue = Volley.newRequestQueue(getContext());

        List<Site> sitesList = new ArrayList<>();

        jsonObjectRequest = new JsonObjectRequest(

                Request.Method.GET,
                common.getURL().concat(common.getSITE_LIST_ENDPOINT()),
                null,

                new Response.Listener<JSONObject>() {

                    @SneakyThrows
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("Response is", response.toString());

                        JSONArray jsonArray = response.getJSONArray(common.getJSON_PREFIX());

                        for (int i = common.getZERO(); i < jsonArray.length(); i++) {

                            try {

                                Site site = new Site();
                                JSONObject obj = jsonArray.getJSONObject(i);

                                site.setName(obj.getString(orderConstant.getNAME()));
                                site.setId(obj.getInt(orderConstant.getID()));
                                site.setSiteManagerId(obj.getInt(orderConstant.getSITE_MANAGER_ID()));
                                site.setAddress(obj.getString(orderConstant.getADDRESS()));

                                locationAdapter.add(obj.getString(orderConstant.getADDRESS()));

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

                                bundle.putInt(orderConstant.getSITE_ID(), selectedSite.getId());
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