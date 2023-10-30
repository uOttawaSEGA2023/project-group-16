package com.group16.hams;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import entities.Patient;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter {
    ArrayList<RecyclerViewHolder> holders;
    public UserRecyclerViewAdapter(ArrayList<RecyclerViewHolder> holders) {
        this.holders = holders;
        System.out.println(getItemCount());
    }

    @NonNull
    @Override
    public LayoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("CREATING");
        View userLayout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_row, parent, false);
        return new LayoutViewHolder(userLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        System.out.println("BINDING");
        ((LayoutViewHolder)holder).tvFullName.setText
                ("Full Name: " + holders.get(position).getFullName());
        ((LayoutViewHolder)holder).tvEmail.setText
                ("Email: " + holders.get(position).getEmail());
        ((LayoutViewHolder)holder).tvAddress.setText
                ("Address: " + holders.get(position).getAddress());
        ((LayoutViewHolder)holder).tvPhoneNumber.setText
                ("Phone Number: " + holders.get(position).getPhoneNumber());

        if (holders.get(position).getType() == 0) {
            ((LayoutViewHolder)holder).tvHealthOrEmployee.setText
                    ("Health Card Number: " + holders.get(position).getHealthOrEmployee());
            ((LayoutViewHolder)holder).tvSpecialties.setText
                    ("");
        }

        else {
            ((LayoutViewHolder)holder).tvHealthOrEmployee.setText
                    ("Employee Number: " + holders.get(position).getHealthOrEmployee());
            ((LayoutViewHolder)holder).tvSpecialties.setText
                    ("Specialties: " + holders.get(position).getSpecialties());
        }
    }

    @Override
    public int getItemCount() {
        return holders.size();
    }

    public static class LayoutViewHolder extends RecyclerView.ViewHolder {
        TextView tvFullName, tvEmail, tvAddress, tvPhoneNumber, tvHealthOrEmployee, tvSpecialties ;
        public LayoutViewHolder(@NonNull View itemView) {
            super(itemView);
            System.out.println("CONSTRUCTING");
            tvFullName = (TextView) itemView.findViewById(R.id.textName);
            tvEmail = (TextView) itemView.findViewById(R.id.textEmail);
            tvAddress = (TextView) itemView.findViewById(R.id.textAddress);
            tvPhoneNumber = (TextView) itemView.findViewById(R.id.textPhoneNumber);
            tvHealthOrEmployee = (TextView) itemView.findViewById(R.id.textEmployeeOrHealth);
            tvSpecialties = (TextView) itemView.findViewById(R.id.textSpecialties);
        }
    }



}
