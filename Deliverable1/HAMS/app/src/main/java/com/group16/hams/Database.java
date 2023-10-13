package com.group16.hams;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import entities.*;

public class Database {
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    public static Doctor d;
    public static Patient p;


    public static void getUser(FirebaseUser user) {
        // Change for doctor
        DatabaseReference myRef = database.getReference().child("Users").
                child("Patients").child(user.getUid());

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                p = new Patient(snapshot.child("firstName").getValue(String.class),
                        snapshot.child("lastName").getValue(String.class),
                        snapshot.child("username").getValue(String.class),
                        snapshot.child("password").getValue(String.class),
                        snapshot.child("phoneNumber").getValue(String.class),
                        snapshot.child("address").getValue(String.class),
                        snapshot.child("healthCardNumber").getValue(Integer.class)
                );

                Log.w(TAG, p.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        };
        myRef.addValueEventListener(listener);
        System.out.println("E");
    }
}