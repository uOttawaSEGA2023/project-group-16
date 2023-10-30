package com.group16.hams;

public class RecyclerViewHolder {
    int type;
    String fullName;
    String email;
    String address;
    String phoneNumber;
    String healthOrEmployee;
    String specialties;
    boolean beenClicked = false;

    public RecyclerViewHolder(int type, String fullName, String email, String address,
                              String phoneNumber, String employeeNumber, String specialties) {
        this.type = type;
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.healthOrEmployee = employeeNumber;
        this.specialties = specialties;
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

    public void changeClickedStatus() {
        beenClicked = !beenClicked;
    }
}
