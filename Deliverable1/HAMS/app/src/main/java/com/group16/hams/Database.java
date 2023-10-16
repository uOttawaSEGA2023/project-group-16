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
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference myRef = database.getReference().child("Users");

    public static User currentUser;


    public static void getUser(FirebaseUser user) {
        // Change for doctor
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Patients").child(user.getUid()).exists()) {
                    snapshot = snapshot.child("Patients").child(user.getUid());
                    currentUser = new Patient(snapshot.child("firstName").getValue(String.class),
                            snapshot.child("lastName").getValue(String.class),
                            snapshot.child("username").getValue(String.class),
                            snapshot.child("password").getValue(String.class),
                            snapshot.child("phoneNumber").getValue(String.class),
                            snapshot.child("address").getValue(String.class),
                            snapshot.child("healthCardNumber").getValue(Integer.class));
                } else if (snapshot.child("Doctors").child(user.getUid()).exists()){
                    snapshot = snapshot.child("Doctors").child(user.getUid());
                    currentUser = new Doctor(snapshot.child("firstName").getValue(String.class),
                            snapshot.child("lastName").getValue(String.class),
                            snapshot.child("username").getValue(String.class),
                            snapshot.child("password").getValue(String.class),
                            snapshot.child("phoneNumber").getValue(String.class),
                            snapshot.child("address").getValue(String.class),
                            String.valueOf(snapshot.child("employeeNumber").getValue(Integer.class)),
                            snapshot.child("specialties").getValue(String.class).split(" "));
                } else if (snapshot.child("Admin").child(user.getUid()).exists()){
                    snapshot = snapshot.child("Admin").child(user.getUid());
                    currentUser = new Administrator();
                }
                    Log.w(TAG, currentUser.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        };
        myRef.addValueEventListener(listener);
    }

    public static void registerUser(FirebaseUser user, User u){
        if (u instanceof Doctor){
            myRef.child("Doctors").child(user.getUid()).setValue(u);
        } else if (u instanceof Patient){
            myRef.child("Patients").child(user.getUid()).setValue(u);
        }
    }
}