package com.group16.hams.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.group16.hams.R;
import com.group16.hams.SearchAppointment;
import com.group16.hams.doctor.ShiftsDoctor;

import entities.*;

public class LoggedInPatient extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logged_in_patient);

        mAuth = FirebaseAuth.getInstance();
        logoutButton = findViewById(R.id.logoutButton);

    }

    public void onClickLogout(View view) {
        mAuth.signOut();
        finish();
        Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show();
    }

    public void onClickBookAppointment(View view){
        Intent intent = new Intent(this, SearchAppointment.class);
        startActivity(intent);
    }
}