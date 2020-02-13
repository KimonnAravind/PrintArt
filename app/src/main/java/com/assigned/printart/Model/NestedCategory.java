package com.assigned.printart.Model;

import android.net.Uri;
import android.widget.ImageView;

public class NestedCategory
{

private String ProductDescription;
private String type;


public NestedCategory()
{

}

    public NestedCategory(String productDescription, String type) {
        ProductDescription = productDescription;
        this.type = type;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
