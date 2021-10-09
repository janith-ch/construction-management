package com.example.application_mobile.fragment.delivery;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.application_mobile.R;
import com.example.application_mobile.adapter.DeliveryAdapter;
import com.example.application_mobile.constant.Common;
import com.example.application_mobile.constant.DeliveryConstant;
import com.example.application_mobile.fragment.order.ReceiveOrders;
import com.example.application_mobile.model.Delivery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;


public class DeliveryList extends Fragment {

    private List<Delivery> deliveryList = new ArrayList<>();
    private Common common = new Common();
    private DeliveryConstant deliveryConstant = new DeliveryConstant();
    private RecyclerView recyclerView;
    private DeliveryAdapter deliveryAdapter;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private Button button;


    public DeliveryList() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDeliveryDetails();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_delivery_list, container, false);
        recyclerView = view.findViewById(R.id.delivery_recycle_view);
        button = view.findViewById(R.id.receive_delivery_create);
        deliveryAdapter = new DeliveryAdapter(deliveryList, getContext());


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(deliveryAdapter);

        ReceiveOrders receiveOrders = new ReceiveOrders();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, receiveOrders).commit();
            }
        });

        return view;
    }

    private void getDeliveryDetails() {

        //RequestQueue initialized
        requestQueue = Volley.newRequestQueue(getContext());

        jsonObjectRequest = new JsonObjectRequest(

                Request.Method.GET,
                common.getURL().concat(common.getDELIVERY_LIST_ENDPOINT()),
                null,

                new Response.Listener<JSONObject>() {

                    @SuppressLint("LongLogTag")
                    @SneakyThrows
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("Response {}", response.toString());

                        deliveryList.clear();

                        JSONArray jsonArray = response.getJSONArray(common.getJSON_PREFIX());

                        for (int i = 0; i < jsonArray.length(); i++) {

                            try {

                                Delivery delivery = new Delivery();
                                JSONObject obj = jsonArray.getJSONObject(i);

                                delivery.setId(obj.getInt(deliveryConstant.getORDER_ID()));
                                delivery.setOrderId(obj.getInt(deliveryConstant.getORDER_ID()));
                                delivery.setDeliveryStatus(obj.getString(deliveryConstant.getDELIVERY_STATUS()));
                                delivery.setDriverName(obj.getString(deliveryConstant.getDRIVER_NAME()));
                                delivery.setVehicleNo(obj.getString(deliveryConstant.getVEHICLE_NO()));
                                delivery.setContactNumber(obj.getString(deliveryConstant.getCONTACT_NO()));
                                delivery.setEstimatedDeliveryDateTime(obj.getString(deliveryConstant.getESTIMATED_DELIVERY_DATE_TIME()));
                                delivery.setNote(obj.getString(deliveryConstant.getNOTE()));

                                deliveryList.add(delivery);

                            } catch (JSONException e) {

                                e.printStackTrace();
                            }

                        }
                        Log.i("filtered deliveryList response {}", deliveryList.toString());

                        deliveryAdapter.notifyDataSetChanged();
                        return;
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