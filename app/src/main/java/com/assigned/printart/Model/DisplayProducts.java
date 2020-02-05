package com.assigned.printart.Model;

public class DisplayProducts
{
    private String categoryName;
    private String categoryId;

    public DisplayProducts()
    {

    }

    public DisplayProducts(String categoryName, String categoryId) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
