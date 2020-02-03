package com.assigned.printart;

import android.os.Bundle;

import com.assigned.printart.Adapter.Adapter;
import com.assigned.printart.FirebListen.FirebaseViewer;
import com.assigned.printart.Model.DisplayProducts;
import com.assigned.printart.Model.Movies;
import com.assigned.printart.Model.NestedDisplay;
import com.assigned.printart.Transform.Transformer;
import com.assigned.printart.Viewer.DisplayProductsViewHolder;
import com.assigned.printart.Viewer.NestedDisplayViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.assigned.printart.FirebListen.FirebaseViewer;
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
import com.google.firebase.database.ValueEventListener;

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

public class HomeActivity extends AppCompatActivity implements FirebaseViewer{

    private AppBarConfiguration mAppBarConfiguration;
    private DatabaseReference ProRef;
    ViewPager viewPager;
    Adapter myadapter;
    FirebaseViewer firebaseViewer;
    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference movies;
    private RecyclerView /*recyclerView,*/ recyclerView;
    FirebaseRecyclerAdapter<DisplayProducts, DisplayProductsViewHolder>adapter;
    FirebaseRecyclerAdapter<NestedDisplay, NestedDisplayViewHolder>adapter1;
    RecyclerView.LayoutManager manager;
    /*RecyclerView.LayoutManager layoutManager, layoutManager1;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //ProRef= FirebaseDatabase.getInstance().getReference().child("Products");
        movies=FirebaseDatabase.getInstance().getReference().child("Movies");


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        manager=new LinearLayoutManager(this);
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Category");
        recyclerView=findViewById(R.id.recyclerViewer);
        recyclerView.setLayoutManager(manager);
        firebaseViewer=this;

        loadmovies();
        viewPager = (ViewPager)findViewById(R.id.vp);
        viewPager.setPageTransformer(true,new Transformer());
        FirebaseRecyclerOptions<DisplayProducts> options = new FirebaseRecyclerOptions.Builder<DisplayProducts>()
                .setQuery(reference,DisplayProducts.class).build();

        adapter= new FirebaseRecyclerAdapter<DisplayProducts, DisplayProductsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DisplayProductsViewHolder holder, int position, @NonNull DisplayProducts displayProducts)
            {

            DisplayProductsViewHolder.categoryName.setText(displayProducts.getCategoryName());

            FirebaseRecyclerOptions<NestedDisplay> options1= new FirebaseRecyclerOptions.Builder<NestedDisplay>()
                    .setQuery(reference.child(displayProducts.getCategoryId()).child("data"),NestedDisplay.class)
                    .build();
            adapter1= new FirebaseRecyclerAdapter<NestedDisplay, NestedDisplayViewHolder>(options1) {
                @Override
                protected void onBindViewHolder(@NonNull NestedDisplayViewHolder holder, int position, @NonNull NestedDisplay nestedDisplay) {
                NestedDisplayViewHolder.dataId.setText(nestedDisplay.getDataId());
                    NestedDisplayViewHolder.dataName.setText(nestedDisplay.getDataName());
                    NestedDisplayViewHolder.dataAge.setText(nestedDisplay.getDataAge());

                }

                @NonNull
                @Override
                public NestedDisplayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                    View v2=LayoutInflater.from(getBaseContext()).inflate
                            (R.layout.nesteddisplay,parent,false);
                    return new NestedDisplayViewHolder(v2);
                }
            };

                adapter1.startListening();
                adapter1.notifyDataSetChanged();
                DisplayProductsViewHolder.category_recyclerView.setAdapter(adapter1);

            }

            @NonNull
            @Override
            public DisplayProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v1=LayoutInflater.from(getBaseContext()).inflate(R.layout.displayproducts,parent,false);
                return new DisplayProductsViewHolder(v1);
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
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
       /* recyclerView=(RecyclerView)findViewById(R.id.recyclerViewer);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
*//*
        recyclerView1=(RecyclerView)findViewById(R.id.recyclerViewer1);
        recyclerView1.setHasFixedSize(true);
        layoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView1.setLayoutManager(layoutManager1);*/
    }

    private void loadmovies() {
    movies.addListenerForSingleValueEvent(new ValueEventListener() {
        List<Movies> moviesList= new ArrayList<>();
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot moviesnapshot:dataSnapshot.getChildren())
            moviesList.add(moviesnapshot.getValue(Movies.class));
            firebaseViewer.Loadsuccess(moviesList);
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
    public void Loadsuccess(List<Movies> moviesList)
    {
        myadapter= new Adapter(this,moviesList);
        viewPager.setAdapter(myadapter);

    }

    @Override
    public void Loadfailed(String string)
    {
        Toast.makeText(this, ""+string, Toast.LENGTH_SHORT).show();
    }

    /*@Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Products> opt=
                new FirebaseRecyclerOptions.Builder<Products>().setQuery
                        (ProRef,Products.class).build();

        FirebaseRecyclerAdapter<Products, DisplayProduct> adapter= new
                FirebaseRecyclerAdapter<Products, DisplayProduct>(opt) {
                    @Override
                    protected void onBindViewHolder(@NonNull DisplayProduct holder, int position, @NonNull Products model)
                    {
                    holder.view3.setText(model.getDate());
                        holder.view2.setText(model.getPid());
                        holder.view1.setText(model.getDate());
                        Picasso.get().load(model.getImage()).into(holder.img1);
                    }
                    @NonNull
                    @Override
                    public DisplayProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scroller_layout,parent,false);
                        DisplayProduct holder = new DisplayProduct(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }*/



}
