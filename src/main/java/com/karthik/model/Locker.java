package com.karthik.model;

import java.util.Date;

import com.exceptions.InvalidStateException;

public class Locker
{
    private int id;
    private int dimension;
    private boolean isAllocated;
    private String currentOTP;
    private Date allocTime;
    private Package order;

    public Locker(int id, int dimension) {
        this.id = id;
        this.dimension = dimension;
        this.isAllocated = false;
        this.currentOTP = null;
        this.allocTime = null;
        this.order = null;
    }

    private void resetState() {
        isAllocated = false;
        order = null;
        currentOTP = null;
        allocTime = null;
    }

    public void allocate(Package order, String otp) {
        isAllocated = true;
        this.order = order;
        currentOTP = otp;
        allocTime = new Date();
    }

    public boolean unlock(String userOTP) throws InvalidStateException {
        if(isAllocated == false) {
            throw new InvalidStateException();
        }
        if(!userOTP.equals(currentOTP)) {
            return false;
        }
        resetState();
        return true;
    }

    public void adminUnlock() throws InvalidStateException {
        // This is a forceful unlock by admin
        if(isAllocated == false) {
            throw new InvalidStateException();
        }
        resetState();
    }

    public boolean isAllocated() {
        return isAllocated;
    }

    public String getCurrentOTP() {
        return currentOTP;
    }

    public Date getAllocTime() {
        return allocTime;
    }

    public Package getOrder() {
        return order;
    }

    public int getDimension() {
        return dimension;
    }

    public int getId() {
        return id;
    }
}
