package com.group16.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import entities.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //Button registerButton = findViewById(R.id.sign_up);
        //registerButton.setOnClickListener(this);
    }

    public void onClickRegister(View view) {
        Intent intent = new Intent(this, ChooseType.class);
        startActivity(intent);
    }
}