package com.example.application_mobile.fragment.quotation;

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
import com.example.application_mobile.constant.QuotationConstant;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.SneakyThrows;


public class QuotationSummary extends Fragment {

    private TextView material, materialType, estimatedUnitCost, estimatedFromDate, estimatedToDate, estimatedCost;
    private Button confirm,cancel;
    private Common common = new Common();
    private Quotations quotations = new Quotations();
    private JsonObjectRequest jsonObjectRequest;
    private QuotationConstant quotationConstant = new QuotationConstant();

    private RequestQueue requestQueue;


    public QuotationSummary() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quotation_summary, container, false);
        material = view.findViewById(R.id.qc_sum_material);
        materialType = view.findViewById(R.id.qc_sum_material_quntity);
        estimatedUnitCost = view.findViewById(R.id.qc_sum_estimate_unit);
        estimatedFromDate = view.findViewById(R.id.qc_sum_estimated_date_from);
        estimatedToDate = view.findViewById(R.id.qc_sum_estimated_date_to);
        estimatedCost = view.findViewById(R.id.qc_sum_estimated_cost);
        cancel = view.findViewById(R.id.qsum_cancel);
        confirm= view.findViewById(R.id.qsum_confirm);

        Bundle bundle = getArguments();
        material.setText(bundle.getString(quotationConstant.getMATERIAL_NAME()));
        materialType.setText(bundle.getString(quotationConstant.getQUANTITY_TYPE()));
        estimatedUnitCost.setText(bundle.getString(quotationConstant.getESTIMATED_UNIT_COST()));
        estimatedFromDate.setText(bundle.getString(quotationConstant.getESTIMATED_FROM_DATE()));
        estimatedToDate.setText(bundle.getString(quotationConstant.getESTIMATED_TO_DATE()));

        int unit_cost = Integer.parseInt(bundle.getString(quotationConstant.getESTIMATED_UNIT_COST()));
        int units = Integer.parseInt(bundle.getString(quotationConstant.getESTIMATED_QUANTITY()));

        estimatedCost.setText(common.getRS().concat(String.valueOf(unit_cost*units)).concat(common.getPOINTS()));



        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {

            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Toast.makeText(getContext(), "Quotation Canceled", Toast.LENGTH_LONG).show();

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, quotations).commit();
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);


        confirm.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View v) {
                createQuotation();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Quotation Canceled", Toast.LENGTH_LONG).show();

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, quotations).commit();
            }
        });


        return view;
    }

    public void createQuotation() throws JSONException {

        Bundle bundle = getArguments();

        requestQueue = Volley.newRequestQueue(getContext());

        JSONObject jsonBody = new JSONObject();

        jsonBody.put(quotationConstant.getUNIT_COST(), Double.parseDouble(bundle.getString(quotationConstant.getESTIMATED_UNIT_COST())));
        jsonBody.put(quotationConstant.getQUANTITY_Q(), Double.parseDouble(bundle.getString(quotationConstant.getESTIMATED_QUANTITY())));
        jsonBody.put(quotationConstant.getVALID_LAST_DATE(), bundle.getString(quotationConstant.getESTIMATED_FROM_DATE()));
        jsonBody.put(quotationConstant.getORDER_ID(), bundle.getInt(quotationConstant.getORDER_ID()));

       Log.i("request json {} ",jsonBody.toString());

        jsonObjectRequest = new JsonObjectRequest(

                Request.Method.POST,
                common.getURL().concat(common.getCREATE_QUOTATION_ENDPOINT()),
                jsonBody,

                new Response.Listener<JSONObject>() {

                    @SneakyThrows
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("Response is", response.toString());

                        if (response.getBoolean(common.getIS_SUCCESS())) {

                            Toast.makeText(getContext(), "Quotation Created", Toast.LENGTH_LONG).show();

                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, quotations).commit();

                        } else {

                            Toast.makeText(getContext(), "Quotation Failed", Toast.LENGTH_LONG).show();
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