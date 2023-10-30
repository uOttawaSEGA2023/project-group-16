package com.group16.hams;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import entities.Doctor;
import entities.Patient;
import entities.User;

public class PendingAccounts extends AppCompatActivity {

    ArrayList<User> pendingUsersList = new ArrayList<>();
    ArrayList<RecyclerViewHolder> pendingUserViews = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pending_accounts);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        pendingUsersList = Database.getAllUsers(Database.UserStatus.PENDING);
        recycleAdd();
        UserRecyclerViewAdapter adapter = new UserRecyclerViewAdapter(pendingUserViews);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onClickReturn(View view) {
        finish();
    }

    public void onClickLoad(View view){
        setPendingUsersList();
    }

    private void setPendingUsersList(){

        pendingUsersList = Database.getAllUsers(Database.UserStatus.PENDING);

        /*
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() { recycleAdd(); }
        }, 1000);

         */
    }

    private void recycleAdd(){
        User curUser;
        String curName;
        String curEmail;
        String curAddress;
        String curPhoneNumber;
        String curHealthCardNumber;
        String curEmployeeNumber;
        String curSpecialites;

        for (int i = 0; i < pendingUsersList.size(); i++) {
            curUser = pendingUsersList.get(i);
            curName = curUser.getFirstName() + " " + curUser.getLastName();
            curEmail = curUser.getUsername();
            curAddress = curUser.getAddress();
            curPhoneNumber = String.valueOf(curUser.getPhoneNumber());

            if (curUser instanceof Patient) {
                curHealthCardNumber = String.valueOf(((Patient) curUser).getHealthCardNumber());

                pendingUserViews.add(new RecyclerViewHolder(0, curName, curEmail, curAddress,
                        curPhoneNumber, curHealthCardNumber));
            }

            else if (curUser instanceof Doctor) {
                curEmployeeNumber = String.valueOf(((Doctor) curUser).getEmployeeNumber());
                curSpecialites = ((Doctor) curUser).getSpecialties();

                pendingUserViews.add(new RecyclerViewHolder(1, curName, curEmail, curAddress,
                        curPhoneNumber, curEmployeeNumber, curSpecialites));
            }
        }
    }
}