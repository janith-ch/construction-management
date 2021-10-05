package com.example.application_mobile.adapter;

import android.annotation.SuppressLint;
import android.app.Fragment;
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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application_mobile.R;
import com.example.application_mobile.fragment.quotation.CreateQuotations;
import com.example.application_mobile.model.Quotation;
import com.google.android.material.navigation.NavigationView;

import java.util.List;


public class QuotationAdapter extends RecyclerView.Adapter<QuotationAdapter.QuotationViewHolder> {

    private List<Quotation> listdata;
    Button button;
    private Context context;

    public QuotationAdapter(List<Quotation> listdata, Context context) {
        this.context = context;
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public QuotationAdapter.QuotationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.single_view_quotation_layout, parent, false);
        QuotationViewHolder viewHolder = new QuotationViewHolder(listItem);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuotationViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final Quotation quotation = listdata.get(position);

        holder.textView_1.setText(listdata.get(position).getMaterialName());
        holder.textView_2.setText(String.valueOf(listdata.get(position).getQuantity()).concat(" "+listdata.get(position).getQuantityType()));
        holder.textView_3.setText(listdata.get(position).getToDate());
        holder.textView_4.setText(listdata.get(position).getFromDate());
        holder.textView_5.setText(listdata.get(position).getDepartmentStatus());
        holder.textView_6.setText(String.valueOf("A00"+listdata.get(position).getSiteId()));
        holder.textView_7.setText(listdata.get(position).getSiteName());
        holder.textView_8.setText(listdata.get(position).getSiteLocation());
        holder.cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "click on item: " + quotation.getSiteId(), Toast.LENGTH_LONG).show();
            }
        });

        CreateQuotations createQuotations = new CreateQuotations();

        holder.button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                bundle.putString("qcMaterial", listdata.get(position).getMaterialName());
                bundle.putString("qcMaterialType", listdata.get(position).getQuantityType());
                bundle.putString("qcFromDate", listdata.get(position).getFromDate());
                bundle.putString("qcToDate", listdata.get(position).getToDate());
                bundle.putInt("qcOrderId",listdata.get(position).getId());

                createQuotations.setArguments(bundle);

                FragmentTransaction mFragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                mFragmentTransaction.replace(R.id.flFragment, createQuotations).commit();


            }
        });

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class QuotationViewHolder extends RecyclerView.ViewHolder {

        public TextView textView_1, textView_2, textView_3, textView_4, textView_5, textView_6, textView_7, textView_8;
        public Button button;
        //  public RelativeLayout relativeLayout;
        public CardView cardView;

        public QuotationViewHolder(View itemView) {

            super(itemView);
            this.textView_1 = (TextView) itemView.findViewById(R.id.q_meterial);
            this.textView_2 = (TextView) itemView.findViewById(R.id.q_material_type);
            this.textView_3 = (TextView) itemView.findViewById(R.id.q_to_date);
            this.textView_4 = (TextView) itemView.findViewById(R.id.q_from_date);
            this.textView_5 = (TextView) itemView.findViewById(R.id.q_dep_status);
            this.textView_6 = (TextView) itemView.findViewById(R.id.q_site_id);
            this.textView_7 = (TextView) itemView.findViewById(R.id.q_site_name);
            this.textView_8 = (TextView) itemView.findViewById(R.id.q_site_location);
            this.button = (Button) itemView.findViewById(R.id.fill_quotation_form);

            cardView = itemView.findViewById(R.id.quotation_list_card_view);

        }
    }
}
