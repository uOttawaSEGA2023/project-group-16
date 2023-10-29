package com.group16.hams;

public class PatientRecyclerViewHolder extends RecyclerViewHolder {
    String fullName;
    String email;
    String address;
    String phoneNumber;
    String healthCardNumber;

    public PatientRecyclerViewHolder(String fullName, String email, String address,
                                     String phoneNumber, String healthCardNumber) {
        super(fullName, email, address, phoneNumber);
        this.healthCardNumber = healthCardNumber;
    }

    public String getHealthCardNumber() {
        return healthCardNumber;
    }
}
