package com.group16.hams.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.group16.hams.Database;
import com.group16.hams.R;
import com.group16.hams.RecyclerViewInterface;

import java.util.ArrayList;

import entities.Doctor;
import entities.Patient;
import entities.User;

public class AcceptedAccounts extends AppCompatActivity implements RecyclerViewInterface {

    ArrayList<User> acceptedUsersList = new ArrayList<>();
    ArrayList<RecyclerViewHolderUser> clickedUsers = new ArrayList<>();
    ArrayList<RecyclerViewHolderUser> acceptedUserViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accepted_accounts);
        RecyclerView recyclerView = findViewById(R.id.acceptedRecyclerView);
        setAcceptedUsersList();

        Context context = this;
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                RecyclerViewAdapterUser adapter = new RecyclerViewAdapterUser
                        (acceptedUserViews, AcceptedAccounts.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(adapter);
            }
        }, 1000);


    }

    public void onClickReturn(View view) {
        finish();
    }

    private void setAcceptedUsersList(){

        acceptedUsersList = Database.getAllUsers(Database.UserStatus.ACCEPTED);


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

        for (int i = 0; i < acceptedUsersList.size(); i++) {
            curUser = acceptedUsersList.get(i);
            curName = curUser.getFirstName() + " " + curUser.getLastName();
            curEmail = curUser.getUsername();
            curAddress = curUser.getAddress();
            curPhoneNumber = String.valueOf(curUser.getPhoneNumber());

            if (curUser instanceof Patient) {
                curHealthCardNumber = String.valueOf(((Patient) curUser).getHealthCardNumber());

                acceptedUserViews.add(new RecyclerViewHolderUser(0, curName, curEmail, curAddress,
                        curPhoneNumber, curHealthCardNumber, "", curUser));
            }

            else if (curUser instanceof Doctor) {
                curEmployeeNumber = String.valueOf(((Doctor) curUser).getEmployeeNumber());
                curSpecialites = ((Doctor) curUser).getSpecialties();

                acceptedUserViews.add(new RecyclerViewHolderUser(1, curName, curEmail, curAddress,
                        curPhoneNumber, curEmployeeNumber, curSpecialites, curUser));
            }
        }

    }

    @Override
    public void onItemClick(int tyep, int position) {
        RecyclerViewHolderUser curHolder = acceptedUserViews.get(position);

        if (curHolder.getBeenClicked()) {
            clickedUsers.remove(curHolder);
        }

        else {
            clickedUsers.add(curHolder);
        }

        curHolder.changeClickedStatus();
    }
}