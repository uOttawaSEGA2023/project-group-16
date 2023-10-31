package com.group16.hams;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import entities.Doctor;
import entities.Patient;
import entities.User;

public class PendingAccounts extends AppCompatActivity implements RecyclerViewInterface {

    ArrayList<User> pendingUsersList = new ArrayList<>();
    ArrayList<RecyclerViewHolder> clickedUsers = new ArrayList<>();
    ArrayList<RecyclerViewHolder> pendingUserViews = new ArrayList<>();
    UserRecyclerViewAdapter adapter;

    Database database = new Database();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pending_accounts);
        RecyclerView recyclerView = findViewById(R.id.pendingRecyclerView);
        setPendingUsersList();

        Context context = this;
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter = new UserRecyclerViewAdapter
                        (pendingUserViews, PendingAccounts.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(adapter);
            }
        }, 1000);


    }

    public void onClickReturn(View view) {
        finish();
    }

    public void onClickAccept(View view) {
        int index;
        RecyclerViewHolder curUserHolder;
        User curUser;

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if (clickedUsers.size() != 0) {
            for (int i = 0; i < clickedUsers.size(); i++) {
                curUserHolder = clickedUsers.get(i);
                curUser = curUserHolder.getStoredUser();

                index = pendingUserViews.indexOf(curUserHolder);

                User finalCurUser = curUser;
                mAuth.signInWithEmailAndPassword(curUser.getUsername(), curUser.getPassword())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser currentFirebaseUser = mAuth.getCurrentUser();
                                    Database.getUser(currentFirebaseUser);
                                    Database.changeStatus(currentFirebaseUser, finalCurUser, Database.UserStatus.ACCEPTED);

                                    Log.d(TAG, "signInWithCustomToken:success");
                                } else {
                                    Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                                }
                            }
                        });

                pendingUserViews.remove(index);
                pendingUsersList.remove(curUser);
                adapter.notifyItemRemoved(index);
            }

            clickedUsers.clear();

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
        }
    }

    public void onClickReject(View view) {
        int index;
        RecyclerViewHolder curUserHolder;
        User curUser;

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if (clickedUsers.size() != 0) {
            for (int i = 0; i < clickedUsers.size(); i++) {
                curUserHolder = clickedUsers.get(i);
                curUser = curUserHolder.getStoredUser();


                index = pendingUserViews.indexOf(curUserHolder);

                User finalCurUser = curUser;
                mAuth.signInWithEmailAndPassword(curUser.getUsername(), curUser.getPassword())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser currentFirebaseUser = mAuth.getCurrentUser();
                                    Database.getUser(currentFirebaseUser);
                                    Database.changeStatus(currentFirebaseUser, finalCurUser, Database.UserStatus.REJECTED);

                                    Log.d(TAG, "signInWithCustomToken:success");
                                } else {
                                    Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                                }
                            }
                        });

                pendingUserViews.remove(index);
                pendingUsersList.remove(curUser);
                adapter.notifyItemRemoved(index);
            }

            clickedUsers.clear();

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
        }
    }

    private void setPendingUsersList(){

        pendingUsersList = Database.getAllUsers(Database.UserStatus.PENDING);


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

        for (int i = 0; i < pendingUsersList.size(); i++) {
            curUser = pendingUsersList.get(i);
            curName = curUser.getFirstName() + " " + curUser.getLastName();
            curEmail = curUser.getUsername();
            curAddress = curUser.getAddress();
            curPhoneNumber = String.valueOf(curUser.getPhoneNumber());

            if (curUser instanceof Patient) {
                curHealthCardNumber = String.valueOf(((Patient) curUser).getHealthCardNumber());

                pendingUserViews.add(new RecyclerViewHolder(0, curName, curEmail, curAddress,
                        curPhoneNumber, curHealthCardNumber, "", curUser));
            }

            else if (curUser instanceof Doctor) {
                curEmployeeNumber = String.valueOf(((Doctor) curUser).getEmployeeNumber());
                curSpecialites = ((Doctor) curUser).getSpecialties();

                pendingUserViews.add(new RecyclerViewHolder(1, curName, curEmail, curAddress,
                        curPhoneNumber, curEmployeeNumber, curSpecialites, curUser));
            }
        }

    }


    @Override
    public void onItemClick(int position) {
        RecyclerViewHolder curHolder = pendingUserViews.get(position);

        if (curHolder.getBeenClicked()) {
            clickedUsers.remove(curHolder);
        }

        else {
            clickedUsers.add(curHolder);
        }

        curHolder.changeClickedStatus();
    }
}