package com.group16.hams;

public class RecyclerViewHolderAppointment {

    public static final int PAST_APPOINTMENT = 0;
    public static final int UPCOMING_APPOINTMENT = 1;
    String appointmentDate;
    String appointmentTime;
    String appointmentPatientName;
    String appointmentApproval;
    int pastOrUpcoming;

    public RecyclerViewHolderAppointment(String appointmentDate, String appointmentTime,
                                         String appointmentPatientName,
                                         String appointmentApproval) {
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentPatientName = appointmentPatientName;
        this.appointmentApproval = appointmentApproval;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public String getAppointmentPatientName() {
        return appointmentPatientName;
    }

    public String getAppointmentApproval() {
        return appointmentApproval;
    }

    public int getPastOrUpcoming() {
        return pastOrUpcoming;
    }

    public void setPastOrUpcoming(int pastOrUpcoming) {
        this.pastOrUpcoming = pastOrUpcoming;
    }
}
