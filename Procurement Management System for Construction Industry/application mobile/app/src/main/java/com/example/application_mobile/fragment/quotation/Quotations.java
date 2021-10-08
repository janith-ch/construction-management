package com.example.application_mobile.fragment.quotation;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.application_mobile.R;
import com.example.application_mobile.adapter.QuotationAdapter;
import com.example.application_mobile.constant.Common;
import com.example.application_mobile.constant.QuotationConstant;
import com.example.application_mobile.model.Quotation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;

public class Quotations extends Fragment {

   private Common common = new Common();
   private QuotationConstant quotationConstant = new QuotationConstant();
   private RecyclerView recyclerView;
   private QuotationAdapter quotationAdapter;
   private List<Quotation> quotationList = new ArrayList<>();
   private RequestQueue requestQueue;
   private JsonObjectRequest jsonObjectRequest;

    public Quotations() {

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getQuotationDetails();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quotations, container, false);
        recyclerView = view.findViewById(R.id.quotations_recycle_view);
        quotationAdapter =  new QuotationAdapter(quotationList,getContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(quotationAdapter);

        return view;
    }

    private void getQuotationDetails() {

        //RequestQueue initialized
        requestQueue = Volley.newRequestQueue(getContext());

        jsonObjectRequest = new JsonObjectRequest(

                Request.Method.GET,
                common.getURL().concat(common.getQUOTATION_LIST_ENDPOINT()),
                null,

                new Response.Listener<JSONObject>() {

                    @SuppressLint("LongLogTag")
                    @SneakyThrows
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("Response {} ", response.toString());
                         quotationList.clear();
                        JSONArray jsonArray = response.getJSONArray(common.getJSON_PREFIX());
                        for (int i = common.getZERO(); i < jsonArray.length(); i++) {
                            try {
                                Quotation quotation = new Quotation();
                                JSONObject obj = jsonArray.getJSONObject(i);

                                quotation.setId(obj.getInt(quotationConstant.getID()));
                                quotation.setMaterialName(obj.getString(quotationConstant.getMATERIAL_NAME()));
                                quotation.setFromDate(obj.getString(quotationConstant.getORDER_DATE()));
                                quotation.setToDate(obj.getString(quotationConstant.getDELIVERY_DATE()));
                                quotation.setSiteLocation(obj.getString(quotationConstant.getADDRESS()));
                                quotation.setQuantityType(obj.getString(quotationConstant.getQUANTITY_TYPE()));
                                quotation.setQuantity(obj.getDouble(quotationConstant.getQUANTITY()));
                                quotation.setSiteId(obj.getInt(quotationConstant.getSITE_ID()));
                                quotation.setSiteName(obj.getString(quotationConstant.getSITE_NAME()));
                                quotation.setQuotationStatus(obj.getInt(quotationConstant.getQUOTATION_STATUS()));

                                if((obj.getInt(quotationConstant.getIS_APPROVED()))==common.getONE()){

                                    quotation.setDepartmentStatus(common.getAPPROVED());

                                }else if((obj.getInt(quotationConstant.getIS_APPROVED()))==common.getTWO()){

                                    quotation.setDepartmentStatus(common.getPENDING());

                                }else {

                                    quotation.setDepartmentStatus(common.getREJECTED());

                                }

                                quotationList.add(quotation);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                         Log.i("filtered quotation response {} ",quotationList.toString());
                        quotationAdapter.notifyDataSetChanged();
                        return;
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Response {} ", error.toString());
            }
        }

        );
        requestQueue.add(jsonObjectRequest);

    }

}