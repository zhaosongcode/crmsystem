package com.crm.graduation.crmsystem.exceptions;

public class OrderStatusErrorException extends Exception {

    private int value;
    public OrderStatusErrorException() {
        super();
    }
    public OrderStatusErrorException(String msg,int value) {
        super(msg);
        this.value=value;
    }
    public int getValue() {
        return value;
    }
}
