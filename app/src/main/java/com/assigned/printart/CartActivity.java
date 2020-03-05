package com.assigned.printart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assigned.printart.Model.DisplayProducts;
import com.assigned.printart.Viewer.DisplayProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CartActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("UserCart")
                .child("7904168617");
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
}