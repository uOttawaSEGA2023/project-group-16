package com.group16.hams.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.group16.hams.R;

public class LoggedInAdmin extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logged_in_admin);

        mAuth = FirebaseAuth.getInstance();
        logoutButton = findViewById(R.id.logoutButton);

    }

    public void onClickLogout(View view) {
        mAuth.signOut();
        finish();
        Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show();
    }

    public void onClickPending(View view) {
        Intent intent = new Intent(this, PendingAccounts.class);
        startActivity(intent);
    }

    public void onClickAccepted(View view) {
        Intent intent = new Intent(this, AcceptedAccounts.class);
        startActivity(intent);
    }

    public void onClickRejected(View view) {
        Intent intent = new Intent(this, RejectedAccounts.class);
        startActivity(intent);
    }
}