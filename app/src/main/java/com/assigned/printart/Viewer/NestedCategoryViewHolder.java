package com.assigned.printart.Viewer;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assigned.printart.InterfacePack.NestedRecycler;
import com.assigned.printart.R;

public class NestedCategoryViewHolder extends RecyclerView.ViewHolder
{

    public static TextView ProductDescription;
    public static ImageView IgmV;
    public static NestedRecycler nestedRecycler;

    public NestedCategoryViewHolder(@NonNull View itemView)
    {
        super(itemView);

        IgmV=itemView.findViewById(R.id.imageView);


    }
}
