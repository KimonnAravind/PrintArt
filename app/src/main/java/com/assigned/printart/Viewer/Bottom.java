package com.assigned.printart.Viewer;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assigned.printart.InterfacePack.NestedRecycler;
import com.assigned.printart.R;

public class Bottom extends RecyclerView.ViewHolder
{public static TextView ProductDescription;
    public static ImageView pics;
    public static NestedRecycler nestedRecycler;

    public Bottom(@NonNull View itemView) {
        super(itemView);
        pics=itemView.findViewById(R.id.imageView);
    }
}
