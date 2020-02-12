package com.assigned.printart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.assigned.printart.Adapter.Adapter;
import com.assigned.printart.Adapter.ProductAdapter;
import com.assigned.printart.FirebListen.FirebaseViewer;
import com.assigned.printart.FirebListen.ProductFirebaseViewer;
import com.assigned.printart.Model.Banners;
import com.assigned.printart.Model.ProductBanners;
import com.assigned.printart.Transform.Transformer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ShowDetailsActivity extends AppCompatActivity implements ProductFirebaseViewer {
    String DisplayID,CategoryID;
    ViewPager products;
    ProductAdapter productAdapter;
    ProductFirebaseViewer productFirebaseViewer;
    DatabaseReference Productbanner;
    private DatabaseReference ProductDetailsRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
       DisplayID=getIntent().getStringExtra("Display");
        CategoryID=getIntent().getStringExtra("Category");
        productFirebaseViewer=this;

        products=(ViewPager)findViewById(R.id.productviewerpage);
        products.setPageTransformer(true,new Transformer());
        Productbanner=FirebaseDatabase.getInstance().getReference().child("ShowingProducts")
                .child(CategoryID).child("01");

        //Toast.makeText(this, ""+CategoryID, Toast.LENGTH_SHORT).show();
        /*ProductDetailsRef= FirebaseDatabase.getInstance().getReference().child("ShowingProducts").child(CategoryID)
                .child(DisplayID).child("ProductPic");
        */

        loadimages();


        Log.e("CATEGORY is is", CategoryID); //1
    }

    @Override
    public void Loadsuccess(List<ProductBanners> productBannersList)
    {
    productAdapter=new ProductAdapter(this,productBannersList);
    products.setAdapter(productAdapter);
    }

    @Override
    public void Loadfailed(String string)
    {
        Toast.makeText(this, ""+string, Toast.LENGTH_SHORT).show();
    }

    private void loadimages()
    {

        Productbanner.addListenerForSingleValueEvent(new ValueEventListener() {
            List<ProductBanners> productBannersList= new ArrayList<>();
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
