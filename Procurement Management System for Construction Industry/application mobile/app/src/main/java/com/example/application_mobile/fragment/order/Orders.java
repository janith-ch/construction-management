package com.example.application_mobile.fragment.order;

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
import com.example.application_mobile.constant.OrderConstant;
import com.example.application_mobile.model.Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;


public class Orders extends Fragment {
    //declare variables
    private Button button;
    private List<Order> oderList = new ArrayList<>();
    private CreateOrderStepOne createOrder = new CreateOrderStepOne();
    private Common common = new Common();
    private OrderConstant orderConstant = new OrderConstant();
    private RecyclerView recyclerView;
    private OrderAdapter adapter;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;


    public Orders() {
        //create default constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //call get oder details method for get oder list
        getOrderDetails();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //find the id with relevant component
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        recyclerView = view.findViewById(R.id.order_recycle_view);
        button = view.findViewById(R.id.order_create);

        adapter = new OrderAdapter(oderList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //set adepter to recycle view
        recyclerView.setAdapter(adapter);

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
                common.getURL().concat(common.getORDER_LIST_ENDPOINT()),
                null,

                new Response.Listener<JSONObject>() {

                    @SuppressLint("LongLogTag")
                    @SneakyThrows
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("Response is {} ", response.toString());

                        oderList.clear();

                        JSONArray jsonArray = response.getJSONArray(common.getJSON_PREFIX());

                        //read list data
                        for (int i = common.getZERO(); i < jsonArray.length(); i++) {

                            try {

                                Order order = new Order();
                                JSONObject obj = jsonArray.getJSONObject(i);

                                //set date to object
                                order.setId(obj.getInt(orderConstant.getID()));
                                order.setMaterialName(obj.getString(orderConstant.getMATERIAL_NAME()));
                                order.setOrderDate(obj.getString(orderConstant.getORDER_DATE()));
                                order.setDeliveryDate(obj.getString(orderConstant.getDELIVERY_DATE()));
                                order.setQuanitiyType(obj.getString(orderConstant.getQUANTITY_TYPE()));
                                order.setQuantity(obj.getDouble(orderConstant.getQUANTITY()));

                                //check order status
                                if ((obj.getInt(orderConstant.getIS_APPROVED())) == common.getONE()) {

                                    order.setDepartmentStatus(common.getAPPROVED());

                                } else if ((obj.getInt(orderConstant.getIS_APPROVED())) == common.getTWO()) {

                                    order.setDepartmentStatus(common.getPENDING());

                                } else {

                                    order.setDepartmentStatus(common.getREJECTED());

                                }

                                oderList.add(order);

                            } catch (JSONException e) {

                                e.printStackTrace();
                            }

                        }
                        Log.i("filtered order response {}", oderList.toString());

                        adapter.notifyDataSetChanged();
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