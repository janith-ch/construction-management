package com.example.application_mobile.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application_mobile.R;
import com.example.application_mobile.constant.OrderConstant;
import com.example.application_mobile.fragment.delivery.CreateDelivery;
import com.example.application_mobile.fragment.order.ReceiveOrders;
import com.example.application_mobile.model.Quotation;
import com.example.application_mobile.model.ReceiveOrder;

import java.util.List;

public class ReceiveOrdersAdapter extends RecyclerView.Adapter<ReceiveOrdersAdapter.ReceiveOrdersViewHolder> {

    private List<ReceiveOrder> listdata;
    private OrderConstant orderConstant = new OrderConstant();
    private Context context;

    // RecyclerView recyclerView;
    public ReceiveOrdersAdapter(List<ReceiveOrder> listdata, Context context) {
        this.context = context;
        this.listdata = listdata;

    }

    @NonNull
    @Override
    public ReceiveOrdersAdapter.ReceiveOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View listItem = layoutInflater.inflate(R.layout.single_view_receive_order_layout, parent, false);

        ReceiveOrdersViewHolder receiveOrdersViewHolder = new ReceiveOrdersViewHolder(listItem);
        return receiveOrdersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiveOrdersViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final ReceiveOrder receiveOrder = listdata.get(position);

        holder.textView_1.setText(String.valueOf(listdata.get(position).getId()));
        holder.textView_2.setText(listdata.get(position).getMaterialName());
        holder.textView_3.setText(String.valueOf(listdata.get(position).getQuantity())
                .concat(" "+(listdata.get(position).getQuanitiyType())));
        holder.textView_4.setText(listdata.get(position).getOrderDate());
        holder.textView_5.setText(listdata.get(position).getDeliveryDate());
        holder.textView_6.setText(String.valueOf(listdata.get(position).getSiteName()));
        holder.textView_7.setText(listdata.get(position).getAddress());
        holder.textView_8.setText("Mr."+listdata.get(position).getSiteMangerFirstName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "click on item: " + receiveOrder.getSiteId(), Toast.LENGTH_LONG).show();
            }
        });

        CreateDelivery createDelivery = new CreateDelivery();

        holder.button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                bundle.putString(orderConstant.getPUR_ID(), String.valueOf(listdata.get(position).getId()));
                bundle.putString(orderConstant.getMATERIAL_NAME(), listdata.get(position).getMaterialName());
                bundle.putString(orderConstant.getQUANTITY(),String.valueOf(listdata.get(position).getQuantity()));
                bundle.putString(orderConstant.getQUANTITY__TYPE(),listdata.get(position).getQuanitiyType());
                bundle.putString(orderConstant.getSITE_NAME(),String.valueOf(listdata.get(position).getSiteName()));
                bundle.putString(orderConstant.getSITE_ADDRESS(),listdata.get(position).getAddress());
                bundle.putString(orderConstant.getSITE_MANAGER_NAME(),listdata.get(position).getSiteMangerFirstName());
                bundle.putString(orderConstant.getFROM_DATE(), listdata.get(position).getOrderDate());
                bundle.putString(orderConstant.getTo_DATE(), listdata.get(position).getDeliveryDate());

                createDelivery.setArguments(bundle);

                FragmentTransaction mFragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                mFragmentTransaction.replace(R.id.flFragment, createDelivery).commit();


            }
        });

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ReceiveOrdersViewHolder extends RecyclerView.ViewHolder {

        public TextView textView_1, textView_2, textView_3, textView_4, textView_5, textView_6, textView_7, textView_8;
        public Button button;
        //  public RelativeLayout relativeLayout;
        public CardView cardView;

        public ReceiveOrdersViewHolder(View itemView) {

            super(itemView);
            this.textView_1 = (TextView) itemView.findViewById(R.id.pod_ref);
            this.textView_2 = (TextView) itemView.findViewById(R.id.pod_meterial);
            this.textView_3 = (TextView) itemView.findViewById(R.id.pod_quantity);
            this.textView_4 = (TextView) itemView.findViewById(R.id.pod_from_date);
            this.textView_5 = (TextView) itemView.findViewById(R.id.pod_to_date);
            this.textView_6 = (TextView) itemView.findViewById(R.id.pod_site_name);
            this.textView_7 = (TextView) itemView.findViewById(R.id.pod_site_location);
            this.textView_8 = (TextView) itemView.findViewById(R.id.pod_manager);
            this.button = (Button) itemView.findViewById(R.id.pod_create_delivery);

            cardView = itemView.findViewById(R.id.purchase_order_list_card_view);

        }
    }
}

