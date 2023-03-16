package com.karthik.utils;

import java.util.Iterator;

import com.karthik.model.Locker;

public interface LockerPool
{
    public void add(Locker locker);
    
    public int size();

    public Iterator<Locker> iterator();
}
