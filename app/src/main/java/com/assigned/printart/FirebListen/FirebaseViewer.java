package com.assigned.printart.FirebListen;

import com.assigned.printart.Model.Movies;

import java.util.List;

public interface FirebaseViewer
{
   public void Loadsuccess(List<Movies> moviesList);
    public void Loadfailed(String string);
}
