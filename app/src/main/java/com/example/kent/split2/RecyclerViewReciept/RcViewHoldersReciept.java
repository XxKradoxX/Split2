package com.example.kent.split2.RecyclerViewReciept;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.kent.split2.R;

public class RcViewHoldersReciept extends RecyclerView.ViewHolder{
    public TextView mName;
    public TextView mPrice;

    public RcViewHoldersReciept(View itemView){
        super(itemView);
        mName = itemView.findViewById(R.id.name);
        mPrice = itemView.findViewById(R.id.price);

    }
}
