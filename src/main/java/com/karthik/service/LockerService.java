package com.karthik.service;

import java.util.Date;
import java.util.Iterator;
import com.exceptions.InvalidOrderTypeException;
import com.exceptions.LockerNotFoundException;
import com.karthik.model.Locker;
import com.karthik.model.OrderType;
import com.karthik.model.Package;
import com.karthik.utils.LockerPool;
import com.karthik.utils.LockerSelector;
import com.karthik.utils.SizeBasedLockerSelector;
import com.karthik.utils.TreeSetLockerPool;

public class LockerService
{
    private LockerPool lockerPool;
    private LockerSelector lockerSelector;
    private OTPService otpService;

    public LockerService() {
        lockerPool = new TreeSetLockerPool();
        lockerSelector = new SizeBasedLockerSelector();
        otpService = new OTPService();
    }

    public void addLocker(int id, int dimension) {
        lockerPool.add(new Locker(id, dimension));
        System.out.println("Locker added : (id: " + id + ", dim: " + dimension + ")");
    }

    public int getPoolSize() {
        return lockerPool.size();
    }

    public int allocateLocker(Package order, OrderType orderType) throws LockerNotFoundException {
        // Select locker as per requirement
        Locker selectedLocker = lockerSelector.getMatchingLocker(lockerPool, order.getMaxDimension());

        // generate otp and pass it into selected locker
        String otp = otpService.generateOTP();
        selectedLocker.allocate(order, otp);
        System.out.println("LockerID " + selectedLocker.getId() + " allocated");

        // send otp to user/delivery guy
        if(orderType.equals(OrderType.DELIVERY)) {
            otpService.sendOTP(order.getUserEmail(), otp);
        }
        else if(orderType.equals(OrderType.RETURN)) {
            // invoke Finder service to allot a delivery guy & get their email address
            otpService.sendOTP("delivery@click.com", otp);
        }
        else {
            throw new InvalidOrderTypeException();
        }
        return selectedLocker.getId();
    }

    public boolean unlockLocker(int lockerId, String otp) {
        try {
            Locker locker = getLockerById(lockerId);
            boolean unlockStatus = locker.unlock(otp);
            if(unlockStatus == false) {
                System.out.println("Incorrect OTP entered");
                return false;
            }
            System.out.println("Locker " + lockerId + " unlocked");
        }
        catch(LockerNotFoundException e) {
            System.out.println("Invalid lockerID provided");
            return false;
        }
        return true;
    }

    public Locker getLockerById(int lockerId) throws LockerNotFoundException {
        Iterator<Locker> lockerItr = lockerPool.iterator();
        while(lockerItr.hasNext()) {
            Locker locker = lockerItr.next();
            if(locker.getId() == lockerId) {
                return locker;
            }
        }
        throw new LockerNotFoundException();
    }

    public void unlockDormantLockers(int numDays) {
        // unlock all the occupied lockers allocated for more than numDays
        long currentTime = new Date().getTime();
        Iterator<Locker> lockerItr = lockerPool.iterator();
        while(lockerItr.hasNext()) {
            Locker locker = lockerItr.next();
            if(!locker.isAllocated())
                continue;
            long allocTime = locker.getAllocTime().getTime();
            double days = ((((double)(currentTime - allocTime)/1000)/60)/60)/24;
            if(days > numDays) {
                System.out.println("Unlocking lockerID " + locker.getId() + " dormant for " + days + " days");
                locker.adminUnlock();
            }
        }
    }
}
