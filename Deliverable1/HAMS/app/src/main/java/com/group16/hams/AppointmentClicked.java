package com.group16.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import entities.*;

public class AppointmentClicked extends AppCompatActivity {

    RecyclerViewHolderAppointment curHolder;

    TextView appointmentDate, appointmentTime, curApprovalStatus, patientName, patientUsername,
            patientPhoneNumber, patientAddress, patientHealthCard;
    Patient curPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("REACHED");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_clicked);

        Intent intent = getIntent();
        curHolder = intent.getParcelableExtra("Appointment Holder");

        System.out.println(curHolder.getAppointment().getAppointmentPatientEmail());

        Database.getPatient(curHolder.getAppointment().getAppointmentPatientEmail(), new Database.MyCallBack() {
            @Override
            public void onCallBack(Patient p) {
                if (p == null) {
                    //Might have to add more precautions here in case the patient gets deleted
                    //after the appointment has already been created. Otherwise this should never occur
                    System.out.println("Patient is not in database.");
                }

                else {
                    curPatient = p;
                }
            }
        });

        appointmentDate = findViewById(R.id.appointmentDate);
        appointmentTime = findViewById(R.id.appointmentTime);
        curApprovalStatus = findViewById(R.id.currentApprovalStatus);
        patientName = findViewById(R.id.patientName);
        patientUsername = findViewById(R.id.patientUsername);
        patientPhoneNumber = findViewById(R.id.patientPhoneNumber);
        patientAddress = findViewById(R.id.patientAddress);
        patientHealthCard = findViewById(R.id.patientHealthCardNumber);

        appointmentDate.setText("Appointment Date: " + curHolder.getAppointmentDate());
        appointmentTime.setText("Appointment Time: " + curHolder.getAppointmentTime());
        curApprovalStatus.setText("Current Approval Status: " + curHolder.getAppointmentApproval());
        patientName.setText("Patient Name: " + curHolder.getAppointmentPatientName());

        //These are causing it to crash
        //patientUsername.setText("Patient Username: " + curPatient.getUsername());
        //patientPhoneNumber.setText("Patient Phone Number: " + curPatient.getPhoneNumber());
        //patientAddress.setText("Patient Address: " + curPatient.getAddress());
        //patientHealthCard.setText("Patient Health Card Number: " + curPatient.getHealthCardNumber());
    }

    public void onClickCancelAppointmentButton(View view) {
        //Change the status of the appointment to CANCELLED
        curHolder.getAppointment().setStatus(Appointment.REJECTED_APPOINTMENT);

        //Do this once the appointment class is finalised (not sure what's left to do)

        Button approveButton = (Button)findViewById(R.id.approveAppointmentButton);
        Button cancelButton = (Button)findViewById(R.id.cancelAppointmentButton);

        approveButton.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.GONE);

        curApprovalStatus.setText("Current Approval Status: " + curHolder.getAppointmentApproval());
    }

    public void onClickApproveAppointmentButton(View view) {
        //Change the status of the appointment to APPROVED
        curHolder.getAppointment().setStatus(Appointment.APPROVED_APPOINTMENT);

        //Do this once the appointment class is finalised (not sure what's left to do)

        Button approveButton = (Button)view.findViewById(R.id.approveAppointmentButton);
        Button cancelButton = (Button)view.findViewById(R.id.cancelAppointmentButton);

        approveButton.setVisibility(View.GONE);
        cancelButton.setVisibility(View.VISIBLE);

        curApprovalStatus.setText("Current Approval Status: " + curHolder.getAppointmentApproval());
    }

    public void onClickReturnToAppointmentsButton(View view) {
        finish();
    }
}