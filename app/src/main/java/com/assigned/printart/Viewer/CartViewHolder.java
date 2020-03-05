package com.assigned.printart.Viewer;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assigned.printart.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CartViewHolder extends RecyclerView.ViewHolder {
    public static ImageView imgv;
    public static FloatingActionButton locl_buttons;
    public static TextView Pname,POPrice,PSPrice,Pdes,Seller,number;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        imgv=itemView.findViewById(R.id.images);

    }
}
