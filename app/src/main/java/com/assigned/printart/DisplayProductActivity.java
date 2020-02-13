package com.assigned.printart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.TypeEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.assigned.printart.Model.DisplayCategory;
import com.assigned.printart.Model.DisplayProducts;
import com.assigned.printart.Viewer.CategoryViewHolder;
import com.assigned.printart.Viewer.DisplayProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class DisplayProductActivity extends AppCompatActivity
{
    public RecyclerView recyclerViewdisplay;
    RecyclerView.LayoutManager layoutManager;
    GridLayoutManager gridLayoutManager;
    private String TypeID;
    Query sorting;
    private String CategoryID;
    private DatabaseReference DisplayReference;
    FirebaseRecyclerAdapter<DisplayProducts, DisplayProductViewHolder>adapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_product);
        recyclerViewdisplay=(RecyclerView)findViewById(R.id.recyclerViewDisplay);
        recyclerViewdisplay.setHasFixedSize(false);
        gridLayoutManager=new GridLayoutManager(getApplicationContext(),2);
        CategoryID=getIntent().getStringExtra("Category");
        TypeID=getIntent().getStringExtra("TypeID");

        DisplayReference= FirebaseDatabase.getInstance().getReference().child("ShowingProducts").child(CategoryID);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewdisplay.setLayoutManager(gridLayoutManager);

        if(TypeID.equals("01"))
        {
            sorting= DisplayReference.orderByChild("type");
        }
        else if(TypeID.equals("02"))
        {
            sorting= DisplayReference.orderByChild("type1");
        }


    }


    @Override
    protected void onStart()
    {
        super.onStart();



        FirebaseRecyclerOptions<DisplayProducts> options=
                new FirebaseRecyclerOptions.Builder<DisplayProducts>().setQuery(sorting, DisplayProducts.class)
                        .build();

         adapter2=new FirebaseRecyclerAdapter<DisplayProducts, DisplayProductViewHolder>(options)
        {

            @Override
            protected void onBindViewHolder(@NonNull DisplayProductViewHolder holder, int position, @NonNull final DisplayProducts model) {
                Picasso.get().load(model.getPro()).into(holder.imgv);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(DisplayProductActivity.this, ShowDetailsActivity.class);
                       intent.putExtra("Display", model.getProID());
                        intent.putExtra("Category", CategoryID);
                       startActivity(intent);

                        Log.e("CATEGORY ID IS: ",CategoryID);
                        Log.e("Display ID IS: ", model.getProID());

                    }
                });

            }

            @NonNull
            @Override
            public DisplayProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.displayproductactivitydesign,parent,false);
                DisplayProductViewHolder holder= new DisplayProductViewHolder(view);

                return holder;


            }

        };

        recyclerViewdisplay.setAdapter(adapter2);
        adapter2.startListening();


    }
}
