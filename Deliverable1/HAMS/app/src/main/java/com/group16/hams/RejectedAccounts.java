package com.group16.hams;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import entities.Doctor;
import entities.Patient;
import entities.User;

public class RejectedAccounts extends AppCompatActivity implements RecyclerViewInterface{

    ArrayList<User> rejectedUsersList = new ArrayList<>();
    ArrayList<RecyclerViewHolderUser> clickedUsers = new ArrayList<>();
    ArrayList<RecyclerViewHolderUser> rejectedUserViews = new ArrayList<>();
    RecyclerViewAdapterUser adapter;

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
                adapter = new RecyclerViewAdapterUser
                        (rejectedUserViews, RejectedAccounts.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(adapter);
            }
        }, 1000);


    }

    public void onClickReturn(View view) {
        finish();
    }

    public void onClickAcceptRejects(View view) {
        int index;
        RecyclerViewHolderUser curUserHolder;
        User curUser;

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if (clickedUsers.size() != 0) {
            for (int i = 0; i < clickedUsers.size(); i++) {
                curUserHolder = clickedUsers.get(i);
                curUser = curUserHolder.getStoredUser();

                index = rejectedUserViews.indexOf(curUserHolder);

                User finalCurUser = curUser;
                mAuth.signInWithEmailAndPassword(curUser.getUsername(), curUser.getPassword())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser currentFirebaseUser = mAuth.getCurrentUser();
                                    Database.getUser(currentFirebaseUser);
                                    (new Handler()).postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Database.changeStatus(currentFirebaseUser, finalCurUser, Database.UserStatus.ACCEPTED);
                                        }
                                    }, 1000);

                                    Log.d(TAG, "signInWithCustomToken:success");
                                } else {
                                    Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                                }
                            }
                        });

                rejectedUserViews.remove(index);
                rejectedUsersList.remove(curUser);
                adapter.notifyItemRemoved(index);
            }

            clickedUsers.clear();

            /*
            mAuth.signInWithEmailAndPassword("admin@admin.com", "adminadmin")
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser currentFirebaseUser = mAuth.getCurrentUser();
                                Database.getUser(currentFirebaseUser);

                                Log.d(TAG, "signInWithCustomToken:success");
                            } else {
                                Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                            }
                        }
                    });
                    
             */
        }
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

                rejectedUserViews.add(new RecyclerViewHolderUser(0, curName, curEmail, curAddress,
                        curPhoneNumber, curHealthCardNumber, "", curUser));
            }

            else if (curUser instanceof Doctor) {
                curEmployeeNumber = String.valueOf(((Doctor) curUser).getEmployeeNumber());
                curSpecialites = ((Doctor) curUser).getSpecialties();

                rejectedUserViews.add(new RecyclerViewHolderUser(1, curName, curEmail, curAddress,
                        curPhoneNumber, curEmployeeNumber, curSpecialites, curUser));
            }
        }

    }

    @Override
    public void onItemClick(int position) {
        RecyclerViewHolderUser curHolder = rejectedUserViews.get(position);

        if (curHolder.getBeenClicked()) {
            clickedUsers.remove(curHolder);
        }

        else {
            clickedUsers.add(curHolder);
        }

        curHolder.changeClickedStatus();
    }
}