package com.assigned.printart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    RadioButton withf,withoutf;
    ProductAdapter aptr;
    ProductFirebaseViewer productFirebaseViewer;

    DatabaseReference Productbanner;
    private List<ProductBanners> productBannersList= new ArrayList<>();
    private Timer timer;
    private int currentposition=0;
    private DatabaseReference ProductDetailsRef;
    TextView t1,t2,t3,t4,t5,t6;
    private RadioGroup radioGroup;
    private TextView Pname,PDes,POprice,PSprice,Sellers;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;
    FirebaseRecyclerAdapter<DisplayCategory, CategoryViewHolder>adapter;
    FirebaseRecyclerAdapter<NestedCategory, NestedCategoryViewHolder>adapter1;
    int x=10,y,z;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
      DisplayID=getIntent().getStringExtra("Display");
      CategoryID=getIntent().getStringExtra("Category");
      t1=(TextView)findViewById(R.id.type1);
      radioGroup=(RadioGroup)findViewById(R.id.radios);
      t2=(TextView)findViewById(R.id.type2);
      t3=(TextView)findViewById(R.id.type3);
      t4=(TextView)findViewById(R.id.type4);
      t5=(TextView)findViewById(R.id.type5);
      t6=(TextView)findViewById(R.id.type6);

        productFirebaseViewer=this;
        manager=new LinearLayoutManager(this);

        Productbanner=FirebaseDatabase.getInstance().getReference().child("ShowingProducts");


        ProductDetailsRef=FirebaseDatabase.getInstance().getReference().child("ShowingProducts");

        products=(ViewPager)findViewById(R.id.productviewerpage);
        loadimages();
        products.setPageTransformer(true,new Transformer());
        Pname=(TextView)findViewById(R.id.ProductName);
        PDes=(TextView)findViewById(R.id.ProductDescription);
        POprice=(TextView)findViewById(R.id.originalPrice);
        PSprice=(TextView)findViewById(R.id.sellingprice);
        Sellers=(TextView)findViewById(R.id.sell);
        withf=(RadioButton)findViewById(R.id.withF);
        withoutf=(RadioButton)findViewById(R.id.withoutF);

        if(CategoryID.equals("01"))
        {
            radioGroup.setVisibility(View.VISIBLE);
        }
        recyclerView=(RecyclerView)findViewById(R.id.recyclerViewin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        Productbanner .child(CategoryID).child(DisplayID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
            if(dataSnapshot.exists()) {



            Pname.setText(dataSnapshot.child("Pame").getValue().toString());
            PDes.setText(dataSnapshot.child("Pdes").getValue().toString());
                POprice.setText("₹"+dataSnapshot.child("PpriceO").getValue().toString());
                y=Integer.parseInt(dataSnapshot.child("Psp").getValue().toString());
                z=Integer.parseInt(dataSnapshot.child("PpriceO").getValue().toString());
                POprice.setPaintFlags(POprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                PSprice.setText("₹"+dataSnapshot.child("Psp").getValue().toString());
                Sellers.setText(dataSnapshot.child("Seller").getValue().toString());
                t1.setText(dataSnapshot.child("type").getValue().toString());
                t2.setText(dataSnapshot.child("type1").getValue().toString());
                t3.setText(dataSnapshot.child("type2").getValue().toString());
                t4.setText(dataSnapshot.child("type3").getValue().toString());
                t5.setText(dataSnapshot.child("type4").getValue().toString());
                t6.setText(dataSnapshot.child("type5").getValue().toString());



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

    public void OnRadioChecked(View view)
    {
boolean isSelected = ((AppCompatRadioButton)view).isChecked();
switch (view.getId())
{
    case R.id.withF:
    {
        withFrame();
        break;
    }
    case R.id.withoutF:
    {
        WithoutFrame();
        break;

    }
}
    }

    private void WithoutFrame()
    {
    if(x>300)
    {
        PSprice.setText("₹" + String.valueOf(y));
        POprice.setText("₹" + String.valueOf(z));
        x=10;
    }
    }

    private void withFrame()
    {
        if(x<300)
        {
            x = 300;
            x = x + Integer.parseInt(PSprice.getText().toString().substring(1));
            //Toast.makeText(this, ""+x, Toast.LENGTH_SHORT).show();
            PSprice.setText("₹" + String.valueOf(x));
            POprice.setText("₹" + String.valueOf(x + 250));
        }
    }


}
