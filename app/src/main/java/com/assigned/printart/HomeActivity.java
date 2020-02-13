package com.assigned.printart;

import android.content.Intent;
import android.os.Bundle;

import com.assigned.printart.Adapter.Adapter;
import com.assigned.printart.FirebListen.FirebaseViewer;
import com.assigned.printart.Model.Banners;
import com.assigned.printart.Model.DisplayCategory;
import com.assigned.printart.Model.Banners;
import com.assigned.printart.Model.NestedCategory;
import com.assigned.printart.Transform.Transformer;
import com.assigned.printart.Viewer.CategoryViewHolder;
import com.assigned.printart.Viewer.NestedCategoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeActivity extends AppCompatActivity implements FirebaseViewer{

    private AppBarConfiguration mAppBarConfiguration;
    ViewPager viewPager,viewPager2;
    Adapter myadapter;
    FirebaseViewer firebaseViewer;

    DatabaseReference reference;
    DatabaseReference banner;
     RecyclerView recyclerView;
    FirebaseRecyclerAdapter<DisplayCategory, CategoryViewHolder>adapter;
    FirebaseRecyclerAdapter<NestedCategory, NestedCategoryViewHolder>adapter1;
    RecyclerView.LayoutManager manager;
    /*RecyclerView.LayoutManager layoutManager, layoutManager1;*/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        banner=FirebaseDatabase.getInstance().getReference().child("Banner");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        manager=new LinearLayoutManager(this);
        reference=FirebaseDatabase.getInstance().getReference("ProductCategory");
        recyclerView=findViewById(R.id.recyclerViewer);
        recyclerView.setLayoutManager(manager);
        firebaseViewer=this;

        loadbanners();
        viewPager = (ViewPager)findViewById(R.id.vp);
        viewPager2 = (ViewPager)findViewById(R.id.vsp);
        viewPager.setPageTransformer(true,new Transformer());

        viewPager2.setPageTransformer(true,new Transformer());

        Random random = new Random();
        int randomNumber = random.nextInt(4) ;
        Query sorting;
        Log.e("Printing","Prints"+randomNumber);
        switch (randomNumber)
        {
            case 0:
            {
                sorting= reference.orderByChild("CountPost");
                break;
            }

            case 1:
            {
                sorting=reference;
                break;
            }

            case 2:
            {
                sorting= reference.orderByChild("CountPost1");
                break;
            }

            case 3:
            {
                sorting= reference.orderByChild("CountPost2");
                break;
            }
            default:
            {
                sorting=reference;
            }
        }


        FirebaseRecyclerOptions<DisplayCategory> options = new FirebaseRecyclerOptions.Builder<DisplayCategory>()
                .setQuery(sorting,DisplayCategory.class).build();

        adapter= new FirebaseRecyclerAdapter<DisplayCategory, CategoryViewHolder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull CategoryViewHolder holder, int position, @NonNull final DisplayCategory displayCategory)
            {
            CategoryViewHolder.CategoryName.setText(displayCategory.getCategoryName());

            FirebaseRecyclerOptions<NestedCategory> options1= new FirebaseRecyclerOptions.Builder<NestedCategory>()
                    .setQuery(reference.child(displayCategory.getCategoryID()).child("About"),NestedCategory.class)
                    .build();
            adapter1= new FirebaseRecyclerAdapter<NestedCategory, NestedCategoryViewHolder>(options1) {
                @Override
                protected void onBindViewHolder(@NonNull NestedCategoryViewHolder holder, int position, @NonNull final NestedCategory nestedCategory) {
                    Picasso.get().load(nestedCategory.getProductDescription()).into(holder.IgmV);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent= new Intent(HomeActivity.this,DisplayProductActivity.class);
                         intent.putExtra("Category", displayCategory.getCategoryID());
                            startActivity(intent);
                        }
                    });
                }
                @NonNull
                @Override
                public NestedCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                    View v2=LayoutInflater.from(getBaseContext()).inflate
                            (R.layout.nesteddisplay,parent,false);
                    return new NestedCategoryViewHolder(v2);
                }
            };
                adapter1.startListening();
                adapter1.notifyDataSetChanged();
                CategoryViewHolder.category_recyclerView.setAdapter(adapter1);

            }

            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v1=LayoutInflater.from(getBaseContext()).inflate(R.layout.displayproducts,parent,false);
                return new CategoryViewHolder(v1);
            }
        };
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }
    private void loadbanners()
    {
    banner.addListenerForSingleValueEvent(new ValueEventListener() {
        List<Banners> bannersList= new ArrayList<>();
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot bannersnapshot:dataSnapshot.getChildren())
            bannersList.add(bannersnapshot.getValue(Banners.class));
            firebaseViewer.Loadsuccess(bannersList);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        firebaseViewer.Loadfailed(databaseError.getMessage());
        }
    });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public void Loadsuccess(List<Banners> bannersList)
    {
        myadapter= new Adapter(this,bannersList);
        viewPager.setAdapter(myadapter);
        viewPager2.setAdapter(myadapter);
    }
    @Override
    public void Loadfailed(String string)
    {
        Toast.makeText(this, ""+string, Toast.LENGTH_SHORT).show();
    }
}
