package com.assigned.printart.Model;

public class DisplayProducts
{

    private String Pro,ProID;

    public DisplayProducts()
    {

    }

    public DisplayProducts(String pro, String proID) {
        Pro = pro;
        ProID = proID;
    }

    public String getPro() {
        return Pro;
    }

    public void setPro(String pro) {
        Pro = pro;
    }

    public String getProID() {
        return ProID;
    }

    public void setProID(String proID) {
        ProID = proID;
    }
}
