package com.assigned.printart.Viewer;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assigned.printart.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CartViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnItemSelectedListener {
    public static ImageView imgvs;
    public static TextView d,t,o,s,sn;
    public static Spinner spin;
    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        imgvs=itemView.findViewById(R.id.images);
        d=itemView.findViewById(R.id.desc);
        t=itemView.findViewById(R.id.nme);
        o=itemView.findViewById(R.id.OPrices);
        s=itemView.findViewById(R.id.SellingPrices);
        sn=itemView.findViewById(R.id.SellerName);
        spin=itemView.findViewById(R.id.spining);
        spin.setOnItemSelectedListener(this);


    }

    /**
     * <p>Callback method to be invoked when an item in this view has been
     * selected. This callback is invoked only when the newly selected
     * position is different from the previously selected position or if
     * there was no selected item.</p>
     * <p>
     * Implementers can call getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * @param parent   The AdapterView where the selection happened
     * @param view     The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id       The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        Log.e("Thisis",""+parent.getSelectedItem().toString());

    }

    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
