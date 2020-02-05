package com.assigned.printart.Model;

public class DisplayCategory
{
    private String CategoryID;
    private String CategoryName;

    public DisplayCategory()
    {

    }

    public DisplayCategory(String categoryID, String categoryName) {
        CategoryID = categoryID;
        CategoryName = categoryName;
    }

    public String getCategoryID()
    {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }
}
