package com.group16.hams;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.group16.hams.admin.LoggedInAdmin;
import com.group16.hams.doctor.LoggedInDoctor;
import com.group16.hams.patient.LoggedInPatient;
import com.group16.hams.register.ChooseType;

import entities.*;

public class LoginMenu extends AppCompatActivity {

    private Button sign_in;
    private EditText email, password;
    private FirebaseAuth mAuth;
    private Dialog popUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAuth = FirebaseAuth.getInstance();

        sign_in = findViewById(R.id.sign_in);
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);

        //Button registerButton = findViewById(R.id.sign_up);
        //registerButton.setOnClickListener(this);
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void updateUI(FirebaseUser currentUser) {
        Intent intent;

        if (Database.currentUser instanceof Patient){
            intent = new Intent(this, LoggedInPatient.class);
            startActivity(intent);
        } else if (Database.currentUser instanceof  Doctor){
            intent = new Intent(this, LoggedInDoctor.class);
            startActivity(intent);
        } else if (Database.currentUser instanceof Administrator){
            intent = new Intent(this, LoggedInAdmin.class);
            startActivity(intent);
        }
    }

    public void onLoginAttempt(View view) {

        System.out.println();
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();

        Toast t = new Toast(LoginMenu.this);
        if(email.isEmpty() && password.isEmpty()){
            t.makeText(LoginMenu.this, "Both fields are empty",
                    Toast.LENGTH_SHORT).show();
            return;
        } else if (email.isEmpty()){
            t.makeText(LoginMenu.this, "Email field is empty",
                    Toast.LENGTH_SHORT).show();
            return;
        } else if (password.isEmpty()){
            t.makeText(LoginMenu.this, "Password field is empty",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Database.getUser(user);

                            Log.d(TAG, "signInWithCustomToken:success");
                            (new Handler()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    String message = "";
                                    switch(Database.currentUserStatus){
                                        case PENDING:
                                            message = "Pending for admin approval";
                                            break;
                                        case REJECTED:
                                            popUp = new Dialog(LoginMenu.this);
                                            popUp.setContentView(R.layout.rejected_message_popup);
                                            popUp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                            popUp.show();
                                            break;
                                        case ACCEPTED:
                                            message = "Success!";
                                            updateUI(user);
                                            break;
                                    }
                                    if (!message.isEmpty()) {
                                        Toast t = Toast.makeText(LoginMenu.this, message,
                                                Toast.LENGTH_SHORT);
                                        t.show();
                                    }
                                }
                            }, 1000);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                            Toast t = Toast.makeText(LoginMenu.this, "Wrong Email or Password",
                                    Toast.LENGTH_SHORT);
                            t.show();

                        }
                    }
                });
    }

    public void onClickRegister(View view) {
        Intent intent = new Intent(this, ChooseType.class);
        startActivity(intent);
    }
}