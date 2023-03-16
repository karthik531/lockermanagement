package com.karthik.utils;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import com.karthik.model.Locker;

public class TreeSetLockerPool implements LockerPool 
{
    private Set<Locker> lockerPool;

    public TreeSetLockerPool() {
        lockerPool = new TreeSet<>((l1, l2) -> {
            return l1.getDimension() - l2.getDimension();
        });
    }

    @Override
    public void add(Locker locker) {
        lockerPool.add(locker);
    }

    @Override
    public Iterator<Locker> iterator() {
        return lockerPool.iterator();
    }

    @Override
    public int size() {
        return lockerPool.size();
    }
}
