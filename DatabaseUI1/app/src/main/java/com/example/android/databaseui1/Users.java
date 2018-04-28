package com.example.android.databaseui1;

public class Users {
    private String userName, password ;
    private int phone ;
    public Users(String userName, String password ,int phone) {
        this.userName = userName;
        this.password = password;
        this.phone = phone ;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {return password; }

    public int getPhone() {return phone;}

}
