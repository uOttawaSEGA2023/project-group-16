package com.group16.hams;

public class DoctorRecyclerViewHolder extends RecyclerViewHolder {
    String fullName;
    String email;
    String address;
    String phoneNumber;
    String employeeNumber;
    String specialties;

    public DoctorRecyclerViewHolder(String fullName, String email, String address,
                                    String phoneNumber, String employeeNumber, String specialties) {
        super(fullName, email, address, phoneNumber);
        this.employeeNumber = employeeNumber;
        this.specialties = specialties;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public String getSpecialties() {
        return specialties;
    }
}
