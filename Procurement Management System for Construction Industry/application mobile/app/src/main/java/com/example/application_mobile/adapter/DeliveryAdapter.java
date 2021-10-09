package com.example.application_mobile.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application_mobile.R;
import com.example.application_mobile.constant.DeliveryConstant;
import com.example.application_mobile.model.Delivery;

import java.util.List;


public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder> {

    private List<Delivery> listdata;
    private Context context;
    private DeliveryConstant deliveryConstant = new DeliveryConstant();

    public DeliveryAdapter(List<Delivery> listdata, Context context) {
        this.context = context;
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public DeliveryAdapter.DeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.single_view_delivery_layout, parent, false);
        DeliveryViewHolder viewHolder = new DeliveryViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.textView_1.setText(deliveryConstant.getOR().concat(String.valueOf(listdata.get(position).getId())));
        holder.textView_2.setText(listdata.get(position).getDriverName());
        holder.textView_3.setText(listdata.get(position).getContactNumber());
        holder.textView_4.setText(listdata.get(position).getDeliveryStatus());
        holder.textView_5.setText(String.valueOf(listdata.get(position).getVehicleNo()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class DeliveryViewHolder extends RecyclerView.ViewHolder {

        public TextView textView_1, textView_2, textView_3, textView_4,textView_5;
        public Button button;
        public CardView cardView;

        public DeliveryViewHolder(View itemView) {

            super(itemView);
            this.textView_1 = (TextView) itemView.findViewById(R.id.delivery_ref);
            this.textView_2 = (TextView) itemView.findViewById(R.id.delivery_material);
            this.textView_3 = (TextView) itemView.findViewById(R.id.delivery_quantity);
            this.textView_4 = (TextView) itemView.findViewById(R.id.delivery_status);
            this.textView_5 = (TextView) itemView.findViewById(R.id.text_vehicle_no);

            cardView = itemView.findViewById(R.id.delivery_list_card_view);

        }
    }
}
