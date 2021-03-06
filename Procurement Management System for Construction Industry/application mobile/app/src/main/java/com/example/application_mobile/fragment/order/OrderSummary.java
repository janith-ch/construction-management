package com.example.application_mobile.fragment.order;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.application_mobile.constant.OrderConstant;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.SneakyThrows;

public class OrderSummary extends Fragment {

    TextView material, quantity, dateFrom, dateTo, siteCode, siteName, location, totalCost;
    Button confirm,cancel;
    Common common = new Common();
    Orders orders = new Orders();
    private OrderConstant orderConstant = new OrderConstant();
    private JsonObjectRequest jsonObjectRequest;

    private RequestQueue requestQueue;

    public OrderSummary() {
        // default constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        CreateOrderStepThree createOrderStepThree = new CreateOrderStepThree();

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {

            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, createOrderStepThree).commit();
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        View view = inflater.inflate(R.layout.fragment_order_summary, container, false);
        material = view.findViewById(R.id.qc_osum_material);
        quantity = view.findViewById(R.id.qc_osum_materialType);
        dateFrom = view.findViewById(R.id.qc_osum_date_from);
        dateTo = view.findViewById(R.id.qc_osum_date_to);
        siteName = view.findViewById(R.id.sum_site_name);
        siteCode = view.findViewById(R.id.sum_site_code);
        location = view.findViewById(R.id.sum_site_location);
        totalCost = view.findViewById(R.id.qsum_total_cost);
        confirm = view.findViewById(R.id.sum_create_order);
        cancel= view.findViewById(R.id.order_sum_cancel);

        //set values to summary
        Bundle bundle = getArguments();
        material.setText(bundle.getString(orderConstant.getMATERIAL_NAME()));
        quantity.setText(bundle.getString(orderConstant.getQUANTITY()));
        dateFrom.setText(bundle.getString(orderConstant.getFROM_DATE()));
        dateTo.setText(bundle.getString(orderConstant.getTo_DATE()));
        siteName.setText(bundle.getString(orderConstant.getSITE()));
        location.setText(bundle.getString(orderConstant.getLOCATION()));
        String x = String.valueOf(bundle.getInt(orderConstant.getSITE_ID()));
        siteCode.setText("A000".concat(x));

        int qun = Integer.parseInt(bundle.getString(orderConstant.getQUANTITY()));
        Double uCost = bundle.getDouble(orderConstant.getUNIT_COST());

        String totCost = common.getRS().concat(String.valueOf(qun * uCost));
        totalCost.setText(totCost);

        confirm.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View v) {
                createOrder();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Order Canceled", Toast.LENGTH_LONG).show();

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, orders).commit();
            }
        });

        return view;
    }

    public void createOrder() throws JSONException {

        Bundle bundle = getArguments();

        requestQueue = Volley.newRequestQueue(getContext());

        JSONObject jsonBody = new JSONObject();

        jsonBody.put(orderConstant.getMATERIAL_ID(), bundle.getInt(orderConstant.getMATERIAL_ID()));
        jsonBody.put(orderConstant.getQUANTITY(), Double.parseDouble(bundle.getString(orderConstant.getQUANTITY())));
        jsonBody.put(orderConstant.getORDER_DATE(), bundle.getString(orderConstant.getFROM_DATE()));
        jsonBody.put(orderConstant.getDELIVERY_DATE(), bundle.getString(orderConstant.getTo_DATE()));
        jsonBody.put(orderConstant.getSITE_ID(), bundle.getInt(orderConstant.getSITE_ID()));

        jsonObjectRequest = new JsonObjectRequest(

                Request.Method.POST,
                common.getURL().concat(common.getCREATE_ORDER_ENDPOINT()),
                jsonBody,

                new Response.Listener<JSONObject>() {

                    @SneakyThrows
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("Response is", response.toString());

                        if (response.getBoolean(common.getIS_SUCCESS())) {

                            Toast.makeText(getContext(), "Order Created", Toast.LENGTH_LONG).show();

                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, orders).commit();

                        } else {

                            Toast.makeText(getContext(), "Order Failed", Toast.LENGTH_LONG).show();
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