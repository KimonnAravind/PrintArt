package com.assigned.printart;

import android.content.Intent;
import android.os.Bundle;

import com.assigned.printart.Adapter.Adapter;
import com.assigned.printart.FirebListen.FirebaseViewer;
import com.assigned.printart.Model.Banners;
import com.assigned.printart.Model.DisplayCategory;
import com.assigned.printart.Model.NestedCategory;
import com.assigned.printart.Paper.PaperStore;
import com.assigned.printart.Transform.Transformer;
import com.assigned.printart.Viewer.CategoryViewHolder;
import com.assigned.printart.Viewer.NestedCategoryViewHolder;
import com.assigned.printart.ui.home.HomeFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements FirebaseViewer, NavigationView.OnNavigationItemSelectedListener,BottomNavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    ViewPager viewPager, viewPager2;
    Adapter myadapter, myadapters;
    private Timer timer;
    private LinearLayout dotslayout;
    int currentPosition = 0;

    private DatabaseReference EndUserPortal;
    FirebaseViewer firebaseViewer;
    List<Banners> bannersList = new ArrayList<>();
    DatabaseReference reference;
    DatabaseReference banner;
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter<DisplayCategory, CategoryViewHolder> adapter;
    FirebaseRecyclerAdapter<NestedCategory, NestedCategoryViewHolder> adapter1;
    RecyclerView.LayoutManager manager;
    String s;
    int positionpro = 0;

    /*RecyclerView.LayoutManager layoutManager, layoutManager1;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        banner = FirebaseDatabase.getInstance().getReference().child("Banner");
        dotslayout = (LinearLayout) findViewById(R.id.dotscontainer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layoutyes);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        manager = new LinearLayoutManager(this);
        reference = FirebaseDatabase.getInstance().getReference("ProductCategory");
        recyclerView = findViewById(R.id.recyclerViewer);
        recyclerView.setLayoutManager(manager);
        firebaseViewer = this;

        DatabaseReference UserPortal;
        UserPortal = FirebaseDatabase.getInstance().getReference();
        EndUserPortal = FirebaseDatabase.getInstance().getReference().child("EndUsers");
        ////   dotsview(positionpro++);
        viewPager = (ViewPager) findViewById(R.id.vp);
        viewPager2 = (ViewPager) findViewById(R.id.vsp);
        viewPager.setPageTransformer(true, new Transformer());
        viewPager2.setPageTransformer(true, new Transformer());


        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(this);



    }

    @Override
    protected void onStart() {

        super.onStart();
        Query sorting;
        // sorting= reference.orderByChild("CountPost");

       /* bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "TOast", Toast.LENGTH_SHORT).show();
            }
        });*/
        Random random = new Random();
        int randomNumber = random.nextInt(4);
        Log.e("RANDROM number", String.valueOf(randomNumber));
        switch (randomNumber) {
            case 0: {

                sorting = reference;
                break;
            }

            case 1: {
                sorting = reference.orderByChild("CountPost");
                break;
            }

            case 2: {
                sorting = reference.orderByChild("CountPost1");
                break;
            }

            case 3: {
                sorting = reference.orderByChild("CountPost2");
                break;
            }
            default: {
                sorting = reference;
            }
        }
        FirebaseRecyclerOptions<DisplayCategory> options = new FirebaseRecyclerOptions.Builder<DisplayCategory>()
                .setQuery(sorting, DisplayCategory.class).build();

        adapter = new FirebaseRecyclerAdapter<DisplayCategory, CategoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CategoryViewHolder holder, int position, @NonNull final DisplayCategory displayCategory) {
                CategoryViewHolder.CategoryName.setText(displayCategory.getCategoryName());

                FirebaseRecyclerOptions<NestedCategory> options1 = new FirebaseRecyclerOptions.Builder<NestedCategory>()
                        .setQuery(reference.child(displayCategory.getCategoryID()).child("About"), NestedCategory.class)
                        .build();
                adapter1 = new FirebaseRecyclerAdapter<NestedCategory, NestedCategoryViewHolder>(options1) {
                    @Override
                    protected void onBindViewHolder(@NonNull NestedCategoryViewHolder holder, int position, @NonNull final NestedCategory nestedCategory) {
                        Picasso.get().load(nestedCategory.getProductDescription()).into(holder.IgmV);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(HomeActivity.this, DisplayProductActivity.class);
                                intent.putExtra("Category", displayCategory.getCategoryID());
                                intent.putExtra("TypeID", nestedCategory.getType());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public NestedCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View v2 = LayoutInflater.from(getBaseContext()).inflate
                                (R.layout.nesteddisplay, parent, false);
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
                View v1 = LayoutInflater.from(getBaseContext()).inflate(R.layout.displayproducts, parent, false);
                return new CategoryViewHolder(v1);
            }
        };
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        final CircleImageView userProfilePicture = headerView.findViewById(R.id.user_profile_image);
        final TextView userPhonenumber = headerView.findViewById(R.id.user_phonenumber);
        final TextView userName = headerView.findViewById(R.id.user_profile_name);

        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "DP", Toast.LENGTH_SHORT).show();
            }
        });
        myadapter = new Adapter(this, bannersList);
        viewPager.setAdapter(myadapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        loadbanners();
        slideshow();
        Paper.init(this);
        s = Paper.book().read(PaperStore.UserLoginID);
        if (s == null || s.isEmpty()) {
            Paper.book().write(PaperStore.UserLoginID, "0000000000");
            s = Paper.book().read(PaperStore.UserLoginID);

        }
        EndUserPortal.child(s).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String x = dataSnapshot.child("DP").getValue().toString();
                    //  Toast.makeText(HomeActivity.this, ""+x, Toast.LENGTH_SHORT).show();
                    Picasso.get().load(x).into(userProfilePicture);
                    userPhonenumber.setText(dataSnapshot.child("PhoneNumber").getValue().toString());
                    userName.setText(dataSnapshot.child("Name").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void loadbanners() {
        banner.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot bannersnapshot : dataSnapshot.getChildren())
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
    public void Loadsuccess(List<Banners> bannersList) {
        myadapter = new Adapter(this, bannersList);
        viewPager.setAdapter(myadapter);
        viewPager2.setAdapter(myadapter);
    }

    @Override
    public void Loadfailed(String string) {
        Toast.makeText(this, "" + string, Toast.LENGTH_SHORT).show();
    }

    public void slideshow() {
        int temp = bannersList.size();


        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(currentPosition, true);
                viewPager2.setCurrentItem(currentPosition, true);
                currentPosition = currentPosition + 1;
                if (currentPosition >= bannersList.size()) {
                    currentPosition = 0;
                }


            }


        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 250, 2500);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*int id = item.getItemId();
        if(id == R.id.action_settings)
        {
            return true;

        }*/
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        int id = menuItem.getItemId();
        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        if (id == R.id.nav_gallery) {
            Paper.book().destroy();
            Paper.book().write(PaperStore.UserLoginID, "0000000000");
            Intent intent = getIntent();
            finish();
            startActivity(intent);

        } else if (id == R.id.nav_home) {
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();


        }
        else if(id==R.id.action_wishlist)
        {
            Toast.makeText(this, "Wishlist", Toast.LENGTH_SHORT).show();
        }



        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layoutyes);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void dotsview(int movingposition) {
        if (dotslayout.getChildCount() > 0) {
            dotslayout.removeAllViews();
        }
        ImageView dotArray[] = new ImageView[5];
        for (int i = 0; i < 5; i++) {
            dotArray[i] = new ImageView(this);
            if (i == movingposition) {
                Toast.makeText(this, "Active", Toast.LENGTH_SHORT).show();
                dotArray[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active));

            } else {
                Toast.makeText(this, "InActive", Toast.LENGTH_SHORT).show();

                dotArray[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.inactive));

            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(4, 0, 4, 0);
            dotslayout.addView(dotArray[i], layoutParams);
        }
    }

}
