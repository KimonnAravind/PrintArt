package com.assigned.printart.FirebListen;

import com.assigned.printart.Model.Banners;


import java.util.List;

public interface FirebaseViewer
{
   public void Loadsuccess(List<Banners> bannersList);
    public void Loadfailed(String string);
}
