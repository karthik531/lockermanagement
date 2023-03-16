package com.karthik.utils;

import java.util.Iterator;
import com.exceptions.LockerNotFoundException;
import com.karthik.model.Locker;

public class SizeBasedLockerSelector implements LockerSelector
{
    @Override
    public Locker getMatchingLocker(LockerPool lockerPool, int packageSize) throws LockerNotFoundException {
        
        Iterator<Locker> lockerItr = lockerPool.iterator();
        while(lockerItr.hasNext()) {
            Locker locker = lockerItr.next();
            if(!locker.isAllocated() && locker.getDimension() >=  packageSize) {
                return locker;
            }
        }
        throw new LockerNotFoundException();
    }
}
