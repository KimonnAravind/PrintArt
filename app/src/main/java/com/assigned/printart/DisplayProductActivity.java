package com.assigned.printart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.TypeEvaluator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.assigned.printart.Model.DisplayCategory;
import com.assigned.printart.Model.DisplayProducts;
import com.assigned.printart.Paper.PaperStore;
import com.assigned.printart.Viewer.CategoryViewHolder;
import com.assigned.printart.Viewer.DisplayProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import io.paperdb.Paper;

public class DisplayProductActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    public RecyclerView recyclerViewdisplay;
    RecyclerView.LayoutManager layoutManager;
    GridLayoutManager gridLayoutManager;
    private String TypeID;
    Query sorting;
    private String CategoryID;
    private DatabaseReference DisplayReference,wishListReference;
    String str1;
    FirebaseRecyclerAdapter<DisplayProducts, DisplayProductViewHolder>adapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_product);
        str1=Paper.book().read(PaperStore.UserLoginCode);
        Toast.makeText(this, ""+str1, Toast.LENGTH_SHORT).show();

        recyclerViewdisplay=(RecyclerView)findViewById(R.id.recyclerViewDisplay);
        recyclerViewdisplay.setHasFixedSize(false);
        gridLayoutManager=new GridLayoutManager(getApplicationContext(),2);
        CategoryID=getIntent().getStringExtra("Category");
        TypeID=getIntent().getStringExtra("TypeID");
        DisplayReference= FirebaseDatabase.getInstance().getReference().child("ShowingProducts").child(CategoryID);
        wishListReference=FirebaseDatabase.getInstance().getReference().child("WishList").child("Kimonn");
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
            protected void onBindViewHolder(@NonNull final DisplayProductViewHolder holder, int position, @NonNull final DisplayProducts model) {
                holder.Pname.setText(model.getPame());
                holder.PSPrice.setText("₹"+model.getPsp());
                holder.POPrice.setText(model.getPpriceO()+" ");
                holder.Pdes.setText(model.getPdes());
                holder.POPrice.setPaintFlags(holder.POPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                Picasso.get().load(model.getPro()).into(holder.imgv);

                holder.locl_buttons.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        copyrecord(DisplayReference.child(model.getProID()),wishListReference.child(model.getProID()));







                            /*HashMap<String,Object> WishList = new HashMap<>();
                            WishList.put("PID",model.getProID());
                            wishListReference.child(str1).updateChildren(WishList)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(DisplayProductActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });*/





                    }
                });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(DisplayProductActivity.this, ShowDetailsActivity.class);
                       intent.putExtra("Display", model.getProID());
                        intent.putExtra("Category", CategoryID);
                        Toast.makeText(DisplayProductActivity.this, ""+model.getPame(), Toast.LENGTH_SHORT).show();
                       //startActivity(intent);

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

    private void copyrecord(DatabaseReference displayReference, final DatabaseReference wishListReference) {

        displayReference.addListenerForSingleValueEvent(new ValueEventListener()  {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                wishListReference.setValue(dataSnapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("Sending", "Success!");
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        int click=menuItem.getItemId();
        return false;
    }
}
