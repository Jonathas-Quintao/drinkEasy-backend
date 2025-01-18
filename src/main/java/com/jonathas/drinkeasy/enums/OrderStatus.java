package com.jonathas.drinkeasy.enums;

public enum OrderStatus {
    PENDING("pending"),
    COMPLETED("completed"),
    CANCELED("canceled");

    private String status;
    OrderStatus(String status){this.status = status;}

    public String getStatus(){
        return status;
    }
}
