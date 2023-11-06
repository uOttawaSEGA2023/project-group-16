package entities;

import java.util.Date;

public class Appointment {
    Patient appointmentPatient;

    String appointmentPatientEmail;
    String startDateAndTimeString;
    String endDateAndTimeString;

    Date startDateAndTime;
    Date endDateAndTime;

    boolean upcoming = true;


    public Appointment(String appointmentPatientEmail, String startDateAndTimeString, String endDateAndTimeString) {
        this.appointmentPatientEmail = appointmentPatientEmail;
        this.startDateAndTimeString = startDateAndTimeString;
        this.endDateAndTimeString = endDateAndTimeString;

        //ADD A METHOD HERE THAT GETS THE PATIENT FROM A METHOD IN DATABASE THAT FINDS THE PATIENT USING THE EMAIL

        //CONVERT DATE STRINGS TO ACTUAL DATES
    }
}
