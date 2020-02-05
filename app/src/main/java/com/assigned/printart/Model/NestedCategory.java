package com.assigned.printart.Model;

public class NestedCategory
{
private String ProductName;
private String ProductPrice;
private String ProductDescription;


public NestedCategory()
{

}

    public NestedCategory(String productName, String productPrice, String productDescription)
    {
        ProductName = productName;
        ProductPrice = productPrice;
        ProductDescription = productDescription;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }
}
