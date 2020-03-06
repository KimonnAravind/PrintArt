package com.assigned.printart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.assigned.printart.Model.DisplayProducts;
import com.assigned.printart.Paper.PaperStore;
import com.assigned.printart.Viewer.DisplayProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class CartActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private TextView deliveryAdd;
    DatabaseReference databaseReference,AddressReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        deliveryAdd=(TextView)findViewById(R.id.deliveryA);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        String users= Paper.book().read(PaperStore.UserLoginID);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("UserCart")
                .child(users);
        AddressReference= FirebaseDatabase.getInstance().getReference().child("EndUsers").child(users);
        FirebaseRecyclerOptions<DisplayProducts> options=
                new FirebaseRecyclerOptions.Builder<DisplayProducts>().setQuery(databaseReference, DisplayProducts.class)
                        .build();
        FirebaseRecyclerAdapter<DisplayProducts, DisplayProductViewHolder> adapter =new FirebaseRecyclerAdapter<DisplayProducts, DisplayProductViewHolder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull DisplayProductViewHolder holder, int position, @NonNull DisplayProducts model) {
                Picasso.get().load(model.getPro()).into(holder.imgv);
            }
            @Override
            public long getItemId(int position) {
                return position;
            }
            @Override
            public int getItemViewType(int position) {
                return position;
            }
            @NonNull
            @Override
            public DisplayProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartlistdesign,parent,false);
                DisplayProductViewHolder holder= new DisplayProductViewHolder(view);
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
                if(dataSnapshot.child("Address").exists())
                {


                deliveryAdd.setText("Deliver to: "+dataSnapshot.child("Address").getValue().toString());
                }
                else
                {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}