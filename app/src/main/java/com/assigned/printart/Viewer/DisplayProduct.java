package com.assigned.printart.Viewer;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assigned.printart.InterfacePack.ItClickListen;
import com.assigned.printart.R;

public class DisplayProduct extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView view1,view2,view3;
    public ImageView img1;
    public ItClickListen listener;
    public DisplayProduct(@NonNull View itemView) {
        super(itemView);

        img1=(ImageView)itemView.findViewById(R.id.product_image);
        view1=(TextView)itemView.findViewById(R.id.product_name);
        view2=(TextView) itemView.findViewById(R.id.product_description);
        view3=(TextView) itemView.findViewById(R.id.product_price);
    }
    public void clickinglistener(ItClickListen listener)
    {
        this.listener=listener;
    }
    @Override
    public void onClick(View view) {
listener.onClick(view,getAdapterPosition(),false );
    }
}
