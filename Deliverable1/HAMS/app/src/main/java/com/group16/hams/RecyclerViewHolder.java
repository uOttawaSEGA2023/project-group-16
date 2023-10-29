package com.group16.hams;

public abstract class RecyclerViewHolder {
    String fullName;
    String email;
    String address;
    String phoneNumber;

    public RecyclerViewHolder(String fullName, String email, String address, String phoneNumber) {
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
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
}
