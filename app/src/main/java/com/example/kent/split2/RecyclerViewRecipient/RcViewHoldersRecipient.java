package com.example.kent.split2.RecyclerViewRecipient;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.kent.split2.R;

public class RcViewHoldersRecipient extends RecyclerView.ViewHolder{
    public TextView mEmail;
    public CheckBox mRecieve;

    public RcViewHoldersRecipient(View itemView){
        super(itemView);
        mEmail = itemView.findViewById(R.id.email);
        mRecieve = itemView.findViewById(R.id.follow);
    }
}
