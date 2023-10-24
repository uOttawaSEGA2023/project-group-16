package com.group16.hams;

import static android.content.ContentValues.TAG;

import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import entities.*;
import java.util.ArrayList;

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
                            snapshot.child("employeeNumber").getValue(Integer.class),
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

    //I THINK THIS SHOULD WORK BUT I AM UNSURE
    //STILL NOT ENTIRELY COMPLETE. IDEALLY WE COULD PASS THE STATUS AS A PARAMETER AND READ ALL THE
    //USERS IN A PARTICULAR STATUS
    public ArrayList<User> getAllUsers() {
        ArrayList<User> totalUsers = new ArrayList<User>();

        ValueEventListener listener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User newUser;

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    //I THINK THIS SHOULD WORK BUT I AM UNSURE
                    if (dsp.getKey().equals("Patient")) {
                        newUser = new Patient(dsp.child("firstName").getValue(String.class),
                                dsp.child("lastName").getValue(String.class),
                                dsp.child("username").getValue(String.class),
                                dsp.child("password").getValue(String.class),
                                dsp.child("phoneNumber").getValue(String.class),
                                dsp.child("address").getValue(String.class),
                                dsp.child("healthCardNumber").getValue(Integer.class));
                        totalUsers.add(newUser);
                    }

                    else if (dsp.getKey().equals("Doctor")) {
                        newUser = new Doctor(dsp.child("firstName").getValue(String.class),
                                dsp.child("lastName").getValue(String.class),
                                dsp.child("username").getValue(String.class),
                                dsp.child("password").getValue(String.class),
                                dsp.child("phoneNumber").getValue(String.class),
                                dsp.child("address").getValue(String.class),
                                dsp.child("employeeNumber").getValue(Integer.class),
                                dsp.child("specialties").getValue(String.class).split(" "));
                        totalUsers.add(newUser);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        };

        return totalUsers;
    }

}