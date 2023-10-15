package com.group16.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

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

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                Intent intent = new Intent(LoggedInPatient.this, MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(LoggedInPatient.this, "Logout Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }
}