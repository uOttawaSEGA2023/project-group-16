package com.group16.hams;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.checkerframework.common.subtyping.qual.Bottom;

import entities.*;

public class MainActivity extends AppCompatActivity {

    private Button sign_in;
    private EditText email, password;
    private FirebaseAuth mAuth;

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
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
    }

    public void onLoginAttempt(View view){
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCustomToken:success");
                            Toast t = Toast.makeText(MainActivity.this, "Success!",
                                    Toast.LENGTH_SHORT);
                            t.setGravity(Gravity.BOTTOM, 0, 30);
                            t.show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Database.getUser(user);
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                            Toast t = Toast.makeText(MainActivity.this, "Wrong Email or Password",
                                    Toast.LENGTH_SHORT);
                            t.setGravity(Gravity.BOTTOM, 0, 0);
                            t.show();
                            updateUI(null);
                        }
                    }
                });
    }


    public void onClickRegister(View view) {
        Intent intent = new Intent(this, ChooseType.class);
        startActivity(intent);
    }
}