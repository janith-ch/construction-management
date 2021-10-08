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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.application_mobile.R;
import com.example.application_mobile.adapter.ReceiveOrdersAdapter;
import com.example.application_mobile.constant.Common;
import com.example.application_mobile.constant.OrderConstant;
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
    private OrderConstant orderConstant = new OrderConstant();
    private RecyclerView recyclerView;
    private ReceiveOrdersAdapter receiveOrdersAdapter;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;


    public ReceiveOrders() {
        // constructor
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

                        Log.i("Response {} ", response.toString());

                        receiveOrderList.clear();

                        JSONArray jsonArray = response.getJSONArray(common.getJSON_PREFIX());

                        for (int i = common.getZERO(); i < jsonArray.length(); i++) {

                            try {

                                ReceiveOrder receiveOrder = new ReceiveOrder();
                                JSONObject obj = jsonArray.getJSONObject(i);

                                receiveOrder.setId(obj.getInt(orderConstant.getID()));
                                receiveOrder.setOrderDate(obj.getString(orderConstant.getORDER_DATE()));
                                receiveOrder.setDeliveryDate(obj.getString(orderConstant.getDELIVERY_DATE()));
                                receiveOrder.setMaterialName(obj.getString(orderConstant.getMATERIAL_NAME()));
                                receiveOrder.setQuanitiyType(obj.getString(orderConstant.getQUANTITY_TYPE()));
                                receiveOrder.setQuantity(obj.getDouble(orderConstant.getQUANTITY()));
                                receiveOrder.setSiteId(obj.getInt(orderConstant.getSITE_ID()));
                                receiveOrder.setSiteName(obj.getString(orderConstant.getSITE_NAME()));
                                receiveOrder.setAddress(obj.getString(orderConstant.getADDRESS()));
                                receiveOrder.setSiteMangerFirstName(obj.getString(orderConstant.getSITE_MANAGER_FIRST_NAME()));

                                if((obj.getInt(orderConstant.getIS_APPROVED()))==common.getONE()){

                                    receiveOrder.setDepartmentStatus(common.getAPPROVED());

                                }else if((obj.getInt(orderConstant.getIS_APPROVED()))==common.getTWO()){

                                    receiveOrder.setDepartmentStatus(common.getPENDING());

                                }else {

                                    receiveOrder.setDepartmentStatus(common.getREJECTED());

                                }

                                receiveOrderList.add(receiveOrder);

                            } catch (JSONException e) {

                                e.printStackTrace();
                            }

                        }
                        Log.i("filtered list {} ",receiveOrderList.toString());

                        receiveOrdersAdapter.notifyDataSetChanged();
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