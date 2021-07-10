package com.example.stock4u.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stock4u.R;
import com.example.stock4u.entities.Sale;

import java.util.List;

public class AdapterSales extends RecyclerView.Adapter<AdapterSales.MyViewHolder> {
    private List<Sale> saleList;
    private Context context;
    private OnSaleListerner monSaleListener;

    public AdapterSales(List<Sale> saleList, Context context, OnSaleListerner monSaleListener) {
        this.saleList = saleList;
        this.context = context;
        this.monSaleListener = monSaleListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ListItem= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_sale,parent,false);
        return new MyViewHolder(ListItem,monSaleListener) ;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Sale sale = saleList.get(position);
        holder.name.setText(sale.getClient().getNome().toUpperCase());
        holder.totalValue.setText(sale.getTotalValue().toString());
        holder.gain.setText(sale.getGain().toString());
    }

    @Override
    public int getItemCount() {
        return saleList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,gain,totalValue;
        OnSaleListerner onSaleListerner;
        public MyViewHolder(@NonNull View itemView,OnSaleListerner onSaleListerner) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewClientNameListSale);
            gain = itemView.findViewById(R.id.textViewLucroListSale);
            totalValue = itemView.findViewById(R.id.textViewTotalListSale);
            this.onSaleListerner = onSaleListerner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
    public interface OnSaleListerner{
        void onSaleListenerClick(int position);
    }
}
