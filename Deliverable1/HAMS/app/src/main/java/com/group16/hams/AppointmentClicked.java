package com.group16.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AppointmentClicked extends AppCompatActivity {

    RecyclerViewHolderAppointment curHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_clicked);

        Intent intent = getIntent();
        curHolder = intent.getParcelableExtra("Appointment Holder");
    }

    public void onClickCancelAppointmentButton(View view) {
        //Change the status of the appointment to CANCELLED
        //Do this once the appointment class is finalised

        Button approveButton = view.findViewById(R.id.approveAppointmentButton);
        Button cancleButton = view.findViewById(R.id.cancelAppointmentButton);

        approveButton.setVisibility(View.VISIBLE);
        cancleButton.setVisibility(View.GONE);
    }

    public void onClickApproveAppointmentButton(View view) {
        //Change the status of the appointment to APPROVED
        //Do this once the appointment class is finalised

        Button approveButton = view.findViewById(R.id.approveAppointmentButton);
        Button cancelButton = view.findViewById(R.id.cancelAppointmentButton);

        approveButton.setVisibility(View.GONE);
        cancelButton.setVisibility(View.VISIBLE);
    }

    public void onClickReturnToAppointmentsButton(View view) {
        finish();
    }
}