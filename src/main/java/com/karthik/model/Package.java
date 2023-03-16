package com.karthik.model;

public class Package 
{
    private int maxDimension;
    private String description;
    private String userEmail;

    public Package(String description, int maxDimension, String userEmail) {
        this.maxDimension = maxDimension;
        this.userEmail = userEmail;
        this.description = description;
    }

    public int getMaxDimension() {
        return maxDimension;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getDescription() {
        return description;
    }
}
