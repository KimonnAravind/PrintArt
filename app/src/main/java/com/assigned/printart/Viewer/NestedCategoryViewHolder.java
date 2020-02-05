package com.assigned.printart.Viewer;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assigned.printart.InterfacePack.NestedRecycler;
import com.assigned.printart.R;

public class NestedCategoryViewHolder extends RecyclerView.ViewHolder
{

    public static TextView ProductName;
    public static TextView ProductPrice;
    public static TextView ProductDescription;
    public static NestedRecycler nestedRecycler;

    public NestedCategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        ProductPrice = itemView.findViewById(R.id.data_id);
        ProductDescription = itemView.findViewById(R.id.data_age);
        ProductName = itemView.findViewById(R.id.data_name);


    }
}
