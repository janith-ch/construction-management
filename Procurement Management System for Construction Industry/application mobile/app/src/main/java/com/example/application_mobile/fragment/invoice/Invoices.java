package com.example.application_mobile.fragment.invoice;

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
import com.example.application_mobile.adapter.InvoiceAdapter;
import com.example.application_mobile.constant.Common;
import com.example.application_mobile.model.Invoice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;

public class Invoices extends Fragment {

    private RecyclerView recyclerView;
    private InvoiceAdapter invoiceAdapter;
    private List<Invoice> invoiceList = new ArrayList<>();
    private Button createInvoice;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private Common common = new Common();

    public Invoices() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getInvoicesDetails();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invoices, container, false);
        recyclerView = view.findViewById(R.id.invoice_recycle_view);
        createInvoice = view.findViewById(R.id.invoice_create);
        invoiceAdapter = new InvoiceAdapter(invoiceList, getContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(invoiceAdapter);

        CreateInvoice createInvoices = new CreateInvoice();

        createInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, createInvoices).commit();
            }
        });

        return view;
    }

    private void getInvoicesDetails() {

        //RequestQueue initialized
        requestQueue = Volley.newRequestQueue(getContext());

        jsonObjectRequest = new JsonObjectRequest(

                Request.Method.GET,
                common.getURL().concat(common.getINVOICE_LIST_ENDPOINT()),
                null,

                new Response.Listener<JSONObject>() {

                    @SneakyThrows
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("Response is", response.toString());
                        invoiceList.clear();
                        JSONArray jsonArray = response.getJSONArray("dataBundle");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                Invoice invoice = new Invoice();
                                JSONObject obj = jsonArray.getJSONObject(i);

                                invoice.setId(obj.getString("id"));
                                invoice.setMaterial(obj.getString("materialName"));
                                invoice.setOrderId(obj.getInt("orderId"));
                                invoice.setQuantity(obj.getString("quantity"));
                                invoice.setTotalPrice(obj.getString("total"));
                                invoice.setSite(obj.getString("siteName"));


                                invoiceList.add(invoice);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        invoiceAdapter.notifyDataSetChanged();
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