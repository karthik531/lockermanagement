package com.karthik.model;

public enum OrderType {
    
    DELIVERY, RETURN;

    public String toString() {

        switch(this) {
            case DELIVERY:
                return "delivery";
            case RETURN:
                return "return";
            default:
                return "undefined";
        }
    }
}
