package com.group16.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import entities.User;

public class PendingAccounts extends AppCompatActivity {

    ArrayList<User> pendingUsersList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pending_accounts);
        setPendingUsersList();
    }

    public void onClickReturn(View view) {
        finish();
    }

    private void setPendingUsersList(){
        Database.getAllUsers(Database.UserStatus.PENDING, new Database.UserFetchCallback() {
            @Override
            public void onUsersFetched(ArrayList<User> users) {
                pendingUsersList.clear();
                pendingUsersList.addAll(users);
            }
        });
    }
}