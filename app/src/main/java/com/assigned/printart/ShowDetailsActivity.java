package com.assigned.printart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.assigned.printart.Adapter.Adapter;
import com.assigned.printart.Adapter.ProductAdapter;
import com.assigned.printart.FirebListen.FirebaseViewer;
import com.assigned.printart.FirebListen.ProductFirebaseViewer;
import com.assigned.printart.Model.Banners;
import com.assigned.printart.Model.DisplayCategory;
import com.assigned.printart.Model.DisplayProducts;
import com.assigned.printart.Model.NestedCategory;
import com.assigned.printart.Model.ProductBanners;
import com.assigned.printart.Transform.Transformer;
import com.assigned.printart.Viewer.Bottom;
import com.assigned.printart.Viewer.CategoryViewHolder;
import com.assigned.printart.Viewer.NestedCategoryViewHolder;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ShowDetailsActivity extends AppCompatActivity implements ProductFirebaseViewer {
    String DisplayID,CategoryID;
    ViewPager products;
    ProductAdapter aptr;
    ProductFirebaseViewer productFirebaseViewer;

    DatabaseReference Productbanner,TypeDetails;
    private List<ProductBanners> productBannersList= new ArrayList<>();
    private Timer timer;
    private int currentposition=0;
    private DatabaseReference ProductDetailsRef;
    private TextView Pname,PDes,POprice,PSprice,Sellers;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;
    FirebaseRecyclerAdapter<DisplayCategory, CategoryViewHolder>adapter;
    FirebaseRecyclerAdapter<NestedCategory, NestedCategoryViewHolder>adapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
      DisplayID=getIntent().getStringExtra("Display");
      CategoryID=getIntent().getStringExtra("Category");
        productFirebaseViewer=this;
        manager=new LinearLayoutManager(this);

        Productbanner=FirebaseDatabase.getInstance().getReference().child("ShowingProducts");

        TypeDetails=FirebaseDatabase.getInstance().getReference().child("ProductCategory")
                .child(CategoryID);
        ProductDetailsRef=FirebaseDatabase.getInstance().getReference().child("ShowingProducts");

        products=(ViewPager)findViewById(R.id.productviewerpage);
        loadimages();
        products.setPageTransformer(true,new Transformer());
        Pname=(TextView)findViewById(R.id.ProductName);
        PDes=(TextView)findViewById(R.id.ProductDescription);
        POprice=(TextView)findViewById(R.id.originalPrice);
        PSprice=(TextView)findViewById(R.id.sellingprice);
        Sellers=(TextView)findViewById(R.id.sell);


        recyclerView=(RecyclerView)findViewById(R.id.recyclerViewin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        Productbanner .child(CategoryID).child(DisplayID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
            if(dataSnapshot.exists())
            {
                Toast.makeText(ShowDetailsActivity.this, ""+dataSnapshot.child("Psp").getValue().toString(), Toast.LENGTH_SHORT).show();

            Pname.setText(dataSnapshot.child("Pame").getValue().toString());
            PDes.setText(dataSnapshot.child("Pdes").getValue().toString());
                POprice.setText("₹"+dataSnapshot.child("PpriceO").getValue().toString());
                POprice.setPaintFlags(POprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                PSprice.setText("₹"+dataSnapshot.child("Psp").getValue().toString());
                Sellers.setText(dataSnapshot.child("Seller").getValue().toString());
            }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        TypeDetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
            if(dataSnapshot.exists())
            {
                Toast.makeText(ShowDetailsActivity.this, ""+dataSnapshot.child("Type1").getValue().toString(), Toast.LENGTH_SHORT).show();
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();


        FirebaseRecyclerOptions<DisplayProducts> options=
                new FirebaseRecyclerOptions.Builder<DisplayProducts>().setQuery(ProductDetailsRef.child(CategoryID), DisplayProducts.class)
                        .build();
        FirebaseRecyclerAdapter<DisplayProducts, Bottom> optionis=new FirebaseRecyclerAdapter<DisplayProducts, Bottom>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Bottom holder, int position, @NonNull DisplayProducts model) {
                Picasso.get().load(model.getPro()).into(holder.pics);

            }

            @NonNull
            @Override
            public Bottom onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom,parent,false);
               Bottom holder= new Bottom(view);

                return holder;
            }
        };
        recyclerView.setAdapter(optionis);
        optionis.startListening();

    }

    @Override
    public void Loadsuccess(List<ProductBanners> productBannersList)
    {
    aptr=new ProductAdapter(this,productBannersList);
    products.setAdapter(aptr);
    }
    @Override
    public void Loadfailed(String string)
    {

    }
    private void loadimages()
    {
        Productbanner.child(CategoryID).child(DisplayID).child("images").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot productbannersnapshot:dataSnapshot.getChildren())
                    productBannersList.add(productbannersnapshot.getValue(ProductBanners.class));
                productFirebaseViewer.Loadsuccess(productBannersList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                productFirebaseViewer.Loadfailed(databaseError.getMessage());
            }
        });
    }

}
