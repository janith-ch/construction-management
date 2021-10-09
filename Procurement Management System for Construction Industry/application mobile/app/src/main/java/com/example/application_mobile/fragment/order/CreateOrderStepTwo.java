package com.example.application_mobile.fragment.order;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.application_mobile.R;
import com.example.application_mobile.constant.Common;
import com.example.application_mobile.constant.OrderConstant;
import com.example.application_mobile.model.Material;
import com.kofigyan.stateprogressbar.StateProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;


public class CreateOrderStepTwo extends Fragment {

    private Common common = new Common();
    private OrderConstant orderConstant = new OrderConstant();
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private StateProgressBar stateProgressBar;
    private Spinner material, quantity_type;
    private EditText quantity;
    private Button button;


    public CreateOrderStepTwo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        CreateOrderStepThree createOrderStepThree = new CreateOrderStepThree();
        CreateOrderStepOne createOrderStepOne = new CreateOrderStepOne();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_order_step_two, container, false);
        stateProgressBar = view.findViewById(R.id.your_state_progress_bar_two);
        material = view.findViewById(R.id.co_material);
        quantity_type = view.findViewById(R.id.co_quantityType);
        quantity = view.findViewById(R.id.co_quantity);
        button = view.findViewById(R.id.create_order_step_02);

        stateProgressBar.setStateDescriptionData(orderConstant.getDescriptionData());

        getMaterialDetails();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = getArguments();

                bundle.putString(orderConstant.getMATERIAL_NAME(), material.getSelectedItem().toString());
                bundle.putString(orderConstant.getQUANTITY_TYPE(), quantity_type.getSelectedItem().toString());
                bundle.putString(orderConstant.getQUANTITY(), quantity.getText().toString());

                createOrderStepThree.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, createOrderStepThree).commit();

            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {

            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, createOrderStepOne).commit();
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        return view;
    }


    private void getMaterialDetails() {

        ArrayAdapter quantityTypeAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1);

        //RequestQueue initialized
        requestQueue = Volley.newRequestQueue(getContext());

        List<Material> materialsList = new ArrayList<>();

        jsonObjectRequest = new JsonObjectRequest(

                Request.Method.GET,
                common.getURL().concat(common.getMATERIAL_LIST_ENDPOINT()),
                null,
                new Response.Listener<JSONObject>() {

                    @SneakyThrows
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("Response is", response.toString());

                        JSONArray jsonArray = response.getJSONArray(common.getJSON_PREFIX());

                        for (int i = common.getZERO(); i < jsonArray.length(); i++) {

                            try {

                                Material materials = new Material();
                                JSONObject obj = jsonArray.getJSONObject(i);

                                materials.setName(obj.getString(orderConstant.getNAME()));
                                materials.setUnitType(obj.getString(orderConstant.getUNIT_TYPE()));
                                materials.setId(obj.getInt(orderConstant.getID()));
                                materials.setUnitCost(obj.getDouble(orderConstant.getUNIT_COST()));
                                materials.setSupplierId(obj.getInt(orderConstant.getSUPPLIER_ID()));

                                quantityTypeAdapter.add(obj.getString(orderConstant.getUNIT_TYPE()));

                                materialsList.add(materials);

                            } catch (JSONException e) {

                                e.printStackTrace();

                            }
                            Log.i("List Data {} ", materialsList.toString());
                        }

                        ArrayAdapter<Material> materialAdapter = new ArrayAdapter<Material>(getContext(), android.R.layout.simple_spinner_item, materialsList);
                        materialAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

                        quantity_type.setAdapter(quantityTypeAdapter);
                        material.setAdapter(materialAdapter);

                        material.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                CreateOrderStepThree createOrderStepThree = new CreateOrderStepThree();
                                Material selectedMaterial = (Material) material.getSelectedItem();

                                Bundle bundle = getArguments();

                                bundle.putInt(orderConstant.getMATERIAL_ID(), selectedMaterial.getId());
                                bundle.putDouble(orderConstant.getUNIT_COST(), selectedMaterial.getUnitCost());

                                createOrderStepThree.setArguments(bundle);
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

}