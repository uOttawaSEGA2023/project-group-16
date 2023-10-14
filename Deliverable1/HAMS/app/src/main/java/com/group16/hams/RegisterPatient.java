package com.group16.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Pattern;

import entities.*;

public class RegisterPatient extends AppCompatActivity {

    private Button join;
    private EditText firstName, lastName, username, password, phoneNumber, address, healthCardNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_patient);

        join = findViewById(R.id.patient_join);
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        username = findViewById(R.id.email_registration);
        password = findViewById(R.id.password_registration);
        phoneNumber = findViewById(R.id.phone_number);
        address = findViewById(R.id.address);
        healthCardNumber = findViewById(R.id.health_card_number);

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
                String healthCardNumberText = healthCardNumber.getText().toString();

                if (validateName(firstNameText) == false){
                    firstName.setError("Invalid Input!");
                    validFlag = false;
                }
                if (validateName(lastNameText) == false){
                    lastName.setError("Invalid Input!");
                    validFlag = false;
                }
                if (validateUsername(usernameText) == false){
                    username.setError("Invalid Input!");
                    validFlag = false;
                }
                if (validatePhoneNumber(phoneNumberText) == false){
                    phoneNumber.setError("Invalid Input!");
                    validFlag = false;
                }
                if (validateAddress(addressText) == false){
                    address.setError("Invalid Input!");
                    validFlag = false;
                }
                if (passwordText.isEmpty() || passwordText == null){
                    password.setError("Invalid Input!");
                    validFlag = false;
                }
                if (healthCardNumberText.isEmpty() || healthCardNumberText == null){
                    healthCardNumber.setError("Invalid Input!");
                    validFlag = false;
                }

                if (validFlag == true){
                    // Save data
                    Intent intent = new Intent(RegisterPatient.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validateName(String name){
        if (name.isEmpty() || name == null){
            return false;
        }
        if (!name.matches("[a-zA-Z]+")) {
            return false;
        }
        int minLength = 1;
        int maxLength = 30;
        if (name.length() < minLength || name.length() > maxLength) {
            return false;
        }
        return true;
    }

    private boolean validateUsername(String username){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(username).matches();
    }

    private boolean validatePhoneNumber(String phoneNumber){
        String regex = "^\\d{3}-?\\d{3}-?\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(phoneNumber).matches();
    }

    private boolean validateAddress(String address){
        String addressRegex = "^[A-Za-z\\s]+,\\s*\\d+,[\\sA-Za-z]+,[\\sA-Za-z]+$";
        Pattern pattern = Pattern.compile(addressRegex);
        return pattern.matcher(address).matches();
    }
}