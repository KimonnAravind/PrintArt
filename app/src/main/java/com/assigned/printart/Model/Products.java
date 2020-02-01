package com.assigned.printart.Model;

public class Products
{
private String date,image,pid;


public Products()
{
}
    public Products(String date, String image, String pid) {
        this.date = date;
        this.image = image;
        this.pid = pid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
