package com.group16.hams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import java.util.ArrayList;

import entities.Doctor;
import entities.Patient;
import entities.User;

public class RejectedAccounts extends AppCompatActivity {

    ArrayList<User> rejectedUsersList = new ArrayList<>();
    ArrayList<RecyclerViewHolder> rejectedUserViews = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rejected_accounts);
        RecyclerView recyclerView = findViewById(R.id.rejectedRecyclerView);
        setRejectedUsersList();

        Context context = this;
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                UserRecyclerViewAdapter adapter = new UserRecyclerViewAdapter(rejectedUserViews);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(adapter);
            }
        }, 1000);


    }

    public void onClickReturn(View view) {
        finish();
    }

    private void setRejectedUsersList(){

        rejectedUsersList = Database.getAllUsers(Database.UserStatus.REJECTED);


        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                recycleAdd();
            }
        }, 1000);


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

        for (int i = 0; i < rejectedUsersList.size(); i++) {
            curUser = rejectedUsersList.get(i);
            curName = curUser.getFirstName() + " " + curUser.getLastName();
            curEmail = curUser.getUsername();
            curAddress = curUser.getAddress();
            curPhoneNumber = String.valueOf(curUser.getPhoneNumber());

            if (curUser instanceof Patient) {
                curHealthCardNumber = String.valueOf(((Patient) curUser).getHealthCardNumber());

                rejectedUserViews.add(new RecyclerViewHolder(0, curName, curEmail, curAddress,
                        curPhoneNumber, curHealthCardNumber, ""));
            }

            else if (curUser instanceof Doctor) {
                curEmployeeNumber = String.valueOf(((Doctor) curUser).getEmployeeNumber());
                curSpecialites = ((Doctor) curUser).getSpecialties();

                rejectedUserViews.add(new RecyclerViewHolder(1, curName, curEmail, curAddress,
                        curPhoneNumber, curEmployeeNumber, curSpecialites));
            }
        }

    }
}