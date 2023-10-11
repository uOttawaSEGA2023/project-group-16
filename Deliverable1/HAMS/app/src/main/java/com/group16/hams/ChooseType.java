package com.group16.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import entities.*;

public class ChooseType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_type);
    }

    public void onClickDoctor(View view) {
        Intent intent = new Intent(this, RegisterDoctor.class);
        startActivity(intent);
    }

    public void onClickPatient(View view) {
        Intent intent = new Intent(this, RegisterPatient.class);
        startActivity(intent);
    }

}