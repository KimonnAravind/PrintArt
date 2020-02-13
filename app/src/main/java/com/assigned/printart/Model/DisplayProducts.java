package com.assigned.printart.Model;

public class DisplayProducts
{

    private String Pro,ProID,type,type1;

    public DisplayProducts()
    {

    }

    public DisplayProducts(String pro, String proID, String type, String type1) {
        Pro = pro;
        ProID = proID;
        this.type = type;
        this.type1 = type1;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }
}
