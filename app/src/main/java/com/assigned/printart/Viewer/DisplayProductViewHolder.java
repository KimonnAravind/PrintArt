package com.assigned.printart.Viewer;

        import android.view.View;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.assigned.printart.R;


public class DisplayProductViewHolder extends RecyclerView.ViewHolder
{
    public static ImageView imgv,wish;
    public static TextView Pname,POPrice,PSPrice,Pdes;

    public DisplayProductViewHolder(@NonNull View itemView) {
        super(itemView);
        imgv=itemView.findViewById(R.id.images);
        wish=itemView.findViewById(R.id.wish);

        Pname=itemView.findViewById(R.id.name);
        POPrice=itemView.findViewById(R.id.productorigialPrice);
        PSPrice=itemView.findViewById(R.id.productofferPrice);
        Pdes=itemView.findViewById(R.id.descriptioN);

    }
}
