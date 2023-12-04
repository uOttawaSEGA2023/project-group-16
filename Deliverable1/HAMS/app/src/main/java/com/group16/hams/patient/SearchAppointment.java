package com.group16.hams.patient;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.group16.hams.Database;
import com.group16.hams.R;

import java.util.ArrayList;
import java.util.List;


import entities.Doctor;
import entities.Patient;
import entities.Specialty;


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

        Database.getSpecialties(new Database.SpecialtyCallBack() {
            @Override
            public void onSpecialtyCallBack(ArrayList<String> specialties) {
                specialtyList = new ArrayList<>();
                if (specialties == null || specialties.isEmpty()) {
                    System.out.println("Doctor does not have a specialty.");
                }
                else {
                    for (String specialty : specialties){
                        String[] splited = specialty.split(",");
                        for (String split : splited){
                            specialtyList.add(new Specialty(split.trim()));
                        }
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