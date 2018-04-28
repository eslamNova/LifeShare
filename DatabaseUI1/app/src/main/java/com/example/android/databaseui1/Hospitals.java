package com.example.android.databaseui1;

/**
 * Created by islam on 4/28/2018.
 */

public class Hospitals {
    private String name, address;

    public Hospitals(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

}
