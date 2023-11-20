package com.group16.hams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import entities.Doctor;

public class SearchAppointment extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Specialty> specialtyList;
    private SpecialtyAdapter specialtyAdapter;
    private SearchView searchView;

    FirebaseUser user;
    DatabaseReference reference;

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
        specialtyList = new ArrayList<Specialty>();


        specialtyList.add(new Specialty("family medicine"));
        specialtyList.add(new Specialty("pediatric"));
        specialtyList.add(new Specialty("surgeon"));
        
        specialtyAdapter = new SpecialtyAdapter(specialtyList);
        recyclerView.setAdapter(specialtyAdapter);

    }

    private void filterList(String text) {
        List<Specialty> filteredList = new ArrayList<>();
        for (Specialty specialty : specialtyList) {
            if (specialty.getSpecialty().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(specialty);
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