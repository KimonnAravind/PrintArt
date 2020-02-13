package com.assigned.printart.Model;

import android.widget.Toast;

import com.assigned.printart.ShowDetailsActivity;

public class ProductBanners
{
public String image;
public ProductBanners()
{

}

    public ProductBanners(String image) {
        this.image = image;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
