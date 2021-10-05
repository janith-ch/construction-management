package com.example.application_mobile.fragment.quotation;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.application_mobile.R;
import com.example.application_mobile.fragment.order.CreateOrderStepTwo;
import com.example.application_mobile.fragment.order.OrderSummary;


public class CreateQuotations extends Fragment {

    private java.util.Calendar calendar = java.util.Calendar.getInstance();
    private EditText from_date, to_date,estimated_unit_cost,estimated_quantity;
    private TextView qc_sum_material,qc_sum_material_type,qc_sum_from_date,qc_sum_to_date;
    private int day, month, year;
    private Button button;

    public CreateQuotations() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_quotations, container, false);
        from_date = (EditText) view.findViewById(R.id.qc_from_date);
        to_date = (EditText) view.findViewById(R.id.qc_to_date);
        button = view.findViewById(R.id.cq_button);
        estimated_unit_cost = view.findViewById(R.id.create_quotation_unit_cost);
        estimated_quantity = view.findViewById(R.id.create_quotation_quantity);

        //oder details mapping for quotation
        qc_sum_material = view.findViewById(R.id.qc_osum_material);
        qc_sum_material_type = view.findViewById(R.id.qc_osum_materialType);
        qc_sum_from_date = view.findViewById(R.id.qc_osum_date_from);
        qc_sum_to_date = view.findViewById(R.id.qc_osum_date_to);

        //set data to data-bundle
        Bundle bundle = getArguments();
        qc_sum_material.setText(bundle.getString("qcMaterial"));
        qc_sum_material_type.setText(bundle.getString("qcMaterialType"));
        qc_sum_from_date.setText(bundle.getString("qcFromDate"));
        qc_sum_to_date.setText(bundle.getString("qcToDate"));

        //get ongoing date
        day = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);

        //call data picker
        to_date.setOnClickListener(toDateClickListener);
        from_date.setOnClickListener(fromDateClickListener);

        QuotationSummary quotationSummary = new QuotationSummary();

        //create quotation button handling
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //bind values to data bundle
                Bundle bundle = getArguments();

                bundle.putString("estimatedFromDate", from_date.getText().toString());
                bundle.putString("estimatedToDate", to_date.getText().toString());
                bundle.putString("estimatedUnitCost", estimated_unit_cost.getText().toString());
                bundle.putString("estimatedQuantity", estimated_quantity.getText().toString());

                System.out.println("====================>"+bundle);
                quotationSummary.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, quotationSummary).commit();
            }
        });

        Quotations quotations = new Quotations();

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {

            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, quotations).commit();
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        return view;
    }

    //from-date mapping
    View.OnClickListener fromDateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            fromDateDialog();
        }
    };

    public void fromDateDialog() {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                from_date.setText(dayOfMonth + "/" + monthOfYear + "/" + year);

            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), listener, year, month, day);
        datePickerDialog.show();
    }

    //to-date mapping
    View.OnClickListener toDateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            toDateDialog();
        }
    };

    public void toDateDialog() {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                to_date.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), listener, year, month, day);
        datePickerDialog.show();
    }
}