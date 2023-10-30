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
    public static UserStatus currentUserStatus;

    //Adjust getUser for new status
    public static void getUser(FirebaseUser user) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()) { // Children of the database (Pending, Rejected, Users/Accepted)
                    String parent = snap.getKey();
                    if (snap.child("Patients").child(user.getUid()).exists()) {
                        determineStatus(parent);
                        snap = snap.child("Patients").child(user.getUid());
                        currentUser = new Patient(snap.child("firstName").getValue(String.class),
                                snap.child("lastName").getValue(String.class),
                                snap.child("username").getValue(String.class),
                                snap.child("password").getValue(String.class),
                                snap.child("phoneNumber").getValue(String.class),
                                snap.child("address").getValue(String.class),
                                snap.child("healthCardNumber").getValue(Integer.class));
                        break;
                    } else if (snap.child("Doctors").child(user.getUid()).exists()) {
                        snap = snap.child("Doctors").child(user.getUid());
                        determineStatus(parent);
                        currentUser = new Doctor(snapshot.child("firstName").getValue(String.class),
                                snap.child("lastName").getValue(String.class),
                                snap.child("username").getValue(String.class),
                                snap.child("password").getValue(String.class),
                                snap.child("phoneNumber").getValue(String.class),
                                snap.child("address").getValue(String.class),
                                snap.child("employeeNumber").getValue(Integer.class),
                                snap.child("specialties").getValue(String.class));
                        break;
                    } else if (snap.child("Admin").child(user.getUid()).exists()) {
                        determineStatus(parent);
                        snap = snap.child("Admin").child(user.getUid());
                        currentUser = new Administrator();
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        };

        myRef.addValueEventListener(listener);

    }

    public static ArrayList<User> getAllUsers(UserStatus status) {
        ArrayList<User> totalUsers = new ArrayList<User>();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot pSnap : snapshot.child("Patients").getChildren()) { // Snapshot of Patients
//                    System.out.println("Key: " + pSnap.getKey());
//                    System.out.println("Children: " + pSnap.getChildren());

                    totalUsers.add(new Patient(pSnap.child("firstName").getValue(String.class),
                            pSnap.child("lastName").getValue(String.class),
                            pSnap.child("username").getValue(String.class),
                            pSnap.child("password").getValue(String.class),
                            pSnap.child("phoneNumber").getValue(String.class),
                            pSnap.child("address").getValue(String.class),
                            pSnap.child("healthCardNumber").getValue(Integer.class))
                    );
                }
                for (DataSnapshot dSnap : snapshot.child("Doctors").getChildren()) {
                    totalUsers.add(new Doctor(dSnap.child("firstName").getValue(String.class),
                                dSnap.child("lastName").getValue(String.class),
                                dSnap.child("username").getValue(String.class),
                                dSnap.child("password").getValue(String.class),
                                dSnap.child("phoneNumber").getValue(String.class),
                                dSnap.child("address").getValue(String.class),
                                dSnap.child("employeeNumber").getValue(Integer.class),
                                dSnap.child("specialties").getValue(String.class))
                            );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        };

        switch (status){
            case PENDING:
                pendingRef.addValueEventListener(listener);
                break;
            case REJECTED:
                rejectedRef.addValueEventListener(listener);
                break;
            case ACCEPTED:
                userRef.addValueEventListener(listener);
                break;
        }
        return totalUsers;
    }

    public static void registerUser(FirebaseUser user, User u){
        if (u instanceof Doctor){
            pendingRef.child("Doctors").child(user.getUid()).setValue(u);
        } else if (u instanceof Patient){
            pendingRef.child("Patients").child(user.getUid()).setValue(u);
        }
    }

    private static void determineStatus(String status){
        if (status.equals("Pending"))
            currentUserStatus = UserStatus.PENDING;
        else if (status.equals("Rejected"))
            currentUserStatus = UserStatus.REJECTED;
        else if (status.equals("Users"))
            currentUserStatus = UserStatus.ACCEPTED;
    }

}