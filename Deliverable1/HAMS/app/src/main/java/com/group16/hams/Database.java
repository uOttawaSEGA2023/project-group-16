package com.group16.hams;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import entities.*;

import java.util.ArrayList;
import java.util.EventListener;

public class Database {
    // Instance Variables
    static FirebaseDatabase database = FirebaseDatabase.getInstance();

    static DatabaseReference myRef = database.getReference();
    static DatabaseReference pendingRef = myRef.child("Pending");
    static DatabaseReference rejectedRef = myRef.child("Rejected");
    static DatabaseReference userRef = myRef.child("Users");

    public static User currentUser;
    public static DatabaseReference currentUserRef;
    public enum UserStatus {
        PENDING,
        REJECTED,
        ACCEPTED;
    }
    public static UserStatus currentUserStatus;

    public static void getPatient(String email, MyCallBack m) {
        DatabaseReference patientRef = userRef.child("Patients");
        patientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Patient p = null;
                for (DataSnapshot s : snapshot.getChildren()){
                    if (s.child("username").getValue(String.class).equals(email)) {
                        p = new Patient(s.child("firstName").getValue(String.class),
                                s.child("lastName").getValue(String.class),
                                s.child("username").getValue(String.class),
                                s.child("password").getValue(String.class),
                                s.child("phoneNumber").getValue(String.class),
                                s.child("address").getValue(String.class),
                                s.child("healthCardNumber").getValue(Integer.class));
                        break;
                    }
                }
                m.onCallBack(p);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("ERROR: getPatient()");
            }
        });
    }
    public interface MyCallBack{
        void onCallBack(Patient p);
    }

    public static void getUser(FirebaseUser user) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) { // Children of the database (Pending, Rejected, Users/Accepted)
                    String parent = snap.getKey();
                    if (snap.child("Patients").child(user.getUid()).exists()) {
                        determineStatus(parent);
                        snap = snap.child("Patients").child(user.getUid());
                        currentUserRef = snap.getRef();
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
                        currentUserRef = snap.getRef();
                        if (snap.child("appointments").hasChildren()) {
                            currentUser = new Doctor(snap.child("firstName").getValue(String.class),
                                    snap.child("lastName").getValue(String.class),
                                    snap.child("username").getValue(String.class),
                                    snap.child("password").getValue(String.class),
                                    snap.child("phoneNumber").getValue(String.class),
                                    snap.child("address").getValue(String.class),
                                    snap.child("employeeNumber").getValue(Integer.class),
                                    snap.child("specialties").getValue(String.class),
                                    getAppointmentsFromDatabase(currentUserRef),
                                    false);
                        } else {
                            currentUser = new Doctor(snap.child("firstName").getValue(String.class),
                                    snap.child("lastName").getValue(String.class),
                                    snap.child("username").getValue(String.class),
                                    snap.child("password").getValue(String.class),
                                    snap.child("phoneNumber").getValue(String.class),
                                    snap.child("address").getValue(String.class),
                                    snap.child("employeeNumber").getValue(Integer.class),
                                    snap.child("specialties").getValue(String.class));
                        }
                        break;
                    } else if (snap.child("Admin").child(user.getUid()).exists()) {
                        determineStatus(parent);
                        snap = snap.child("Admin").child(user.getUid());
                        currentUser = new Administrator();
                        break;
                    }
                }            }

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

        switch (status) {
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

    public static void registerUser(FirebaseUser user, User u) {
        if (u instanceof Doctor) {
            pendingRef.child("Doctors").child(user.getUid()).setValue(u);
        } else if (u instanceof Patient) {
            pendingRef.child("Patients").child(user.getUid()).setValue(u);
        }
    }

    public static void changeStatus(FirebaseUser user, User u, UserStatus newStatus) {
        removeUserFromCurrentStatus(user);
        DatabaseReference newStatusRef = getStatusReference(newStatus);

        //checks the type of user type and uses uid for the unique key
        if (u instanceof Doctor) {
            newStatusRef.child("Doctors").child(user.getUid()).setValue(u);
        } else if (u instanceof Patient) {
            newStatusRef.child("Patients").child(user.getUid()).setValue(u);
        }
    }

    private static void determineStatus(String status) {
        if (status.equals("Pending"))
            currentUserStatus = UserStatus.PENDING;
        else if (status.equals("Rejected"))
            currentUserStatus = UserStatus.REJECTED;
        else if (status.equals("Users"))
            currentUserStatus = UserStatus.ACCEPTED;
    }

    private static DatabaseReference getStatusReference(UserStatus status) {
        switch (status) {
            case PENDING:
                return pendingRef;
            case REJECTED:
                return rejectedRef;
            case ACCEPTED:
                return userRef;
            default:
                throw new IllegalArgumentException("Unknown status: " + status);
        }
    }

    private static void removeUserFromCurrentStatus(FirebaseUser user) {
        DatabaseReference ref = getStatusReference(currentUserStatus);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Patients").child(user.getUid()).exists()) {
                    ref.child("Patients").child(user.getUid()).removeValue();

                }
                else if(snapshot.child("Doctors").child(user.getUid()).exists()){
                    ref.child("Doctors").child(user.getUid()).removeValue();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("ERROR: removeUserFromCurrentStatus");
            }
        });

    }

    public static void appointmentToDatabase(ArrayList<Appointment> appointments){
        if (!(currentUser instanceof Doctor))
            return;

        DatabaseReference temp;

        for (Appointment a : appointments){
            temp = currentUserRef.child("appointments").child(a.getStartDateAndTimeString());
            temp.child("username").setValue(a.getAppointmentPatientEmail());
            temp.child("status").setValue(a.getStatus());
        }

    }

    public static ArrayList<Appointment> getAppointmentsFromDatabase(DatabaseReference c){
        ArrayList<Appointment> dApps = new ArrayList<>();

        ValueEventListener listener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String [] date = {"Year", "Month", "Day",  "Space",  "Hour","Minute"};

                for (DataSnapshot year : snapshot.getChildren()){
                    date[0] = year.getKey() + "/";
                    for (DataSnapshot month : year.getChildren()) {
                        date[1] = month.getKey() + "/";
                        for (DataSnapshot dayAndHour: month.getChildren()){
                            String [] temp = dayAndHour.getKey().split(" ");
                            date[2] = temp[0];
                            date[4] = temp[1].split(":")[0] + ":";
                            date[5] = temp[1].split(":")[1];
                            String email = dayAndHour.child("username").getValue(String.class);
                            int status = dayAndHour.child("status").getValue(Integer.class);
                            dApps.add(new Appointment(email,date[0] + date[1] + date[2] + " " + date[4] + date[5], status));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        c.child("appointments").addValueEventListener(listener);

        return dApps;
    }

    public static void changeAppointmentStatus(Appointment a, int status){
        currentUserRef.child("appointments").child(a.getStartDateAndTimeString()).child("status").setValue(status);
    }

    public static void changeAutoApprove(Boolean b){
        if (!(currentUser instanceof Doctor))
            return;

        currentUserRef.child("autoApprove").setValue(b);
        ((Doctor) currentUser).setAutoApprove(b);
        System.out.println(currentUser);
        System.out.println(((Doctor) currentUser).getAutoApprove());
    }
}