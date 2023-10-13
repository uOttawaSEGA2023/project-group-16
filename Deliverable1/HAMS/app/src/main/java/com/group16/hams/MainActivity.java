package com.group16.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import entities.*;

public class MainActivity extends AppCompatActivity {

    private Button sign_in;
    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        sign_in = findViewById(R.id.sign_in);
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);

        //Button registerButton = findViewById(R.id.sign_up);
        //registerButton.setOnClickListener(this);
    }

    public void onClickRegister(View view) {
        Intent intent = new Intent(this, ChooseType.class);
        startActivity(intent);
    }
}