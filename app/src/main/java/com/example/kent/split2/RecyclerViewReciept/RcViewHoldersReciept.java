package com.example.kent.split2.RecyclerViewReciept;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.kent.split2.R;

public class RcViewHoldersReciept extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
    public TextView mName;
    public TextView mPrice;

    public RcViewHoldersReciept(View itemView) {
        super(itemView);
        mName = itemView.findViewById(R.id.name);
        mPrice = itemView.findViewById(R.id.price);
        CardView cardView = itemView.findViewById(R.id.mCardView);

        itemView.setOnCreateContextMenuListener(this); //REGISTER ONCREATE MENU LISTEN
    }

    private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {

            switch (item.getItemId()) {
                case 1:
                    //Do stuff
                    break;

                case 2:
                    //Do stuff

                    break;
            }
            return true;
        }
    };

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem Edit = menu.add(Menu.NONE, 1, 1, "Edit");
        MenuItem Delete = menu.add(Menu.NONE, 2, 2, "Delete");
        Edit.setOnMenuItemClickListener(onEditMenu);
        Delete.setOnMenuItemClickListener(onEditMenu);

    }

}
