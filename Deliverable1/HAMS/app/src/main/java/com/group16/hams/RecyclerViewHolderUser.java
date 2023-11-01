package com.group16.hams;

import entities.User;

public class RecyclerViewHolderUser {
    int type;
    String fullName;
    String email;
    String address;
    String phoneNumber;
    String healthOrEmployee;
    String specialties;
    boolean beenClicked = false;
    User storedUser;

    public RecyclerViewHolderUser(int type, String fullName, String email, String address,
                                  String phoneNumber, String employeeNumber, String specialties, User storedUser) {
        this.type = type;
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.healthOrEmployee = employeeNumber;
        this.specialties = specialties;
        this.storedUser = storedUser;
    }

    public int getType() {
        return type;
    }
    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getHealthOrEmployee() {
        return healthOrEmployee;
    }
    public String getSpecialties() {
        return specialties;
    }

    public boolean getBeenClicked() {
        return beenClicked;
    }

    public User getStoredUser() {
        return storedUser;
    }

    public void changeClickedStatus() {
        beenClicked = !beenClicked;
    }
}
