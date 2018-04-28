package com.example.android.databaseui1;

public class Request {
    private String userName, hospitalName,hospitalAddress, bloodType, phoneNumber;

    public Request(String hospitalName, String bloodType, String userName, String hospitalAddress, String phoneNumber) {
        this.hospitalName = hospitalName;
        this.bloodType = bloodType;
        this.userName = userName;
        this.hospitalAddress = hospitalAddress;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getUserName() {
        return userName;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }
}
