package com.group16.hams;

public class RecyclerViewHolderAppointment {
    String appointmentDate;
    String appointmentTime;
    String appointmentPatientName;
    String appointmentApproval;

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
}
