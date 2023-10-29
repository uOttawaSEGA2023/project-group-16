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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Database {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference myRef = database.getReference();
    private static DatabaseReference pendingRef = myRef.child("Pending");
    private static DatabaseReference rejectedRef = myRef.child("Rejected");
    private static DatabaseReference userRef = myRef.child("Users");
    public static User currentUser;
    public enum UserStatus{
        PENDING,
        REJECTED,
        ACCEPTED;
    }

    //Adjust getUser for new status
    public static void getUser(FirebaseUser user) {
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
        userRef.addValueEventListener(listener);
    }

    public static void registerUser(FirebaseUser user, User u){
        if (u instanceof Doctor){
            pendingRef.child("Doctors").child(user.getUid()).setValue(u);
        } else if (u instanceof Patient){
            pendingRef.child("Patients").child(user.getUid()).setValue(u);
        }
    }

    public interface UserFetchCallback {
        void onUsersFetched(ArrayList<User> users);
    }

    public static ArrayList<User> getAllUsers(UserStatus status, UserFetchCallback callback) {
        ArrayList<User> totalUsers = new ArrayList<User>();

        switch(status){
            case PENDING:
                pendingRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
                        callback.onUsersFetched(totalUsers);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
                break;
            case REJECTED:
                rejectedRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
                        callback.onUsersFetched(totalUsers);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
                break;
            case ACCEPTED:
                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
                        callback.onUsersFetched(totalUsers);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
                break;
        }

        return totalUsers;
    }

}