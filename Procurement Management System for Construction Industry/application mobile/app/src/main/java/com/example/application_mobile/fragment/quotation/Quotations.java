package com.example.application_mobile.fragment.quotation;

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
import com.example.application_mobile.adapter.OrderAdapter;
import com.example.application_mobile.adapter.QuotationAdapter;
import com.example.application_mobile.constant.Common;
import com.example.application_mobile.model.Order;
import com.example.application_mobile.model.Quotation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;

public class Quotations extends Fragment {

   private Common common = new Common();
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

                    @SneakyThrows
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("Response is", response.toString());
                         quotationList.clear();
                        JSONArray jsonArray = response.getJSONArray("dataBundle");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                Quotation quotation = new Quotation();
                                JSONObject obj = jsonArray.getJSONObject(i);

                                quotation.setId(obj.getInt("id"));
                                quotation.setMaterialName(obj.getString("materialName"));
                                quotation.setFromDate(obj.getString("orderDate"));
                                quotation.setToDate(obj.getString("deliveryDate"));
                                quotation.setSiteLocation(obj.getString("address"));
                                quotation.setQuantityType(obj.getString("quanitiyType"));
                                quotation.setQuantity(obj.getDouble("quantity"));
                                quotation.setSiteId(obj.getInt("siteId"));
                                quotation.setSiteName(obj.getString("siteName"));

                                if((obj.getInt("isApprove"))==1){

                                    quotation.setDepartmentStatus("APPROVED");

                                }else if((obj.getInt("isApprove"))==2){

                                    quotation.setDepartmentStatus("PENDING");

                                }else {

                                    quotation.setDepartmentStatus("REJECTED");

                                }

                                quotationList.add(quotation);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        quotationAdapter.notifyDataSetChanged();
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