package com.example.application_mobile.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application_mobile.R;
import com.example.application_mobile.model.Invoice;
import com.example.application_mobile.model.Order;

import java.util.ArrayList;
import java.util.List;


public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> {

    private List<Invoice> listdata;
    private Context context;

    public InvoiceAdapter(List<Invoice> listdata, Context context) {
        this.context = context;
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public InvoiceAdapter.InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.single_view_invoice_layout, parent, false);
        InvoiceViewHolder invoiceviewHolder = new InvoiceViewHolder(listItem);

        return invoiceviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceAdapter.InvoiceViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final List<Order> oderData = new ArrayList<>();

        holder.textView_1.setText(("INV00".concat(listdata.get(position).getId())));
        holder.textView_2.setText("PU00".concat(String.valueOf(listdata.get(position).getOrderId())));
        holder.textView_3.setText(listdata.get(position).getSite());
        holder.textView_4.setText( listdata.get(position).getLocation());
        holder.textView_5.setText(listdata.get(position).getMaterial());
        holder.textView_6.setText(String.valueOf(listdata.get(position).getQuantity()));
        holder.textView_7.setText(listdata.get(position).getCreatedDate());
        holder.textView_8.setText("RS.".concat(listdata.get(position).getTotalPrice()).concat(".00"));
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

    public static class InvoiceViewHolder extends RecyclerView.ViewHolder {

        public TextView textView_1, textView_2,textView_3, textView_4,textView_5, textView_6,textView_7,textView_8;
        public CardView cardView;

        public InvoiceViewHolder(View itemView) {

            super(itemView);
            this.textView_1 = (TextView) itemView.findViewById(R.id.i_id);
            this.textView_2 = (TextView) itemView.findViewById(R.id.i_por);
            this.textView_3 = (TextView) itemView.findViewById(R.id.i_site);
            this.textView_4 = (TextView) itemView.findViewById(R.id.i_location);
            this.textView_5 = (TextView) itemView.findViewById(R.id.i_material);
            this.textView_6 = (TextView) itemView.findViewById(R.id.i_qunntity);
            this.textView_7 = (TextView) itemView.findViewById(R.id.i_createdDate);
            this.textView_8 = (TextView) itemView.findViewById(R.id.i_totalAmount);
            cardView = itemView.findViewById(R.id.invoice_list_card_view);

        }
    }
}