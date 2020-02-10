package com.assigned.printart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.assigned.printart.Model.Movies;
import com.assigned.printart.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends PagerAdapter
{

     Context context;
     List<Movies> moviesList;
     LayoutInflater inflater;

    public Adapter(Context context, List<Movies> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
        inflater= LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return moviesList.size();
    }

    /**
     * Determines whether a page View is associated with a specific key object
     * as returned by {@link #instantiateItem(ViewGroup, int)}. This method is
     * required for a PagerAdapter to function properly.
     *
     * @param view   Page View to check for association with <code>object</code>
     * @param o Object to check for association with <code>view</code>
     * @return true if <code>view</code> is associated with the key object <code>object</code>
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager)container).removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.pageviewer,container,false);
        ImageView movie_images = (ImageView)view.findViewById(R.id.imageviewer);
        Picasso.get().load(moviesList.get(position).getImage()).into( movie_images);
        container.addView(view);
        return view;
    }
}
