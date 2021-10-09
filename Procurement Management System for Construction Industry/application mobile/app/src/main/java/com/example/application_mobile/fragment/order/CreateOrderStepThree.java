package com.example.application_mobile.fragment.order;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.application_mobile.R;
import com.example.application_mobile.constant.Common;
import com.example.application_mobile.constant.OrderConstant;
import com.kofigyan.stateprogressbar.StateProgressBar;


public class CreateOrderStepThree extends Fragment {

    private StateProgressBar stateProgressBar;
    private Button button;
    private java.util.Calendar calendar = java.util.Calendar.getInstance();
    private EditText from_date, to_date;
    private int day, month, year;
    private Common common = new Common();
    private OrderConstant orderConstant = new OrderConstant();

    public CreateOrderStepThree() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_order_step_three, container, false);
        stateProgressBar = view.findViewById(R.id.your_state_progress_bar_three);
        stateProgressBar.setStateDescriptionData(orderConstant.getDescriptionData());
        button = view.findViewById(R.id.button_create_final);

        from_date = (EditText) view.findViewById(R.id.co_frome_date);
        to_date = (EditText) view.findViewById(R.id.co_to_date);

        day = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);

        to_date.setOnClickListener(toDateClickListener);
        from_date.setOnClickListener(fromDateClickListener);

        OrderSummary orderSummary = new OrderSummary();
        CreateOrderStepTwo createOrderStepTwo = new CreateOrderStepTwo();

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //bind values to data bundle
                Bundle bundle = getArguments();

                bundle.putString(orderConstant.getFROM_DATE(), String.valueOf(from_date.getText()));
                bundle.putString(orderConstant.getTo_DATE(), String.valueOf(to_date.getText()));

                orderSummary.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, orderSummary).commit();
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {

            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, createOrderStepTwo).commit();
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        return view;
    }

    //from date mapping
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

    //to date mapping
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