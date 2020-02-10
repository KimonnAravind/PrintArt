package com.assigned.printart.Viewer;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assigned.printart.R;


public class DisplayProductViewHolder extends RecyclerView.ViewHolder
{
    public static ImageView imgv;

    public DisplayProductViewHolder(@NonNull View itemView) {
        super(itemView);
        imgv=itemView.findViewById(R.id.images);
    }
}
