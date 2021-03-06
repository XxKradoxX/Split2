package com.example.kent.split2.RecyclerViewReciept;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kent.split2.Product;
import com.example.kent.split2.R;

import java.util.List;

public class RCAdapterReciept extends RecyclerView.Adapter<RcViewHoldersReciept>{

    private List<Product> productList;
    private Context context;

    public RCAdapterReciept(List<Product> productList, Context context){
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public RcViewHoldersReciept onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_reciept_item, null);
        RcViewHoldersReciept rcv = new RcViewHoldersReciept(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull final RcViewHoldersReciept holder, final int position) {
        final Product current = productList.get(position);
        System.out.println("dam" + current.getName());
        holder.mName.setText(current.getName());
        holder.mPrice.setText(Double.toString(current.getPrice()));
    }

    @Override
    public int getItemCount() {
        return this.productList.size();
    }
}
