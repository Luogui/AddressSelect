package com.example.luogui.addressselectdemo;


/**
 * bean
 * Created by  luogui on 2017/5/23.
 */

public class Address {
    private int addressId;
    private String addressName;

    public Address(int addressId, String addressName) {
        this.addressId = addressId;
        this.addressName = addressName;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }
}
