package com.assigned.printart.Model;

import android.net.Uri;
import android.widget.ImageView;

public class NestedCategory
{

private String ProductDescription;


public NestedCategory()
{

}

    public NestedCategory(String productDescription) {
        ProductDescription = productDescription;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }
}
