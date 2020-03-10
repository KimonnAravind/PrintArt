package com.assigned.printart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.assigned.printart.Model.DisplayProducts;
import com.assigned.printart.Paper.PaperStore;
import com.assigned.printart.Viewer.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import io.paperdb.Paper;

    public class CartActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private TextView deliveryAdd;
    Button changeAdd, updateadd;
    private EditText deliveryless,pinc;
    TextView total;
    int xx=0,yy=0;
        DatabaseReference databaseReference, AddressReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        deliveryAdd = (TextView) findViewById(R.id.deliveryA);
        deliveryless = (EditText) findViewById(R.id.deliveryB);
        pinc=(EditText)findViewById(R.id.deliveryPin);
        changeAdd = (Button) findViewById(R.id.changeAdds);
        updateadd = (Button) findViewById(R.id.addorub);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        total=(TextView)findViewById(R.id.total);


  String     users = Paper.book().read(PaperStore.UserLoginID);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserCart")
                .child(users);
        AddressReference = FirebaseDatabase.getInstance().getReference().child("EndUsers").child(users);
        FirebaseRecyclerOptions<DisplayProducts> options =
                new FirebaseRecyclerOptions.Builder<DisplayProducts>().setQuery(databaseReference, DisplayProducts.class)
                        .build();
        FirebaseRecyclerAdapter<DisplayProducts, CartViewHolder> adapter = new FirebaseRecyclerAdapter<DisplayProducts, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CartViewHolder holder, final int position, @NonNull final DisplayProducts model)
            {
                Picasso.get().load(model.getPro()).into(holder.imgvs);
                holder.d.setText(model.getPdes());
                holder.t.setText(model.getPame());
                holder.o.setText("₹"+model.getPpriceO()+" ");
                holder.o.setPaintFlags(holder.o.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.s.setText("₹"+model.getPsp());
                holder.sn.setText(model.getSeller());
                holder.tt.setText(model.getQuantity());

                xx=Integer.parseInt(model.getQuantity());
                yy=yy+xx;
                Toast.makeText(CartActivity.this, ""+yy, Toast.LENGTH_SHORT).show();

             holder.itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v)
                 {

                     Intent intent = new Intent(CartActivity.this, ShowDetailsActivity.class);
                     intent.putExtra("Display", model.getProID());
                     intent.putExtra("Category", model.getCatt());
                     startActivity(intent);
                 }
             });


            }



            @Override
            public long getItemId(int position) {
                Log.e("one",""+position);

                return position;
            }

            @Override
            public int getItemViewType(int position) {
                Log.e("one+",""+position);
                return position;
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartlistdesign, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }

        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }

    @Override
    protected void onStart() {
        super.onStart();
        AddressReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Address").exists()) {

                    deliveryAdd.setVisibility(View.VISIBLE);
                    changeAdd.setVisibility(View.VISIBLE);
                    deliveryAdd.setText("Deliver to: "+""+dataSnapshot.child("Name").getValue().toString()+" "
                            + dataSnapshot.child("Address").getValue().toString()
                            +"-"+dataSnapshot.child("Pincode").getValue().toString());
                    changeAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deliveryless.setVisibility(View.VISIBLE);
                            updateadd.setVisibility(View.VISIBLE);
                            deliveryAdd.setVisibility(View.INVISIBLE);
                            pinc.setVisibility(View.VISIBLE);
                            changeAdd.setVisibility(View.INVISIBLE);
                        }
                    });
                } else {
                    deliveryless.setVisibility(View.VISIBLE);
                    updateadd.setVisibility(View.VISIBLE);
                    pinc.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        updateadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!deliveryless.getText().toString().isEmpty()&&!pinc.getText().toString().isEmpty())
                {
                    HashMap<String,Object> DeliveryMap= new HashMap<>();
                    DeliveryMap.put("Address",deliveryless.getText().toString());
                    DeliveryMap.put("Pincode",pinc.getText().toString());

                    AddressReference.updateChildren(DeliveryMap);
                    Toast.makeText(CartActivity.this, "Address updated successfully", Toast.LENGTH_SHORT).show();
                    deliveryless.setVisibility(View.INVISIBLE);
                    updateadd.setVisibility(View.INVISIBLE);
                    pinc.setVisibility(View.INVISIBLE);
                    deliveryAdd.setVisibility(View.VISIBLE);
                    changeAdd.setVisibility(View.VISIBLE);

                    AddressReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            deliveryAdd.setText("Deliver to: "+""+dataSnapshot.child("Name").getValue().toString()+" "
                                    + dataSnapshot.child("Address").getValue().toString()
                                    +"-"+dataSnapshot.child("Pincode").getValue().toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else
                {
                    Toast.makeText(CartActivity.this, "Please fill both the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, ""+parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "close", Toast.LENGTH_SHORT).show();
    }
}