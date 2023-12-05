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

import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

public class Database {
    // Instance Variables
    static FirebaseDatabase database = FirebaseDatabase.getInstance();

    static DatabaseReference myRef = database.getReference();
    static DatabaseReference pendingRef = myRef.child("Pending");
    static DatabaseReference rejectedRef = myRef.child("Rejected");
    static DatabaseReference userRef = myRef.child("Users");
    static DatabaseReference patientsRef = userRef.child("Patients");
    static DatabaseReference doctorsRef = userRef.child("Doctors");

    public static boolean breakLoop;

    public static User currentUser;
    public static DatabaseReference currentUserRef;
    public enum UserStatus {
        PENDING,
        REJECTED,
        ACCEPTED;
    }
    public static UserStatus currentUserStatus;

    // Getting User Types
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

    public static void getPatientWithID(String email, PatientWithIDCallBack m) {
        DatabaseReference patientRef = userRef.child("Patients");
        patientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Patient p = null;
                String patientID = null;
                for (DataSnapshot s : snapshot.getChildren()){
                    if (s.child("username").getValue(String.class).equals(email)) {
                        patientID = s.getKey();
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
                m.PatientWithIDCallBack(p, patientID);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("ERROR: getPatient()");
            }
        });
    }
    public interface PatientWithIDCallBack{
        void PatientWithIDCallBack(Patient p, String patientID);
    }
    public static void getDoctor(String email, MyCallBack2 m) {
        DatabaseReference doctorRef = userRef.child("Doctors");
        doctorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Doctor p = null;
                for (DataSnapshot s : snapshot.getChildren()){
                    if (s.child("username").getValue(String.class).equals(email)) {
                        p = new Doctor(s.child("firstName").getValue(String.class),
                                s.child("lastName").getValue(String.class),
                                s.child("username").getValue(String.class),
                                s.child("password").getValue(String.class),
                                s.child("phoneNumber").getValue(String.class),
                                s.child("address").getValue(String.class),
                                s.child("employeeNumber").getValue(Integer.class),
                                s.child("specialties").getValue(String.class));
                        break;
                    }
                }
                m.onCallBack2(p);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("ERROR: getDoctor()");
            }
        });
    }
    public interface MyCallBack2{
        void onCallBack2(Doctor p);
    }

    public static void getDoctorWithID(String email, MyCallBack3 m) {
        DatabaseReference doctorRef = userRef.child("Doctors");
        doctorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Doctor p = null;
                String doctorID = null;
                for (DataSnapshot s : snapshot.getChildren()){
                    if (s.child("username").getValue(String.class).equals(email)) {
                        doctorID = s.getKey();
                        p = new Doctor(s.child("firstName").getValue(String.class),
                                s.child("lastName").getValue(String.class),
                                s.child("username").getValue(String.class),
                                s.child("password").getValue(String.class),
                                s.child("phoneNumber").getValue(String.class),
                                s.child("address").getValue(String.class),
                                s.child("employeeNumber").getValue(Integer.class),
                                s.child("specialties").getValue(String.class));
                        break;
                    }
                }
                m.onCallBack3(p, doctorID);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("ERROR: getDoctor()");
            }
        });
    }
    public interface MyCallBack3{
        void onCallBack3(Doctor p, String doctorID);
    }

    public static void getAllPatients(AllPatientsCallBack m) {
        breakLoop = false;

        DatabaseReference patientsRef = userRef.child("Patients");
        patientsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Patient> patients = new ArrayList<>();
                ArrayList<String> patientsIDs = new ArrayList<>();
                for (DataSnapshot s : snapshot.getChildren()){
                    patients.add(new Patient(s.child("firstName").getValue(String.class),
                            s.child("lastName").getValue(String.class),
                            s.child("username").getValue(String.class),
                            s.child("password").getValue(String.class),
                            s.child("phoneNumber").getValue(String.class),
                            s.child("address").getValue(String.class),
                            s.child("healthCardNumber").getValue(Integer.class))
                    );
                    patientsIDs.add(s.getKey());
                }

                if (!breakLoop) {
                    System.out.println("TEST IF LOOPING 1");
                    m.onAllPatientsCallBack(patients, patientsIDs);
                    breakLoop = true;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("ERROR: getAllPatients()");
            }
        });
    }
    public interface AllPatientsCallBack{
        void onAllPatientsCallBack(ArrayList<Patient> patients, ArrayList<String> patientIDs);
    }

    public static void getAllShiftsOfDoctors(ShiftCallback callback) {
        HashMap<Shift, ArrayList<String>> allShifts = new HashMap<>();

        doctorsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot doctorSnapshot : dataSnapshot.getChildren()) {
                    ArrayList<String> id = new ArrayList<>();
                    String username = doctorSnapshot.child("username").getValue(String.class);
                    String specialties = doctorSnapshot.child("specialties").getValue(String.class);
                    id.add(username);
                    id.add(specialties);

                    if (doctorSnapshot.hasChild("shifts")) {
                        DataSnapshot shiftsSnapshot = doctorSnapshot.child("shifts");

                        for (DataSnapshot shiftSnapshot : shiftsSnapshot.getChildren()) {
                            String year = shiftSnapshot.getKey();

                            for (DataSnapshot monthSnapshot : shiftSnapshot.getChildren()) {
                                String month = monthSnapshot.getKey();

                                for (DataSnapshot daySnapshot : monthSnapshot.getChildren()) {
                                    String[] day = daySnapshot.getKey().split(" ");
                                    String date = year + "/" + month + "/" + day[0];

                                    String startTime = daySnapshot.child("Start Time").getValue(String.class);
                                    String endTime = daySnapshot.child("End Time").getValue(String.class);


                                    Shift shift = new Shift(date, startTime, endTime, username, specialties);
                                    allShifts.put(shift, id);
                                }
                            }
                        }
                    }
                }

                // Notify the callback with the collected shifts
                callback.onShiftsRetrieved(allShifts);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors here
            }
        });
    }
    public interface ShiftCallback {
        void onShiftsRetrieved(HashMap<Shift, ArrayList<String>> shifts);
    }

    public static void getUser(FirebaseUser user) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) { // Children of the database (Pending, Rejected, Users/Accepted)
                    String parent = snap.getKey();
                    if (snap.child("Patients").child(user.getUid()).exists()) {
                        snap = snap.child("Patients").child(user.getUid());
                        determineStatus(parent);
                        currentUserRef = snap.getRef();
                        if (snap.child("timeslots").hasChildren()) {
                            currentUser = new Patient(snap.child("firstName").getValue(String.class),
                                    snap.child("lastName").getValue(String.class),
                                    snap.child("username").getValue(String.class),
                                    snap.child("password").getValue(String.class),
                                    snap.child("phoneNumber").getValue(String.class),
                                    snap.child("address").getValue(String.class),
                                    snap.child("healthCardNumber").getValue(Integer.class),
                                    getTimeSlotsFromDatabase(currentUserRef, new DataCallBack() {
                                        @Override
                                        public void onDataLoaded(ArrayList<TimeSlot> timeSlots) {

                                        }
                                    }),
                                    getPatientAppointmentsFromDatabase(currentUserRef));
                        } else {
                            currentUser = new Patient(snap.child("firstName").getValue(String.class),
                                    snap.child("lastName").getValue(String.class),
                                    snap.child("username").getValue(String.class),
                                    snap.child("password").getValue(String.class),
                                    snap.child("phoneNumber").getValue(String.class),
                                    snap.child("address").getValue(String.class),
                                    snap.child("healthCardNumber").getValue(Integer.class));
                        }
                        break;
                    } else if (snap.child("Doctors").child(user.getUid()).exists()) {
                        snap = snap.child("Doctors").child(user.getUid());
                        determineStatus(parent);
                        currentUserRef = snap.getRef();

                        boolean autoApprove = false;

                        try {
                            if (snap.child("autoApprove").getValue(Boolean.class) != null) {
                                autoApprove = snap.child("autoApprove").getValue(Boolean.class);
                            }
                        }

                        catch (NullPointerException e) {
                            System.out.println("No autoApprove value. Keep default as false.");
                        }

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
                                    autoApprove,
                                    getShiftsFromDatabase(currentUserRef));
                        }

                        else if (snap.child("shifts").hasChildren()) {
                            currentUser = new Doctor(snap.child("firstName").getValue(String.class),
                                    snap.child("lastName").getValue(String.class),
                                    snap.child("username").getValue(String.class),
                                    snap.child("password").getValue(String.class),
                                    snap.child("phoneNumber").getValue(String.class),
                                    snap.child("address").getValue(String.class),
                                    snap.child("employeeNumber").getValue(Integer.class),
                                    snap.child("specialties").getValue(String.class),
                                    autoApprove,
                                    getShiftsFromDatabase(currentUserRef));
                        }

                        else {
                            currentUser = new Doctor(snap.child("firstName").getValue(String.class),
                                    snap.child("lastName").getValue(String.class),
                                    snap.child("username").getValue(String.class),
                                    snap.child("password").getValue(String.class),
                                    snap.child("phoneNumber").getValue(String.class),
                                    snap.child("address").getValue(String.class),
                                    snap.child("employeeNumber").getValue(Integer.class),
                                    snap.child("specialties").getValue(String.class),
                                    autoApprove);
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
//hib
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

    // Register Methods
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

    // Appointment Methods
    public static void appointmentToDatabase(ArrayList<Appointment> appointments){
        if ((!(currentUser instanceof Doctor)))
            return;

        DatabaseReference temp;

        for (Appointment a : appointments){
            temp = currentUserRef.child("appointments").child(a.getStartDateAndTimeString());
            temp.child("username").setValue(a.getAppointmentPatientEmail());
            temp.child("status").setValue(a.getStatus());
            a.checkIfPast();
        }

    }

    public static void appointmentToDatabaseFromPatient(ArrayList<Appointment> appointments, String doctorID){
        if (!(currentUser instanceof Patient))
            return;

        DatabaseReference thisDoctor = doctorsRef.child(doctorID);
        DatabaseReference temp;

        for (Appointment a : appointments){
            temp = thisDoctor.child("appointments").child(a.getStartDateAndTimeString());
            temp.child("username").setValue(a.getAppointmentPatientEmail());
            temp.child("status").setValue(a.getStatus());
            a.checkIfPast();
        }

    }

    public static void timeSlotToDatabase(ArrayList<TimeSlot> timeSlots, String patientID){
        if (!(currentUser instanceof Patient))
            return;

        DatabaseReference thisPatient = patientsRef.child(patientID);
        DatabaseReference temp;

        for (TimeSlot a : timeSlots){
            temp = thisPatient.child("timeslots").child(a.getDateAndTimeString());
            temp.child("username").setValue(a.getAppointmentDoctorEmail());
            temp.child("status").setValue(a.getStatus());
            temp.child("specialty").setValue(a.getTimeSlotSpecialty());
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
                            Integer statusInteger = dayAndHour.child("status").getValue(Integer.class);
                            int status = (statusInteger != null) ? statusInteger : 0;
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
    public static void changeAutoApprove(Boolean b){
        if (!(currentUser instanceof Doctor))
            return;

        currentUserRef.child("autoApprove").setValue(b);
        ((Doctor) currentUser).setAutoApprove(b);
        System.out.println(currentUser);
        System.out.println(((Doctor) currentUser).getAutoApprove());
    }

    // TimeSlot Methods
    public static void getSpecialties(SpecialtyCallBack m) {
        DatabaseReference doctorRef = userRef.child("Doctors");
        doctorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> specialties = new ArrayList<>();
                for (DataSnapshot s : snapshot.getChildren()){
                    String p = s.child("specialties").getValue(String.class);
                    if (p != null){
                        specialties.add(p);
                    }
                }
                m.onSpecialtyCallBack(specialties);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("ERROR: getSpecialty()");
            }
        });
    }
    public interface SpecialtyCallBack{
        void onSpecialtyCallBack(ArrayList<String> specialties);
    }
    public static void patientAppointmentsToDatabase(ArrayList<TimeSlot> patientAppointments){
        if (!(currentUser instanceof Patient))
            return;

        DatabaseReference temp;

        for (TimeSlot a : patientAppointments){
            temp = currentUserRef.child("appointments").child(a.getDateAndTimeString());
            temp.child("username").setValue(a.getAppointmentDoctorEmail());
            temp.child("status").setValue(a.getStatus());
            temp.child("specialty").setValue(a.getTimeSlotSpecialty());
            temp.child("rating").setValue(a.getRating());
        }
    }
    public static ArrayList<TimeSlot> getPatientAppointmentsFromDatabase(DatabaseReference c){
        ArrayList<TimeSlot> patientAppointments = new ArrayList<>();

        DatabaseReference patientAppointmentsRef = currentUserRef.child("appointments");
        patientAppointmentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String [] date = {"Year", "Month", "Day",  "Space",  "StartHour", "StartMinute", "Space", "EndHour", "EndMinute"};

                for (DataSnapshot year : dataSnapshot.getChildren()){
                    date[0] = year.getKey() + "/";
                    for (DataSnapshot month : year.getChildren()) {
                        date[1] = month.getKey() + "/";
                        for (DataSnapshot dayAndHour: month.getChildren()){
                            String [] temp = dayAndHour.getKey().split(" ");
                            date[2] = temp[0];
                            date[4] = temp[1].split(":")[0] + ":";
                            date[5] = temp[1].split(":")[1];
                            date[7] = temp[2].split(":")[0] + ":";
                            date[8] = temp[2].split(":")[1];
                            String email = dayAndHour.child("username").getValue(String.class);
                            int status = dayAndHour.child("status").getValue(Integer.class);
                            String specialty = dayAndHour.child("specialty").getValue(String.class);
                            float rating = 0;
                            if (dayAndHour.child("rating").getValue() != null) {
                                rating = dayAndHour.child("rating").getValue(Float.class);
                            }
                            patientAppointments.add(new TimeSlot(email,date[0] + date[1] + date[2] + " " + date[4] + date[5] + " " + date[7] + date[8], specialty, status, rating));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        return patientAppointments;
    }

    public static void deletePatientAppointment(TimeSlot patientAppointment) {
        if (!(currentUser instanceof Patient)) {
            System.out.println("Current user is not a Patient.");
            return;
        }

        DatabaseReference patientAppointmentRef = currentUserRef.child("appointments")
                .child(patientAppointment.getDateAndTimeString());

        patientAppointmentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    dataSnapshot.getRef().removeValue();
                    System.out.println("Appointment deleted successfully.");
                } else {
                    System.out.println("Appointment not found in database.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Failed to delete appointment: " + databaseError.getMessage());
            }
        });
    }

    public static void deleteDoctorAppointmentThroughDoctor(Appointment patientAppointment) {
        if (!(currentUser instanceof Doctor)) {
            System.out.println("Current user is not a Doctor.");
            return;
        }

        DatabaseReference patientAppointmentRef = currentUserRef.child("appointments")
                .child(patientAppointment.getStartDateAndTimeString());

        patientAppointmentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    dataSnapshot.getRef().removeValue();
                    System.out.println("Appointment deleted successfully.");
                } else {
                    System.out.println("Appointment not found in database.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Failed to delete appointment: " + databaseError.getMessage());
            }
        });
    }

    public static void deleteDoctorAppointment(Appointment doctorAppointment, String doctorID) {
        if (!(currentUser instanceof Patient)) {
            System.out.println("Current user is not a Patient.");
            return;
        }

        DatabaseReference doctorAppointmentRef = doctorsRef.child(doctorID).child("appointments")
                .child(doctorAppointment.getStartDateAndTimeString());

        doctorAppointmentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    dataSnapshot.getRef().removeValue();
                    System.out.println("Appointment deleted successfully.");
                } else {
                    System.out.println("Appointment not found in database.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Failed to delete appointment: " + databaseError.getMessage());
            }
        });
    }

    public static void deletePatientAppointmentThroughDoctor(TimeSlot doctorAppointment, String patientID) {
        if (!(currentUser instanceof Doctor)) {
            System.out.println("Current user is not a Doctor.");
            return;
        }

        DatabaseReference doctorAppointmentRef = patientsRef.child(patientID).child("appointments")
                .child(doctorAppointment.getDateAndTimeString());

        doctorAppointmentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    dataSnapshot.getRef().removeValue();
                    System.out.println("Appointment deleted successfully.");
                } else {
                    System.out.println("Appointment not found in database.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Failed to delete appointment: " + databaseError.getMessage());
            }
        });
    }

    public interface DataCallBack {
        void onDataLoaded(ArrayList<TimeSlot> timeSlots);
    }
    public static ArrayList<TimeSlot> getTimeSlotsFromDatabase(DatabaseReference c, DataCallBack callBack){
        ArrayList<TimeSlot> dApps = new ArrayList<>();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String [] date = {"Year", "Month", "Day",  "Space",  "StartHour", "StartMinute", "Space", "EndHour", "EndMinute"};

                for (DataSnapshot year : snapshot.getChildren()){
                    date[0] = year.getKey() + "/";
                    for (DataSnapshot month : year.getChildren()) {
                        date[1] = month.getKey() + "/";
                        for (DataSnapshot dayAndHour: month.getChildren()){
                            String [] temp = dayAndHour.getKey().split(" ");
                            date[2] = temp[0];
                            date[4] = temp[1].split(":")[0] + ":";
                            date[5] = temp[1].split(":")[1];
                            date[7] = temp[2].split(":")[0] + ":";
                            date[8] = temp[2].split(":")[1];
                            String email = dayAndHour.child("username").getValue(String.class);
                            Integer statusInteger = dayAndHour.child("status").getValue(Integer.class);
                            int status = (statusInteger != null) ? statusInteger : 0;
                            String specialty = dayAndHour.child("specialty").getValue(String.class);
                            dApps.add(new TimeSlot(email,date[0] + date[1] + date[2] + " " + date[4] + date[5] + " " + date[7] + date[8], specialty, status));
                        }
                    }
                }
                callBack.onDataLoaded(dApps);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        c.child("timeslots").addValueEventListener(listener);

        return dApps;
    }

    public static void changeAppointmentStatus(Appointment a, int status){
        currentUserRef.child("appointments").child(a.getStartDateAndTimeString()).child("status").setValue(status);
    }
    public static void changeTimeSlotStatus(TimeSlot a, int status, String patientID){
        DatabaseReference thisPatient = patientsRef.child(patientID);
        DatabaseReference temp;

        temp = thisPatient.child("timeslots").child(a.getDateAndTimeString());
        temp.child("status").setValue(status);
    }
    public static void changePatientAppointmentRating(TimeSlot t, float v){
        currentUserRef.child("appointments").child(t.getDateAndTimeString()).child("rating").setValue(v);
    }

    // Shift Methods
    public static void shiftToDatabase(ArrayList<Shift> shifts){
        if (!(currentUser instanceof Doctor))
            return;

        DatabaseReference temp;

        for (Shift a : shifts){
            String[] date = a.getDate().split("/");
            String day = date[0];
            String month = date[1];
            String year = date[2];
            String newDate = year + "/" + month + "/" + day + " " + a.getStartTime();
            temp = currentUserRef.child("shifts").child(newDate);
            temp.child("Start Time").setValue(a.getStartTime());
            temp.child("End Time").setValue(a.getEndTime());
            temp.child("Doctor Username").setValue(a.getDoctorUsername());
            temp.child("Specialties").setValue(a.getDoctorSpecialty());
        }

    }
    public static ArrayList<Shift> getShiftsFromDatabase(DatabaseReference c){
        ArrayList<Shift> shifts = new ArrayList<>();

        DatabaseReference shiftsRef = currentUserRef.child("shifts");
        shiftsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot yearSnapshot : dataSnapshot.getChildren()) {
                    String year = yearSnapshot.getKey();

                    for (DataSnapshot monthSnapshot : yearSnapshot.getChildren()) {
                        String month = monthSnapshot.getKey();

                        for (DataSnapshot daySnapshot : monthSnapshot.getChildren()) {
                            String[] day = daySnapshot.getKey().split(" ");
                            String date = day[0] + "/" + month + "/" + year;

                            String startTime = daySnapshot.child("Start Time").getValue(String.class);
                            String endTime = daySnapshot.child("End Time").getValue(String.class);
                            String username = daySnapshot.child("Doctor Username").getValue(String.class);
                            String specialties = daySnapshot.child("Specialties").getValue(String.class);

                            shifts.add(new Shift(date, startTime, endTime, username, specialties));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        return shifts;
    }
    public static void deleteShift(Shift shift) {
        if (!(currentUser instanceof Doctor)) {
            System.out.println("Current user is not a Doctor.");
            return;
        }

        String[] dateParts = shift.getDate().split("/");
        String day = dateParts[0];
        String month = dateParts[1];
        String year = dateParts[2];
        String formattedDate = year + "/" + month + "/" + day + " " + shift.getStartTime();

        DatabaseReference shiftRef = currentUserRef.child("shifts").child(formattedDate);

        shiftRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    dataSnapshot.getRef().removeValue();
                    System.out.println("Shift deleted successfully.");
                } else {
                    System.out.println("Shift not found in database.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Failed to delete shift: " + databaseError.getMessage());
            }
        });
    }

}