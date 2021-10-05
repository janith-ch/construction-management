package com.example.application_mobile.fragment.delivery;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.application_mobile.R;
import com.example.application_mobile.constant.Common;
import com.example.application_mobile.fragment.order.Orders;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.SneakyThrows;


public class CreateDelivery extends Fragment {

    TextView materialName, quantity, toDate, fromDate, purchaseRef, siteName, siteLocation, manager;
    EditText personName, personNo, vehicleNo, deliveryNote;
    Button createDelivery;
    Common common = new Common();
    DeliveryList deliveryList = new DeliveryList();
    private JsonObjectRequest jsonObjectRequest;
    private RequestQueue requestQueue;

    public CreateDelivery() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_delivery, container, false);
        materialName = view.findViewById(R.id.cd_meaterialName);
        quantity = view.findViewById(R.id.cd_quantity);
        toDate = view.findViewById(R.id.cd_toDate);
        fromDate = view.findViewById(R.id.cd_fromDate);
        purchaseRef = view.findViewById(R.id.cd_ref);
        siteName = view.findViewById(R.id.cd_siteName);
        siteLocation = view.findViewById(R.id.cd_siteLocation);
        manager = view.findViewById(R.id.cd_managerName);
        personName = view.findViewById(R.id.delivery_person);
        personNo = view.findViewById(R.id.delivery_person_no);
        vehicleNo = view.findViewById(R.id.vehicle_no);
        deliveryNote = view.findViewById(R.id.delivery_note);
        createDelivery = view.findViewById(R.id.create_del_button);


        Bundle bundle = getArguments();
        materialName.setText(bundle.getString("MaterialName"));
        quantity.setText(bundle.getString("quantity").concat(" " + bundle.getString("quantityType")));
        toDate.setText(bundle.getString("toDate"));
        fromDate.setText(bundle.getString("fromDate"));
        purchaseRef.setText(bundle.getString("purId"));
        siteName.setText(bundle.getString("siteName"));
        siteLocation.setText(bundle.getString("siteAddress"));
        manager.setText("Mr.".concat(bundle.getString("siteManagerName")));

        createDelivery.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View v) {
                createDelivery();
            }
        });

        return view;
    }

    public void createDelivery() throws JSONException {

        Bundle bundle = getArguments();

        requestQueue = Volley.newRequestQueue(getContext());

        JSONObject jsonBody = new JSONObject();

        jsonBody.put("orderId", bundle.getString("purId"));
        jsonBody.put("driverName", personName.getText().toString());
        jsonBody.put("vehicleNo", vehicleNo.getText().toString());
        jsonBody.put("contactNumber", personNo.getText().toString());
        jsonBody.put("estimatedDeliveryDateTime",bundle.getString("fromDate"));
        jsonBody.put("note", deliveryNote.getText().toString());

        jsonObjectRequest = new JsonObjectRequest(

                Request.Method.POST,
                common.getURL().concat(common.getCREATE_DELIVERY_ENDPOINT()),
                jsonBody,

                new Response.Listener<JSONObject>() {

                    @SneakyThrows
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("Response is", response.toString());

                        if (response.getBoolean("isSuccess")) {

                            Toast.makeText(getContext(), "Delivery Created", Toast.LENGTH_LONG).show();

                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, deliveryList).commit();

                        } else {

                            Toast.makeText(getContext(), "Delivery Failed", Toast.LENGTH_LONG).show();
                        }

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