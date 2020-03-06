package com.assigned.printart.Viewer;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assigned.printart.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CartViewHolder extends RecyclerView.ViewHolder
{
    public static ImageView imgvs;
    public static TextView d,t,o,s,sn;
    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        imgvs=itemView.findViewById(R.id.images);
        d=itemView.findViewById(R.id.desc);
        t=itemView.findViewById(R.id.nme);
        o=itemView.findViewById(R.id.OPrices);
        s=itemView.findViewById(R.id.SellingPrices);
        sn=itemView.findViewById(R.id.SellerName);
    }
}
