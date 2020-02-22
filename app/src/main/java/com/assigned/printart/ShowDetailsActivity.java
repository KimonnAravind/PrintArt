package com.assigned.printart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
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
import java.util.Timer;
import java.util.TimerTask;

public class ShowDetailsActivity extends AppCompatActivity implements ProductFirebaseViewer {
    String DisplayID,CategoryID;
    ViewPager products;
    ProductAdapter aptr;
    ProductFirebaseViewer productFirebaseViewer;

    DatabaseReference Productbanner;
    private List<ProductBanners> productBannersList= new ArrayList<>();
    private Timer timer;
    private int currentposition=0;
    private DatabaseReference ProductDetailsRef;
    private TextView Pname,PDes,POprice,PSprice,Sellers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
      DisplayID=getIntent().getStringExtra("Display");
      CategoryID=getIntent().getStringExtra("Category");
        productFirebaseViewer=this;
        Productbanner=FirebaseDatabase.getInstance().getReference().child("ShowingProducts")
                .child(CategoryID).child(DisplayID);
        products=(ViewPager)findViewById(R.id.productviewerpage);
        loadimages();
        products.setPageTransformer(true,new Transformer());
        Pname=(TextView)findViewById(R.id.ProductName);
        PDes=(TextView)findViewById(R.id.ProductDescription);
        POprice=(TextView)findViewById(R.id.originalPrice);
        PSprice=(TextView)findViewById(R.id.sellingprice);
        Sellers=(TextView)findViewById(R.id.sell);

        Productbanner.addValueEventListener(new ValueEventListener() {
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
        Productbanner.child("images").addListenerForSingleValueEvent(new ValueEventListener() {

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

private void slideshow()
{
    final Handler handler= new Handler();
    final Runnable runnable= new Runnable()

    {
    @Override
    public void run()
    {    if(currentposition==productBannersList.size())
    {

        currentposition=0;
        products.setCurrentItem(currentposition++,true);
    }
    }

};
timer=new Timer();
timer.schedule(new TimerTask() {
    @Override
    public void run()
    {
    handler.post(runnable);
    }
},25,250);
}

}
