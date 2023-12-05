package com.group16.hams;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import entities.Doctor;
import entities.Patient;
import entities.Shift;


public class Deliverable4UnitTests {

    @Test
    public void ShiftUpcoming() {
        Shift testShift = new Shift("12/12/2023", "12:00", "15:00","doctor@username.com", "Medicine");

        assertEquals("Test if shift is upcoming", true, testShift.isFuture());
    }

    @Test
    public void PatientHealthCard() {
        Patient testPatient = new Patient("Test", "Patient", "madeup@email.com", "password", "999-999-9999", "first, 1, second, third", 0);

        assertEquals("Test health card number", 0, testPatient.getHealthCardNumber());
    }

    @Test
    public void PatientName() {
        Patient testPatient = new Patient("Test", "Patient", "madeup@email.com", "password", "999-999-9999", "first, 1, second, third", 0);

        assertNotEquals("Test patient first name", "Patient", testPatient.getFirstName());
    }

    @Test
    public void DoctorSpecialties() {
        Doctor testDoctor = new Doctor("Test", "Doctor", "madeup@email.com", "password", "999-999-9999", "first, 1, second, third", 0, "Medicine");

        assertEquals("Test doctor specialties", "medicine", testDoctor.getSpecialties());
    }


}
