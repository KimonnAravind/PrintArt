package com.assigned.printart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ShowDetailsActivity extends AppCompatActivity
{
    private ImageView ImagefromFB;
    String DisplayID,CategoryID;
    private DatabaseReference ProductDetailsRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        DisplayID=getIntent().getStringExtra("Display");
        CategoryID=getIntent().getStringExtra("Category");

        ImagefromFB=(ImageView)findViewById(R.id.Imagefromfb);

        ProductDetailsRef= FirebaseDatabase.getInstance().getReference().child("ShowingProducts").child(CategoryID)
                .child(DisplayID);
        ProductDetailsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String imageUrl = dataSnapshot.child("pro").getValue().toString();
                    Picasso.get().load(imageUrl).into(ImagefromFB);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
