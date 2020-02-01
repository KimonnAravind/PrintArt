package com.assigned.printart.Viewer;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.assigned.printart.InterfacePack.NestedRecycler;
import com.assigned.printart.R;
public class NestedDisplayViewHolder extends RecyclerView.ViewHolder
{

    public static TextView dataName;
    public static TextView dataId;
    public static TextView dataAge;
    public static NestedRecycler nestedRecycler;

    public NestedDisplayViewHolder(@NonNull View itemView) {
        super(itemView);
        dataId = itemView.findViewById(R.id.data_id);
        dataAge = itemView.findViewById(R.id.data_age);
        dataName = itemView.findViewById(R.id.data_name);


    }
}
