package com.example.application_mobile.adapter;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application_mobile.R;
import com.example.application_mobile.model.Order;

import java.util.ArrayList;
import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> listdata;

    public OrderAdapter(List<Order> listdata) {
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.single_view_order_layout, parent, false);
        OrderViewHolder viewHolder = new OrderViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final List<Order> oderData = new ArrayList<>();

        holder.textView_1.setText(String.valueOf("A00"+listdata.get(position).getId()));
        holder.textView_2.setText(listdata.get(position).getOrderDate());
        holder.textView_3.setText(listdata.get(position).getMaterialName());
        holder.textView_4.setText(String.valueOf( listdata.get(position).getQuantity()).
                          concat(" "+listdata.get(position).getQuanitiyType()));
        holder.textView_5.setText(listdata.get(position).getDeliveryDate());
        holder.textView_6.setText(listdata.get(position).getOrderDate());
        holder.textView_7.setText(listdata.get(position).getDepartmentStatus());
        holder.cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "click on item: " + oderData.get(position).getId(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        public TextView textView_1, textView_2,textView_3, textView_4,textView_5, textView_6,textView_7;
        public CardView cardView;

        public OrderViewHolder(View itemView) {

            super(itemView);
            this.textView_1 = (TextView) itemView.findViewById(R.id.olv_1_oder_date);
            this.textView_2 = (TextView) itemView.findViewById(R.id.olv_2_id);
            this.textView_3 = (TextView) itemView.findViewById(R.id.olv_6_type);
            this.textView_4 = (TextView) itemView.findViewById(R.id.olv_7_quantity);
            this.textView_5 = (TextView) itemView.findViewById(R.id.olv_3_from);
            this.textView_6 = (TextView) itemView.findViewById(R.id.olv_4_to);
            this.textView_7 = (TextView) itemView.findViewById(R.id.olv_5_status);
            cardView = itemView.findViewById(R.id.oder_list_card_view);

        }
    }
}