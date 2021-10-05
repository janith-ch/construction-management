package com.example.application_mobile.fragment.order;

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
import com.example.application_mobile.adapter.ReceiveOrdersAdapter;
import com.example.application_mobile.constant.Common;
import com.example.application_mobile.model.Order;
import com.example.application_mobile.model.Quotation;
import com.example.application_mobile.model.ReceiveOrder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;


public class ReceiveOrders extends Fragment {
    private List<ReceiveOrder> receiveOrderList = new ArrayList<>();
    private Common common = new Common();
    private RecyclerView recyclerView;
    private ReceiveOrdersAdapter receiveOrdersAdapter;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;


    public ReceiveOrders() {
        // Required empty public constructor
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getReceivedOrderDetails();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_receive_orders, container, false);
        recyclerView = view.findViewById(R.id.receive_orders_recycle_view);
        receiveOrdersAdapter =  new ReceiveOrdersAdapter(receiveOrderList,getContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(receiveOrdersAdapter);

        return view;
    }


    private void getReceivedOrderDetails() {

        //RequestQueue initialized
        requestQueue = Volley.newRequestQueue(getContext());

        jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                common.getURL().concat(common.getRECEIVE_ODER_ENDPOINT()),
                null,
                new Response.Listener<JSONObject>() {
                    @SneakyThrows
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("Response is", response.toString());
                        receiveOrderList.clear();
                        JSONArray jsonArray = response.getJSONArray("dataBundle");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                ReceiveOrder receiveOrder = new ReceiveOrder();
                                JSONObject obj = jsonArray.getJSONObject(i);

                                receiveOrder.setId(obj.getInt("id"));
                                receiveOrder.setOrderDate(obj.getString("orderDate"));
                                receiveOrder.setDeliveryDate(obj.getString("deliveryDate"));
                                receiveOrder.setMaterialName(obj.getString("materialName"));
                                receiveOrder.setQuanitiyType(obj.getString("quanitiyType"));
                                receiveOrder.setQuantity(obj.getDouble("quantity"));
                                receiveOrder.setSiteId(obj.getInt("siteId"));
                                receiveOrder.setSiteName(obj.getString("siteName"));
                                receiveOrder.setAddress(obj.getString("address"));
                                receiveOrder.setSiteMangerFirstName(obj.getString("siteMangerFirstName"));

                                if((obj.getInt("isApprove"))==1){
                                    receiveOrder.setDepartmentStatus("APPROVED");
                                }else if((obj.getInt("isApprove"))==2){
                                    receiveOrder.setDepartmentStatus("PENDING");
                                }else {
                                    receiveOrder.setDepartmentStatus("REJECTED");
                                }

                                receiveOrderList.add(receiveOrder);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        System.out.println("=================>"+receiveOrderList);

                        receiveOrdersAdapter.notifyDataSetChanged();
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