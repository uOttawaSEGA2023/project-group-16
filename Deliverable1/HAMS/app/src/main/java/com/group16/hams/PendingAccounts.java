package com.group16.hams;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

    public void onClickLoad(View view){
        setPendingUsersList();
    }

    private void setPendingUsersList(){
        pendingUsersList = Database.getAllUsers(Database.UserStatus.PENDING);
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                for (User users: pendingUsersList) {
                    Log.w(TAG, users.toString());
                }

                Log.w(TAG, Integer.toString(pendingUsersList.size()));
            }
        }, 1000);

    }
}