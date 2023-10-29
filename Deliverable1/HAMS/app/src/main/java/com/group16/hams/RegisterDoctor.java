package com.group16.hams;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import entities.*;

import java.util.regex.Pattern;

public class RegisterDoctor extends AppCompatActivity {

    private Button join;
    private FirebaseAuth mAuth;
    private EditText firstName, lastName, username, password, phoneNumber, address, employeeNumber, specialtiesEditText;

    private User u;
    private String[] specialties;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_doctor);

        join = findViewById(R.id.patient_join);
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        username = findViewById(R.id.email_registration);
        password = findViewById(R.id.password_registration);
        phoneNumber = findViewById(R.id.phone_number);
        address = findViewById(R.id.address);
        employeeNumber = findViewById(R.id.employee_id);
        specialtiesEditText = findViewById(R.id.specialty);

        join.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                boolean validFlag = true;
                String firstNameText = firstName.getText().toString();
                String lastNameText = lastName.getText().toString();
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();
                String phoneNumberText = phoneNumber.getText().toString();
                String addressText = address.getText().toString();
                String employeeNumberText = employeeNumber.getText().toString();
                String specialtiesText = specialtiesEditText.getText().toString();
                String[] specialtiesArray = specialtiesText.split(" ");


                if (validateName(firstNameText) == false) {
                    firstName.setError("Invalid Input!");
                    validFlag = false;
                }
                if (validateName(lastNameText) == false) {
                    lastName.setError("Invalid Input!");
                    validFlag = false;
                }
                if (validateUsername(usernameText) == false) {
                    username.setError("Invalid Input!");
                    validFlag = false;
                }
                if (validatePhoneNumber(phoneNumberText) == false) {
                    phoneNumber.setError("Invalid Number!");
                    validFlag = false;
                }
                if (validateAddress(addressText) == false) {
                    address.setError("Invalid Address!");
                    validFlag = false;
                }
                if (passwordText.length() < 6 || passwordText == null) {
                    password.setError("Password must be longer than 6 Inputs!");
                    validFlag = false;
                }

                if (validateEmployeeNumber(employeeNumberText) == false) {
                    employeeNumber.setError("Invalid Number!");
                    validFlag = false;
                }

                if (!validateString(specialtiesText)) {
                    specialtiesEditText.setError("Invalid Input!");
                    validFlag = false;
                }



                if (validFlag) {
                    // Save data
                    Intent intent = new Intent(RegisterDoctor.this, MainActivity.class);
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(usernameText, passwordText)
                            .addOnCompleteListener(RegisterDoctor.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        u = new Doctor(firstNameText,
                                                lastNameText,
                                                usernameText,
                                                passwordText,
                                                phoneNumberText,
                                                addressText,
                                                Integer.parseInt(employeeNumberText),
                                                specialtiesArray);


                                        Database.registerUser(user, u);

                                        Toast.makeText(RegisterDoctor.this, "Success! Please log in.",
                                                Toast.LENGTH_SHORT).show();
                                        startActivity(intent);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(RegisterDoctor.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private boolean validateName(String name) {
        if (name.isEmpty() || name == null) {
            return false;
        }
        if (!name.matches("[a-zA-Z]+")) {
            return false;
        }
        int minLength = 1;
        int maxLength = 30;
        return name.length() >= minLength && name.length() <= maxLength;
    }

    private boolean validateUsername(String username) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(username).matches();
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        String regex = "^\\d{3}-?\\d{3}-?\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(phoneNumber).matches();
    }

    private boolean validateAddress(String address) {
        String addressRegex = "^[A-Za-z\\s]+,\\s*\\d+,[\\sA-Za-z]+,[\\sA-Za-z]+$";
        Pattern pattern = Pattern.compile(addressRegex);
        return pattern.matcher(address).matches();
    }


    private boolean validateEmployeeNumber(String employeeNumber) {
        return employeeNumber != null && !employeeNumber.isEmpty();
    }
    private boolean validateString(String input) {
        return input != null && !input.isEmpty();
    }

}


