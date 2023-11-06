package com.group16.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import entities.*;

public class LoggedInDoctor extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logged_in_doctor);

        mAuth = FirebaseAuth.getInstance();
        logoutButton = findViewById(R.id.logoutButton);

    }

    public void onClickLogout(View view) {
        mAuth.signOut();
        finish();
        Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show();
    }

    public void onClickAppointments(View view) {
        Intent intent = new Intent(this, AppointmentsDoctor.class);
        startActivity(intent);
    }
}