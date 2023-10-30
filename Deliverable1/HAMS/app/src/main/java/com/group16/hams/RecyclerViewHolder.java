package com.group16.hams;

public class RecyclerViewHolder {
    public static final int LayoutPatient = 0;
    public static final int LayoutDoctor = 1;

    int layoutType;
    String fullName;
    String email;
    String address;
    String phoneNumber;
    String healthCardNumber;

    public RecyclerViewHolder(int layoutType, String fullName, String email, String address,
                              String phoneNumber, String healthCardNumber) {
        this.layoutType = layoutType;
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.healthCardNumber = healthCardNumber;
    }

    String employeeNumber;
    String specialties;

    public RecyclerViewHolder(int layoutType, String fullName, String email, String address,
                              String phoneNumber, String employeeNumber, String specialties) {
        this.layoutType = layoutType;
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.employeeNumber = employeeNumber;
        this.specialties = specialties;
    }

    public int getLayoutType() {
        return layoutType;
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

    public String getHealthCardNumber() {
        return healthCardNumber;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public String getSpecialties() {
        return specialties;
    }
}
