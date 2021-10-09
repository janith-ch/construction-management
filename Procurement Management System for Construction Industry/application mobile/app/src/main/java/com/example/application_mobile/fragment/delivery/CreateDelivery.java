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
import com.example.application_mobile.constant.DeliveryConstant;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.SneakyThrows;


public class CreateDelivery extends Fragment {

    private TextView materialName, quantity, toDate, fromDate, purchaseRef, siteName, siteLocation, manager;
    private EditText personName, personNo, vehicleNo, deliveryNote;
    private Button createDelivery;
    private Common common = new Common();
    private DeliveryList deliveryList = new DeliveryList();
    private DeliveryConstant deliveryConstant = new DeliveryConstant();
    private JsonObjectRequest jsonObjectRequest;
    private RequestQueue requestQueue;

    public CreateDelivery() {
        // default constructor
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
        materialName.setText(bundle.getString(deliveryConstant.getMATERIAL_NAME()));
        quantity.setText(bundle.getString(deliveryConstant.getQUANTITY()).concat(" " + bundle.getString(deliveryConstant.getQUANTITY_TYPE())));
        toDate.setText(bundle.getString(deliveryConstant.getTo_DATE()));
        fromDate.setText(bundle.getString(deliveryConstant.getFROM_DATE()));
        purchaseRef.setText(bundle.getString(deliveryConstant.getPUR_ID()));
        siteName.setText(bundle.getString(deliveryConstant.getSITE_NAME()));
        siteLocation.setText(bundle.getString(deliveryConstant.getSITE_ADDRESS()));
        manager.setText(deliveryConstant.getMR().concat(bundle.getString(deliveryConstant.getSITE_MANAGER_NAME())));

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

        jsonBody.put(deliveryConstant.getORDER_ID(), bundle.getString(deliveryConstant.getPUR_ID()));
        jsonBody.put(deliveryConstant.getDRIVER_NAME(), personName.getText().toString());
        jsonBody.put(deliveryConstant.getVEHICLE_NO(), vehicleNo.getText().toString());
        jsonBody.put(deliveryConstant.getCONTACT_NO(), personNo.getText().toString());
        jsonBody.put(deliveryConstant.getESTIMATED_DELIVERY_DATE_TIME(), bundle.getString(deliveryConstant.getFROM_DATE()));
        jsonBody.put(deliveryConstant.getNOTE(), deliveryNote.getText().toString());

        jsonObjectRequest = new JsonObjectRequest(

                Request.Method.POST,
                common.getURL().concat(common.getCREATE_DELIVERY_ENDPOINT()),
                jsonBody,

                new Response.Listener<JSONObject>() {

                    @SneakyThrows
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("Response {} ", response.toString());

                        if (response.getBoolean(common.getIS_SUCCESS())) {

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