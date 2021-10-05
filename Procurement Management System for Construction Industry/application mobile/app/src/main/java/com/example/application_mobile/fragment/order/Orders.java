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
import com.example.application_mobile.constant.Common;
import com.example.application_mobile.model.Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;


public class Orders extends Fragment {

   private Button button;
   private List<Order> oderList = new ArrayList<>();
   private CreateOrderStepOne createOrder = new CreateOrderStepOne();
   private Common common = new Common();
   private RecyclerView recyclerView;
   private OrderAdapter adapter;
   private RequestQueue requestQueue;
   private JsonObjectRequest jsonObjectRequest;

    public Orders() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getOrderDetails();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        recyclerView = view.findViewById(R.id.order_recycle_view);
        adapter = new OrderAdapter(oderList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        button = view.findViewById(R.id.order_create);
        button.setOnClickListener(new View.OnClickListener() {

            Bundle bundle = new Bundle();
            @Override
            public void onClick(View v) {
                createOrder.setArguments(bundle);
               getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, createOrder).commit();
           }
        });

        return view;


    }

    private void getOrderDetails() {

        //RequestQueue initialized
        requestQueue = Volley.newRequestQueue(getContext());

        jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                common.getURL().concat("orders"),
                null,
                new Response.Listener<JSONObject>() {
                    @SneakyThrows
                   @Override
                    public void onResponse(JSONObject response) {

                        Log.e("Response is", response.toString());
                         oderList.clear();
                        JSONArray jsonArray = response.getJSONArray("dataBundle");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                Order order = new Order();
                                JSONObject obj = jsonArray.getJSONObject(i);

                                order.setId(obj.getInt("id"));
                                order.setMaterialName(obj.getString("materialName"));
                                order.setOrderDate(obj.getString("orderDate"));
                                order.setDeliveryDate(obj.getString("deliveryDate"));
                                order.setQuanitiyType(obj.getString("quanitiyType"));
                                order.setQuantity(obj.getDouble("quantity"));

                                if((obj.getInt("isApprove"))==1){
                                    order.setDepartmentStatus("APPROVED");
                                }else if((obj.getInt("isApprove"))==2){
                                    order.setDepartmentStatus("PENDING");
                                }else {
                                    order.setDepartmentStatus("REJECTED");
                                }

                                oderList.add(order);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        System.out.println("=================>"+oderList);

                        adapter.notifyDataSetChanged();
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