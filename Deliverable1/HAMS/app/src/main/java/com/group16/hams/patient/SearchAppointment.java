package com.group16.hams.patient;


import static com.group16.hams.doctor.AddShift.calculateNumberOfTimeSlots;
import static com.group16.hams.doctor.AddShift.convertMinutesToTime;
import static com.group16.hams.doctor.AddShift.convertTimeToMinutes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;


import com.group16.hams.Database;
import com.group16.hams.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import entities.Doctor;
import entities.Patient;
import entities.Shift;
import entities.Specialty;
import entities.TimeSlot;


public class SearchAppointment extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Specialty> specialtyList;
    private SpecialtyAdapter specialtyAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view);

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Database.getAllShiftsOfDoctors(new Database.ShiftCallback() {
            @Override
            public void onShiftsRetrieved(HashMap<Shift, ArrayList<String>> shifts) {
                System.out.println(shifts);
                Database.getAllPatients(new Database.AllPatientsCallBack() {
                    @Override
                    public void onAllPatientsCallBack(ArrayList<Patient> patients, ArrayList<String> patientIDs) {
                        System.out.println("SEARCH APPOINTMENT");

                        if (patients == null || patients.isEmpty()) {
                            System.out.println("Database does not have any patients.");
                        }
                        else {
                            int i = 0;
                            for (Patient patient : patients) {
                                for (Map.Entry<Shift, ArrayList<String>> shift : shifts.entrySet()) {
                                    int numberOfTimeSlots = calculateNumberOfTimeSlots(shift.getKey().getStartTime(), shift.getKey().getEndTime());
                                    int timeSlotInterval = 30;
                                    int shiftStartMinute = convertTimeToMinutes(shift.getKey().getStartTime());
                                    for (int k = 0; k < numberOfTimeSlots; k++) {
                                        int startTime = shiftStartMinute + k * timeSlotInterval;
                                        int endTime = startTime + timeSlotInterval;

                                        // Format the time slots
                                        String timeSlotStartTime = convertMinutesToTime(startTime);
                                        String timeSlotEndTime = convertMinutesToTime(endTime);

                                        // Add the time slot to the patient
                                        patient.addTimeSlot(new TimeSlot(shift.getValue().get(0), shift.getKey().getDate() + " " + timeSlotStartTime + " " + timeSlotEndTime, shift.getValue().get(1)));
                                    }
                                }
                                String thisPatientID = patientIDs.get(i);
                                i++;
                                (new Handler()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Database.timeSlotToDatabase(patient.getTimeSlots(), thisPatientID);
                                    }
                                });
                            }
                        }
                    }
                });
            }
        });

        Database.getSpecialties(new Database.SpecialtyCallBack() {
            @Override
            public void onSpecialtyCallBack(ArrayList<String> specialties) {
                specialtyList = new ArrayList<>();
                if (specialties == null || specialties.isEmpty()) {
                    System.out.println("Doctor does not have a specialty.");
                }
                else {
                    for (String specialty : specialties){
                        /*String[] splited = specialty.split(",");
                        for (String split : splited){
                            specialtyList.add(new Specialty(split.trim()));
                        }*/
                        specialtyList.add(new Specialty(specialty));
                    }
                }

                specialtyAdapter = new SpecialtyAdapter(SearchAppointment.this, specialtyList);
                recyclerView.setAdapter(specialtyAdapter);
            }
        });
    }

    private void filterList(String text) {
        List<Specialty> filteredList = new ArrayList<>();

        if (specialtyList != null) {
            for (Specialty specialty : specialtyList) {
                if (specialty.getSpecialty().toLowerCase().contains(text.toLowerCase())){
                    filteredList.add(specialty);
                }
            }
        }

        if (filteredList.isEmpty()){
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }else{
            specialtyAdapter.setFilteredList(filteredList);

        }
    }
    public void onClickSearchViewReturnButton(View view) { finish(); }
}