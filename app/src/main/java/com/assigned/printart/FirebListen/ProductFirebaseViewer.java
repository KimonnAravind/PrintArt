package com.assigned.printart.FirebListen;

import com.assigned.printart.Model.Banners;
import com.assigned.printart.Model.ProductBanners;

import java.util.List;

public interface ProductFirebaseViewer
{

    public void Loadsuccess(List<ProductBanners> productBannersList);
    public void Loadfailed(String string);

}
