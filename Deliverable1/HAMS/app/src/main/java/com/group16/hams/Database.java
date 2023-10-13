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

public class Database {
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    public static String address, firstName, lastName, username, password, phoneNumber, healthCardNumber;

    public static void getUser(FirebaseUser user) {
        // Change for doctor
        DatabaseReference myRef = database.getReference().child("Users").
                child("Patients").child(user.getUid());

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                address = snapshot.child("address").getValue(String.class);
                System.out.println(address);
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