package com.karthik.utils;

import com.karthik.model.Locker;

public interface LockerSelector
{
    public Locker getMatchingLocker(LockerPool lockerPool, int packageSize);
}
