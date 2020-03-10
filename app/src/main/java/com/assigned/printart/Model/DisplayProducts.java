package com.assigned.printart.Model;

public class DisplayProducts
{

    private String Pro,ProID,type,type1,Pame,Pdes,PpriceO,Psp,Seller,WithFrame,quantity,catt;

    public DisplayProducts()
    {

    }

    public DisplayProducts(String pro, String proID, String type, String type1, String pame, String pdes, String ppriceO, String psp, String seller, String withFrame, String quantity, String catt) {
        Pro = pro;
        ProID = proID;
        this.type = type;
        this.type1 = type1;
        Pame = pame;
        Pdes = pdes;
        PpriceO = ppriceO;
        Psp = psp;
        Seller = seller;
        WithFrame = withFrame;
        this.quantity = quantity;
        this.catt = catt;
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

    public String getPame() {
        return Pame;
    }

    public void setPame(String pame) {
        Pame = pame;
    }

    public String getPdes() {
        return Pdes;
    }

    public void setPdes(String pdes) {
        Pdes = pdes;
    }

    public String getPpriceO() {
        return PpriceO;
    }

    public void setPpriceO(String ppriceO) {
        PpriceO = ppriceO;
    }

    public String getPsp() {
        return Psp;
    }

    public void setPsp(String psp) {
        Psp = psp;
    }

    public String getSeller() {
        return Seller;
    }

    public void setSeller(String seller) {
        Seller = seller;
    }

    public String getWithFrame() {
        return WithFrame;
    }

    public void setWithFrame(String withFrame) {
        WithFrame = withFrame;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCatt() {
        return catt;
    }

    public void setCatt(String catt) {
        this.catt = catt;
    }
}
