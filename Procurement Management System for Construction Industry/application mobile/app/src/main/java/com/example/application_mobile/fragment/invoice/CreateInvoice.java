package com.example.application_mobile.fragment.invoice;
import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.application_mobile.R;
import com.example.application_mobile.constant.Common;
import com.example.application_mobile.fragment.delivery.DeliveryList;
import com.example.application_mobile.model.Delivery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;

public class CreateInvoice extends Fragment {
    private Spinner order_ref_dropdown;
    private Button createInvoice;
    EditText delivered_quantity, feedback;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private Common common = new Common();
    private String oderRef;
    private int siteId, materialId;
    private DeliveryList deliveryList_1 = new DeliveryList();
    private List<Delivery> deliveryList = new ArrayList<>();

    public CreateInvoice() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_invoice, container, false);
        order_ref_dropdown = view.findViewById(R.id.order_ref);
        createInvoice = view.findViewById(R.id.create_invoice);
        delivered_quantity = view.findViewById(R.id.delivered_quantity);

        getOrderRefDetails();

        createInvoice.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View v) {
                setCreateInvoice();
            }
        });

        return view;
    }

    private void getOrderRefDetails() {

        //RequestQueue initialized
        requestQueue = Volley.newRequestQueue(getContext());

        jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                common.getURL().concat(common.getORDER_REF_DELIVERING()),
                null,
                new Response.Listener<JSONObject>() {

                    @SneakyThrows
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("Response is", response.toString());

                        JSONArray jsonArray = response.getJSONArray("dataBundle");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            try {

                                Delivery delivery = new Delivery();
                                JSONObject obj = jsonArray.getJSONObject(i);

                                delivery.setSiteId(obj.getInt("siteId"));
                                delivery.setMaterialId(obj.getInt("materialId"));
                                delivery.setRefId(obj.getString("id"));

                                deliveryList.add(delivery);

                            } catch (JSONException e) {

                                e.printStackTrace();
                            }
                        }

                        ArrayAdapter<Delivery> deliveryAdapter = new ArrayAdapter<Delivery>(getContext(), android.R.layout.simple_spinner_item, deliveryList);

                        deliveryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

                        order_ref_dropdown.setAdapter(deliveryAdapter);

                        order_ref_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                Delivery selectedRef = (Delivery) order_ref_dropdown.getSelectedItem();

                                materialId = selectedRef.getMaterialId();
                                siteId = selectedRef.getSiteId();
                                oderRef = selectedRef.getRefId();

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

    @SuppressLint("LongLogTag")
    public void setCreateInvoice() throws JSONException {


        requestQueue = Volley.newRequestQueue(getContext());

        JSONObject jsonBody = new JSONObject();

        jsonBody.put("siteId", siteId);
        jsonBody.put("materialId", materialId);
        jsonBody.put("quantity", delivered_quantity.getText().toString());
        jsonBody.put("isApproved", 1);
        jsonBody.put("orderId", Integer.parseInt(oderRef));

       Log.i("Invoice json body {} ",jsonBody.toString());

        jsonObjectRequest = new JsonObjectRequest(

                Request.Method.POST,
                common.getURL().concat(common.getCREATE_QUOTATION_ENDPOINT()),
                jsonBody,

                new Response.Listener<JSONObject>() {

                    @SneakyThrows
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("Response is", response.toString());

                        if (response.getBoolean("isSuccess")) {

                            Toast.makeText(getContext(), "Invoice Created", Toast.LENGTH_LONG).show();

                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, deliveryList_1).commit();

                        } else {

                            Toast.makeText(getContext(), "Invoice Failed", Toast.LENGTH_LONG).show();
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